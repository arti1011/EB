package de.party.nutzer.rest;

import java.util.List;
import java.util.NoSuchElementException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;





import org.jboss.logging.Logger;

import com.google.common.base.Strings;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import de.party.nutzer.domain.Adresse;
import de.party.nutzer.domain.Nutzer;
import de.party.nutzer.domain.ProfilePicture;
import de.party.nutzer.service.NutzerService;
import de.party.party.domain.Party;
import de.party.party.domain.Rating;
import de.party.party.service.PartyService;
import de.party.util.rest.NotFoundException;


@Path("/user")
@Produces(APPLICATION_JSON)
@Consumes
@Transactional
@RequestScoped
public class NutzerResource {

			
	private static final Logger LOGGER = Logger.getLogger(NutzerResource.class);
	
	// Exception Message Keys
	private static final String NOT_FOUND_MAIL  = "nutzer.notFound.email";
	private static final String NOT_FOUND_NACHNAME = "nutzer.notFound.nachname";

	private static final String NOT_FOUND_USER = "nutzer.notFound";
	
	private static final String NOT_FOUND_PARTY = "party.notFound";
	
	@Context
	private UriInfo uriInfo;
	
	@Inject
	private UriHelperNutzer uriHelperNutzer;
	
		
	@Inject 
	private NutzerService ns;
	
	@Inject
	private PartyService ps;
	
		
	@GET
	@Produces(TEXT_PLAIN)
	@Path("version")
	public String version() {
		
		LOGGER.info("VERSION REQUESTED");
		return "1.0";
	}
	

	
	/**
	 * Nutzer anhand der Email suchen, QueryParam = email
	 * 
	 * Aufruf: /?email=mumi1012@test.de
	 * 
	 * @param email
	 * @return Nutzer
	 * 
	 * @throws NotFoundException
	 */
	@GET
	public Response findNutzer(@QueryParam(Nutzer.EMAIL_QUERY_PARAM) String email,
							   @QueryParam(Nutzer.NACHNAME_QUERY_PARAM) String nachname
			) {
		
		
		Nutzer nutzer = null;
		
		List<Nutzer> nutzerListe = null;
		
		if (!Strings.isNullOrEmpty(email)) {
			nutzer = ns.findNutzerByEmail(email);
			
			if (nutzer == null) {
				throw new NotFoundException(NOT_FOUND_MAIL, email);
			}
		}
		
		else if (!Strings.isNullOrEmpty(nachname)) {
			nutzerListe = ns.findNutzerByNachname(nachname);
			
			if (nutzerListe.isEmpty()) {
				throw new NotFoundException(NOT_FOUND_NACHNAME, nachname);
			}
		}
		
		Object entity = null;
		if (nutzerListe != null) {
			entity = new GenericEntity<List<Nutzer>>(nutzerListe) { };
		}
		
		else if (nutzer != null) {
			entity = nutzer;
		}
		
		return Response.ok(entity).build();
	}
	
	/**
	 * Nutzer anhand Nachnamen-Prefix suchen (Wildcard-Suche)
	 * 
	 * Beispiel: user/prefix/nachname/mue
	 *  
	 * @param nachnamePrefix
	 * @return Nutzer
	 * 
	 * @throws NotFoundException
	 */
	@GET
	@Path("{id:[1-9][0-9]*}/prefix/nachname/{nachname}")
	public Response findNutzerByNachnamePrefix(@PathParam("id") Long id,@PathParam("nachname") String nachnamePrefix) {
		final List<Nutzer> nutzer = ns.findNutzerByNachnamePrefix(id,nachnamePrefix);
		
		if (nutzer.isEmpty()) {
			return null;
		}
		
		//URIs anpassen
		for (Nutzer singleUser : nutzer) {
			uriHelperNutzer.updateUriNutzer(singleUser, uriInfo);
		}
		
		return Response.ok(nutzer).build();
		
	}
	
	
	/**
	 * Methode um einen Nutzer anhand der internen ID auszulesen
	 * 
	 * @param id
	 * @return Nutzer
	 */
	@GET
	@Path("{id:[1-9][0-9]*}")
	@Produces(APPLICATION_JSON)
	public Response findNutzerById(@PathParam("id") Long id) {
		final Nutzer nutzer = ns.findNutzerById(id);
		
		if(nutzer ==  null) {
			throw new NoSuchElementException();
		}
		
		// URLs innerhalb der gefundenen Nutzer anpassen
		uriHelperNutzer.updateUriNutzer(nutzer, uriInfo);
		return Response.ok(nutzer).build();
	}
	
	
	/**
	 * Freunde anhand der eigenen ID finden
	 * 
	 * Aufruf: ../friend/?id=1
	 * 
	 * @param id
	 * @return Nutzer-Objekte, die in der Relation Freundschaft zu der eingegebenen ID auftauchen
	 */
	@GET
	@Path("/friend/")
	public Response findFriendsById(@QueryParam(Nutzer.ID_QUERY_PARAM) Long id) {
		
		final List<Nutzer> friends = ns.findFriendsById(id);
			
		
		for (Nutzer nutzer : friends) {
			uriHelperNutzer.updateUriNutzer(nutzer, uriInfo);
		}
		return Response.ok(friends).build();
	}
	
	/**
	 * 
	 * 
	 * @param nutzer_id
	 * @return List<URI> Nutzer ==> Freunde
	 */
	@GET
	@Path("{id:[1-9][0-9]*}/friends/")
	public Response findFriendsByNutzerId(@PathParam("id") Long nutzer_id) {
		final Nutzer nutzer = ns.findNutzerById(nutzer_id);
		if (nutzer == null) {
			return null;
		}
		
		final List<Nutzer> freunde = ns.findFriendsByNutzer(nutzer);
//		final List<Freundschaft> myFriendsRelation = ns.findFriendsByNutzer(nutzer);
		
		// URL für die Freunde der Freunde..
		/*for (Nutzer freund : freunde) {
			uriHelperNutzer.updateUriNutzer(freund, uriInfo);
			
		} */
		
		return Response.ok(freunde).build();
	}
	
	@POST
	@Path("/friend/{id:[1-9][0-9]*}")
	@Consumes(APPLICATION_JSON)
	@Transactional
	public Response addFriend(@PathParam("id") Long nutzer_id, Long[] friend_ids) {
		
		Nutzer requester = ns.addFriend(nutzer_id, friend_ids);
		
		if(requester == null)
		{
			throw new NotFoundException(NOT_FOUND_USER);
		}
		
		Long id = friend_ids[0];
		
		return Response.ok(id).build();
		
	}
	
	/**
	 * Alle Freunde auslesen die noch nicht zu meiner Party eingeladen wurden
	 * 
	 * --> Alle Freunde die weder Status ZUSAGE, OFFEN, ABSAGE zu dieser Party haben
	 * 
	 * @param party_id
	 * @return List<Nutzer>
	 */
	@GET
	@Path("/friends/notInvited/{id:[1-9][0-9]*}")
	@Consumes(APPLICATION_JSON)
	public Response findNotInvitedFriendsByPartyId(@PathParam("id") Long party_id) {
		
		//Party auslesen und anschließend Veranstalter = Nutzer-Objekt auslesen
		final Party party = ps.findPartyById(party_id);
		if (party == null) {
			throw new NotFoundException(NOT_FOUND_PARTY, party_id);
		}
		final Nutzer veranstalter = party.getVeranstalter();
		
		final List<Nutzer> notInvitedFriends = ns.findNotInvitedFriendsByParty(veranstalter, party);
		
		return Response.ok(notInvitedFriends).build();
		
	}
	
	
	@Deprecated
	@POST
	@Consumes(APPLICATION_JSON)
	@Produces({APPLICATION_JSON, APPLICATION_XML})
	@Transactional
	public Response registrateUser(@Valid Nutzer nutzer) {
		
		
		nutzer = ns.registrateUser(nutzer);
		//TODO EmailExists Exception anhand Jürgen Stack einbinden
		// Erstmal nur ein HTTP-OK zurückgeben
		return Response.ok(nutzer).build();
				
				
		
	}
	
	
	
	@PUT
	@Consumes(APPLICATION_JSON)
	@Produces({APPLICATION_JSON, APPLICATION_XML})
	@Transactional
	public Response updateUser(Nutzer nutzer) {
		
				
		nutzer = ns.updateUser(nutzer);
		if (nutzer == null) {
			throw new NotFoundException(NOT_FOUND_USER);
		}
	
		return Response.ok(nutzer).build();
				
				
		
	}
	
	
	@GET
	@Path("/profilepicture/{id:[1-9][0-9]*}")
	public Response getProfilePicture(@PathParam("id") Long id) {
		
		ProfilePicture profilepicture;
		
		profilepicture = ns.findMyProfilePicture(id);
		
		if(profilepicture ==  null) {
			throw new NoSuchElementException();
		}
		
		
		return Response.ok(profilepicture).build();
	}
	
	
	
	@POST
	@Path("/profilepicture")
	@Consumes(APPLICATION_JSON)
	@Produces({APPLICATION_JSON, APPLICATION_XML})
	@Transactional
	public Response addProfilePicture(ProfilePicture profilepicture) {
		
		if(profilepicture == null)
		{
			return null;
		}

		profilepicture = ns.addProfilePicture(profilepicture);
		
		
	
		return Response.ok(profilepicture).build();
				
				
		
	}
	
	/**
	 * Parties auslesen anhand der Veranstalter ID die noch offen sind
	 * 
	 * Wird benötigt um Freunde nachträglich noch einzuladen
	 * 
	 * @param nutzerId
	 * @return List<Party>
	 */
	@GET
	@Path("{id:[1-9][0-9]*}/meineOffenenPartyVeranstaltungen")
	public Response findOpenPartiesByNutzerId(@PathParam("id") Long nutzerId) {
		final Nutzer nutzer = ns.findNutzerById(nutzerId);
		
		if (nutzer == null) {
			throw new NotFoundException(NOT_FOUND_USER, nutzerId);
		}
		
		final List<Party> parties = ps.findOpenPartiesByNutzer(nutzer);
		
		return Response.ok(parties).build();
	}
	
	/**
	 * Alle Parties auslesen die in der Vergangenheit liegen und man selbst veranstaltet hat
	 * 
	 * Methode für Rating
	 * 
	 * @param nutzerId
	 * @return List<Party>
	 */
	@GET
	@Path("{id:[1-9][0-9]*}/meineGeschlossenenPartyVeranstaltungen")
	public Response findClosedPartiesByNutzerId(@PathParam("id") Long nutzerId) {
		final Nutzer veranstalter = ns.findNutzerById(nutzerId);
		
		if (veranstalter == null) {
			throw new NotFoundException(NOT_FOUND_USER, nutzerId);
		}
		
		final List<Party> parties = ps.findClosedPartiesByNutzer(veranstalter);
		
		return Response.ok(parties).build();
	}
	
	/**
	 * Alle Parties zu denen man eingeladen wurde. Nur die Parties anzeigen die noch stattfinden.
	 * 
	 * @param nutzerId
	 * @return List<Party> mit Status.Type=OFFEN
	 */
	@GET
	@Path("{id:[1-9][0-9]*}/offeneEinladungen")
	public Response findOffeneEinladungenByNutzerId(@PathParam("id") Long nutzerId) {
		final Nutzer nutzer = ns.findNutzerById(nutzerId);
		
		uriHelperNutzer.updateUriNutzer(nutzer, uriInfo);
		
		final List<Party> parties = ps.findOffeneEinladungenByNutzer(nutzer);
		
			
		return Response.ok(parties).build();
		
	}
	
	/**
	 * Offene Einladung zur Party annehmen
	 * 
	 * @param nutzerId
	 * @param party
	 * @return Response.noContent() (HTTP: 204)
	 */
	@PUT
	@Path("{id:[1-9][0-9]*}/offeneEinladungen/zusagen")
	@Consumes(APPLICATION_JSON)
	@Produces
	public Response partyZusagen(@PathParam("id") Long nutzerId, Party party) {
		
		
		
		final Nutzer nutzer = ns.findNutzerById(nutzerId);
		
		//TODO exception message key
		if (nutzer == null || party == null) {
			throw new NotFoundException("Sie wurden zu dieser Party nicht eingeladen.");
		}
		
		// Zusagen
		ps.partyZusagen(nutzer, party);
		
		return Response.noContent().build();
	}
	
	/**
	 * Eine Party-Einladung ablehnen (=absagen)
	 * 
	 * @param nutzerId
	 * @param party
	 * @return Response.noContent() - 204
	 */
	@PUT
	@Path("{id:[1-9][0-9]*}/offeneEinladungen/absagen")
	@Consumes(APPLICATION_JSON)
	@Produces
	public Response offenePartyeinladungAbsagen(@PathParam("id") Long nutzerId, Party party) {
		
		
		
		final Nutzer nutzer = ns.findNutzerById(nutzerId);
		
		//TODO exception message key
		if (nutzer == null || party == null) {
			throw new NotFoundException("Sie wurden zu dieser Party nicht eingeladen.");
		}
		
		// Zusagen
		ps.partyAbsagen(nutzer, party);
		
		return Response.noContent().build();
	}
	
	
	
	/**
	 * Alle Parties anzeigen zu denen man zugesagt hat
	 * 
	 * Es werden nur die Parties ausgelesen, die noch stattfinden (=zukünftig)
	 * 
	 * @param nutzerId
	 * @return
	 */
	@GET
	@Path("{id:[1-9][0-9]*}/zugesagteParties")
	public Response findZugesagtePartiesyNutzerId(@PathParam("id") Long nutzerId) {
		final Nutzer nutzer = ns.findNutzerById(nutzerId);
		
		uriHelperNutzer.updateUriNutzer(nutzer, uriInfo);
		
		final List<Party> parties = ps.findZugesagtePartiesByNutzer(nutzer);
		
		
		
		return Response.ok(parties).build();
		
	}
	
	/**
	 * Zu einer schon zugesagten Party absagen 
	 * Hinweis: Nur in Kombination mit voherigem GET nutzen, da dies garantiert, dass nur Parties abgesagt werden
	 * die noch in der Zukunft liegen
	 * 
	 * @param nutzerId
	 * @param party
	 * @return no Content - 204
	 */
	@PUT
	@Path("{id:[1-9][0-9]*}/zugesagteParties/absagen")
	@Consumes(APPLICATION_JSON)
	@Produces
	public Response zugesagtePartyAbsagen(@PathParam("id") Long nutzerId, Party party) {
		
		
		
		final Nutzer nutzer = ns.findNutzerById(nutzerId);
		
		//TODO exception message key
		if (nutzer == null || party == null) {
			throw new NotFoundException("Sie wurden zu dieser Party nicht eingeladen.");
		}
		
		// Zusagen
		ps.partyAbsagen(nutzer, party);
		
		return Response.noContent().build();
	}
	
	/**
	 * Alle Parties auslesen, die man abgesagt hat und deren Datum noch in der Zukunft liegt.
	 * 
	 * --> Danach eventuell wieder zusagen
	 * 
	 * NFE wenn Benutzer nicht gefunden wird
	 * 
	 * @param nutzerId
	 * @return List<Party>
	 * 
	 * @throws NotFoundException
	 */
	@GET
	@Path("{id:[1-9][0-9]*}/abgesagteParties")
	public Response findAbgesagtePartiesyNutzerId(@PathParam("id") Long nutzerId) {
		final Nutzer nutzer = ns.findNutzerById(nutzerId);
		if (nutzer == null) {
			throw new NotFoundException(NOT_FOUND_USER, nutzerId);
		}
		final List<Party> parties = ps.findAbgesagtePartiesByNutzer(nutzer);
		
		return Response.ok(parties).build();
		
	}	
	
	/**
	 * Zu einer schon abgesagten Party zusagen.
	 * Hinweis: Nur in Kombination mit voherigem GET nutzen, da dies garantiert, dass nur Parties wieder 
	 * zugesagtwerden die noch in der Zukunft liegen
	 * 
	 * @param nutzerId
	 * @param party
	 * @return no Content - 204
	 */
	@PUT
	@Path("{id:[1-9][0-9]*}/abgesagteParties/zusagen")
	@Consumes(APPLICATION_JSON)
	@Produces
	public Response abgesagtePartyZusagen(@PathParam("id") Long nutzerId, Party party) {
		
		
		
		final Nutzer nutzer = ns.findNutzerById(nutzerId);
		
		//TODO exception message key
		if (nutzer == null || party == null) {
			throw new NotFoundException("Sie wurden zu dieser Party nicht eingeladen.");
		}
		
		// Zusagen
		ps.partyZusagen(nutzer, party);
		
		return Response.noContent().build();
	}
	
	/**
	 * Alle Parties anzeigen an denen der User teilgenommen hat
	 * 
	 * Es werden nur die Parties ausgelesen, die schon stattgefunden habe (Datum=in der Vergangenheit)
	 * und die noch nicht bewertet wurden
	 * 
	 *  
	 * @param nutzerId
	 * @return List<Party>
	 */
	@GET
	@Path("{id:[1-9][0-9]*}/PartiesIAttended")
	public Response findAttendedPartiesyNutzerId(@PathParam("id") Long nutzerId) {
		final Nutzer nutzer = ns.findNutzerById(nutzerId);
		
				
		final List<Party> parties = ps.findPartiesIAttendedByNutzer(nutzer);
		
		
		
		return Response.ok(parties).build();
		
	}
	/**
	 * Alle Parties anzeigen an denen der User teilgenommen hat
	 * 
	 * Es werden nur die Parties ausgelesen, die schon stattgefunden habe (Datum=in der Vergangenheit)
	 * und die noch nicht bewertet wurden
	 * 
	 * Hilfsmethode um anschließend ein Rating auf eine Party abzusetzen
	 * 
	 * @param nutzerId
	 * @return List<Party>
	 */
	@GET
	@Path("{id:[1-9][0-9]*}/PartiesIAttendedNotRated")
	public Response findAttendedPartiesToRateByNutzerId(@PathParam("id") Long nutzerId) {
		final Nutzer nutzer = ns.findNutzerById(nutzerId);
		
				
		final List<Party> parties = ps.findPartiesIAttendedNotRatedByNutzer(nutzer);
		
		
		
		return Response.ok(parties).build();
		
	}

	/**
	 * Alle Ratings auslesen, die der übermittelte User veranstaltet hat.
	 * Methode für Profil, kumuliertes Rating über alle Parties
	 * 
	 * @param veranstalterId
	 * @return List<Rating>
	 */
	@GET
	@Path("{id:[1-9][0-9]*}/myParties/ratings")
	public Response getRankingsToMyParties(@PathParam("id") Long veranstalterId) {
		final Nutzer veranstalter = ns.findNutzerById(veranstalterId);
		
		if (veranstalter == null) {
			throw new NotFoundException(NOT_FOUND_USER, veranstalterId);
		}
		
		final List<Party> parties = ps.findClosedPartiesByNutzer(veranstalter);
		
		if (parties == null || parties.isEmpty()) {
			return null;
		}
		
		final List<Rating> ratings = ns.findRatingsToVeranstalter(veranstalter, parties);
		
		
		return Response.ok(ratings).build();
		
		
		
	}
		
}

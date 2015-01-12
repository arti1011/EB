package de.party.party.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import de.party.nutzer.domain.Nutzer;
import de.party.nutzer.service.NutzerService;
import de.party.party.domain.FreundeHolder;
import de.party.party.domain.Party;
import de.party.party.domain.ListenHolder;
import de.party.item.domain.PartyItem;
import de.party.party.domain.Rating;

import de.party.party.service.PartyService;
import de.party.util.rest.NotFoundException;
import static de.party.util.Constants.KEINE_ID;


@Path("/party")
@Produces(APPLICATION_JSON)
@Consumes
@Transactional
@RequestScoped
public class PartyResource {

	
	//Konstanten für Exceptions
	private static final String NOT_FOUND_PARTY = "party.notFound";
	
	
	@Inject
	private UriHelperParty uriHelperParty;
	
	@Context
	private UriInfo uriInfo;
	
	@Inject
	private PartyService ps;
	
	@Inject
	private NutzerService ns;
	
		
	/**
	 * Party anhand einer ID suchen
	 * 
	 * @param id
	 * @return Party
	 * 
	 * @throws NotFoundException
	 */
	@GET
	@Path("{id:[1-9][0-9]*}")
	public Response findPartyById(@PathParam("id") Long id) {
		final Party party = ps.findPartyById(id);
		
		
		if (party == null) {
			throw new NotFoundException(NOT_FOUND_PARTY, id);
		}
		
		uriHelperParty.updateUriParty(party, uriInfo);
		
		return Response.ok(party).build();
	}
	
	@GET
	@Path("{id:[1-9][0-9]*}/einkaufliste/offen")
	public Response findPartyItemsById(@PathParam("id") Long id) {
		
		final List<PartyItem> items = ps.findPartyItemsById(id);
		
		
		if (items == null || items.isEmpty()) {
			throw new BadRequestException("Es wurden keine Items gefunden");
		}
		
		
		return Response.ok(items).build();
	}
	
	@GET
	@Path("{id:[1-9][0-9]*}/listen")
	public Response findPartyListenByUserId(@PathParam("id") Long id) {
		
		
		final Nutzer nutzer = ns.findNutzerById(id);
		
		final ListenHolder holder = ps.findPartyListenById(nutzer);
		
		
		if (holder == null) {
			throw new BadRequestException("Es wurde keine Liste gefunden");
			
		}
		
		
		return Response.ok(holder).build();
	}
	
	
	@GET
	@Path("{id:[1-9][0-9]*}/listenteilnehmer")
	public Response findPartyListenTeilnehmerByUserId(@PathParam("id") Long id) {
		
		
		final Party party = ps.findPartyById(id);
		
		final FreundeHolder holder = ns.findPartyFreundeListenById(party);
		
		
		if (holder == null) {
			throw new BadRequestException("Es wurde keine Liste gefunden");
			
		}
		
		
		return Response.ok(holder).build();
	}
	
	
	
	@GET
	@Path("{id:[1-9][0-9]*}/einkaufliste/ichbringe/{userId:[1-9][0-9]*}")
	public Response findPartyItemsById(@PathParam("id") Long id, @PathParam("userId") Long userId) {
		
		final List<PartyItem> items = ps.findPartyItemsByMitbringerId(id, userId);
		
		
		if (items == null || items.isEmpty()) {
			throw new BadRequestException("Es wurden keine Items gefunden");
		}
		
		
		return Response.ok(items).build();
	}
	
	
	@GET
	@Path("{id:[1-9][0-9]*}/einkaufliste/werbringtwas")
	public Response findPartyItemsByIdOwner(@PathParam("id") Long id) {
		
	final List<PartyItem> items = ps.findPartyItemsByIdOwner(id);
		
		
		if (items == null || items.isEmpty()) {
			throw new BadRequestException("Es wurden keine Items gefunden");
		}
		
		
		return Response.ok(items).build();
	}
	
	/**
	 * Alle eingeladenen Benutzer zur gegebenen PartyId auslesen
	 * 
	 * @param id
	 * @return List<Nutzer> 
	 */
	@GET
	@Path("{id:[1-9][0-9]*}/eingeladen")
	public Response findEinladungenByPartyId(@PathParam("id") Long id) {
		//TODO Methode einbinden
		return null;
		
	}
	

	/**
	 * Teilnehmer an einer Party je nach Status {OFFEN, ZUSAGE, ABSAGE} auslesen
	 * 
	 * @param id
	 * @return List<{@link Nutzer}>
	 * 
	 * @throws NotFoundException
	 */
	@GET
	@Path("{id:[1-9][0-9]*}/teilnehmer/eingeladen")
	public Response findEingeladeneTeilnehmerByPartyId(@PathParam("id") Long id) {
		final Party party = ps.findPartyById(id);
		
		if (party == null) {
			throw new NotFoundException(NOT_FOUND_PARTY, id);
		}
		
		final List<Nutzer> teilnehmer = ns.findEingeladeneTeilnehmerByParty(party);
		
		
		return Response.ok(teilnehmer).build();
		
	}
	
	/**
	 * Alle Teilnehmer auslesen die zu einer Party zugesagt haben
	 * 
	 * @param id
	 * @return List<{@link Nutzer}>
	 * 
	 * @throws NotFoundException
	 */
	@GET
	@Path("{id:[1-9][0-9]*}/teilnehmer/zugesagt")
	public Response findZugesagteTeilnehmerByPartyId(@PathParam("id") Long id) {
		final Party party = ps.findPartyById(id);
	
		if (party == null) {
			throw new NotFoundException(NOT_FOUND_PARTY, id);
		}
		
		final List<Nutzer> teilnehmer = ns.findZugesagteTeilnehmerByParty(party);
		
		
		return Response.ok(teilnehmer).build();
		
	}
	
	/**
	 * Alle Teilnehmer auslesen die zu einer Party abgesagt haben
	 * 
	 * @param id
	 * @return List<{@link Nutzer}>
	 * 
	 * @throws NotFoundException
	 */
	@GET
	@Path("{id:[1-9][0-9]*}/teilnehmer/abgesagt")
	public Response findAbgesagteTeilnehmerByPartyId(@PathParam("id") Long id) {
		final Party party = ps.findPartyById(id);
		
		if (party == null) {
			throw new NotFoundException("");
		}
		
		final List<Nutzer> teilnehmer = ns.findAbgesagteTeilnehmerByParty(party);
		
		
		return Response.ok(teilnehmer).build();
		
	}
	
	/**
	 * Eine neue Party erstellen
	 * 
	 * Constraints: Veranstalter-ID notNull, Datum in der Zukunft
	 * 
	 * Sonst. Prüfungen: Check ob Veranstalter auch existiert
	 * 
	 * 
	 * @param party
	 * @return Response.created(HTTP: 201), URI zum neuen Datensatz im Response Header
	 * 
	 * @throws BadRequestException
	 */
	@POST
	@Consumes(APPLICATION_JSON)
	@Produces({APPLICATION_JSON, APPLICATION_XML})
	public Response createParty(@Valid Party party, List<Nutzer> teilnehmer) {
		
		// ID zurücksetzen
		party.setId(KEINE_ID);
		
		party = ps.createParty(party);
		
		
				
		//final URI partyUri = uriHelperParty.getUriParty(party, uriInfo);
		
		return Response.ok(party).build();
		
	}
	
	/**
	 * Freunde zur Party einladen. 
	 * 
	 *  
	 * @param partyId
	 * @param nutzer
	 * @return Response.noContent() - HTTP 204
	 */
	@PUT
	@Path("{id:[1-9][0-9]*}/invite")
	@Consumes(APPLICATION_JSON)
	@Produces
	@Transactional
	public Response inviteFriendsToParty(@PathParam("id") Long partyId, List<Nutzer> friends) {
		
		// Party-Objekt auslesen
		final Party party = ps.findPartyById(partyId);
		if (party == null) {
			throw new NotFoundException(NOT_FOUND_PARTY, partyId);
		}
		if (friends == null || friends.isEmpty()) {
			throw new BadRequestException("Es wurden keine Freunde zum einladen übergeben");
		}
		
		//Persistente Nutzer-Objekte ermitteln
				
		for (Nutzer nutzer : friends) {
			final Nutzer persistenterNutzer = ns.findNutzerById(nutzer.getId());
			if (persistenterNutzer != null) {
				ps.inviteFriendToParty(party, persistenterNutzer);
				
			}
			
		}
		
		
				
		return Response.ok("1").build();
		
		
	}
	/**
	 * Eine Party bewerten. 
	 * 
	 * Vor dieser Methode die Party-Objekte auslesen, an denen
	 * der Nutzer teilgenommen hat und noch nicht geratet hat
	 * -> HilfsMethode /rest/user/id:[..]/PartiesIAttendedNotRated
	 * 
	 * 
	 * @param ranking
	 * @return Response.created
	 */
	@POST
	@Path("/bewerten")
	@Consumes(APPLICATION_JSON)
	@Transactional
	public Response rateParty(Rating rating) {
		//ID zurücksetzen
		rating.setId(KEINE_ID);
		
		rating = ps.bewerteParty(rating);
		return Response.created(null).build();
	}

	
	@PUT
	@Path("{id:[1-9][0-9]*}/addeinkaufliste")
	@Consumes(APPLICATION_JSON)
	@Produces
	@Transactional
	public Response addEinkauflisteToParty(@PathParam("id") Long partyId, List<PartyItem> items) {
		
		// Party-Objekt auslesen
		final Party party = ps.findPartyById(partyId);
		if (party == null) {
			throw new NotFoundException(NOT_FOUND_PARTY, partyId);
		}
		if (items == null || items.isEmpty()) {
			throw new BadRequestException("Es wurden keine Items zum einkaufen übergeben");
	
		}
		
		for(PartyItem i : items) {
			
			i.setParty_fk(party.getId());
			
		}
		
					
	
				ps.addEinkauflisteToParty(items);
		
		
		
				
		return Response.ok("1").build();
		
		
	}
	
	
	@PUT
	@Path("{id:[1-9][0-9]*}/mitbringen")
	@Consumes(APPLICATION_JSON)
	@Produces
	@Transactional
	public Response mitbringenToParty(@PathParam("id") Long userId, List<PartyItem> items) {
		
		// Party-Objekt auslesen
		final Nutzer user = ns.findNutzerById(userId);
		if (user == null) {
			throw new NotFoundException(NOT_FOUND_PARTY, userId);
		}
		if (items == null || items.isEmpty()) {
			throw new BadRequestException("Es wurden keine Items zum mitbringen übergeben");
	
		}
		
		String mitbringer = user.getVorname()+" "+user.getNachname();
		Long mitbringer_fk = user.getId();
		
		ps.mitbringenToParty(mitbringer, mitbringer_fk, items);

				
		return Response.ok("1").build();

	}
	



	/**
	 * Alle Rating Objekte zu einer Party-ID auslesen
	 * 
	 * @param id
	 * @return List<Rating>
	 */

	@GET
	@Path("{id:[1-9][0-9]*}/rating")
	public Response getRatingToPartyId(@PathParam("id") Long id) {
		
		//Party-Objekt ermitteln
		final Party party = ps.findPartyById(id);
		
		final List<Rating> ratings = ps.getRatingToParty(party);
		
		return Response.ok(ratings).build();
	}
	
	
}


package de.party.party.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import de.party.nutzer.domain.Nutzer;
import de.party.nutzer.service.NutzerService;
import de.party.party.domain.Party;
import de.party.party.domain.PartyTeilnahme;
import de.party.party.service.PartyService;
import de.party.util.rest.NotFoundException;


@Path("/party")
@Produces(APPLICATION_JSON)
@Consumes
@Transactional
@RequestScoped
public class PartyResource {

	@Inject
	private UriHelperParty uriHelperParty;
	
	@Context
	private UriInfo uriInfo;
	
	@Inject
	private PartyService ps;
	
	@Inject
	private NutzerService ns;
		
	@GET
	@Path("{id:[1-9][0-9]*}")
	public Response findPartyById(@PathParam("id") Long id) {
		final Party party = ps.findPartyById(id);
		
		//TODO key in messages einbinden
		if (party == null) {
			throw new NotFoundException("Keine Party mit der ID " + id + " gefunden.");
		}
		
		uriHelperParty.updateUriParty(party, uriInfo);
		return Response.ok(party).build();
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
	 * @return
	 */
	@GET
	@Path("{id:[1-9][0-9]*}/teilnehmer/eingeladen")
	public Response findEingeladeneTeilnehmerByPartyId(@PathParam("id") Long id) {
		final Party party = ps.findPartyById(id);
		//TODO NotFoundException msg einbinden
		if (party == null) {
			throw new NotFoundException("");
		}
		//TODO hier weitermachen
		final List<Nutzer> teilnehmer = ns.findEingeladeneTeilnehmerByParty(party);
		
		
		return Response.ok(teilnehmer).build();
		
	}
	
	
	}

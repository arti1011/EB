package de.party.party.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import de.party.party.domain.Party;
import de.party.party.service.PartyService;
import de.party.util.rest.NotFoundException;


@Path("/party")
@Produces(APPLICATION_JSON)
@Consumes
@RequestScoped
public class PartyResource {

	@Inject
	private UriHelperParty uriHelperParty;
	
	@Context
	private UriInfo uriInfo;
	
	@Inject
	private PartyService ps;
	
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
	
	
	
	}

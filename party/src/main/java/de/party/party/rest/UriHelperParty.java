package de.party.party.rest;

import java.net.URI;


import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import de.party.party.domain.Party;

@ApplicationScoped
public class UriHelperParty {
	
	 
	public void updateUriParty(Party party, UriInfo uriInfo) {
		//URI der eingeladenen Nutzer
		final UriBuilder einladungenUriBuilder = uriInfo.getBaseUriBuilder()
									 .path(PartyResource.class)
									 .path(PartyResource.class, 
									 "findEingeladeneTeilnehmerByPartyId");
		
		final URI eingeladenUri = einladungenUriBuilder.build(party.getId());
		
		//URI der Teilnehmer die zugesagt haben
		final UriBuilder zusagenUriBuilder = uriInfo.getBaseUriBuilder()
				 									.path(PartyResource.class)
													 .path(PartyResource.class, 
													 "findZugesagteTeilnehmerByPartyId");
		final URI zusagenUri = zusagenUriBuilder.build(party.getId());
		
		//URI der Teilnehmer die abgesagt haben
		final UriBuilder absagenUriBuilder = uriInfo.getBaseUriBuilder()
													.path(PartyResource.class)
													.path(PartyResource.class, 
													"findAbgesagteTeilnehmerByPartyId");
		final URI absagenUri = absagenUriBuilder.build(party.getId());
		
		party.setEinladungenUri(eingeladenUri);
		party.setZusagenUri(zusagenUri);
		party.setAbsagenUri(absagenUri);
	}
	
	public URI getUriParty(Party party, UriInfo uriInfo) {
		final UriBuilder ub = uriInfo.getBaseUriBuilder()
									 .path(PartyResource.class)
									 .path(PartyResource.class, "findPartyById");
		final URI uri = ub.build(party.getId());
		return uri;
									 
	}

}

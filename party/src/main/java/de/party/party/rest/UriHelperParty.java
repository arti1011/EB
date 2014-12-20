package de.party.party.rest;

import java.net.URI;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import de.party.nutzer.domain.Nutzer;
import de.party.nutzer.rest.UriHelperNutzer;
import de.party.party.domain.Party;

@ApplicationScoped
public class UriHelperParty {
	
	@Inject
	private UriHelperNutzer uriHelperNutzer;
	
	//TODO 
	public void updateUriParty(Party party, UriInfo uriInfo) {
//		final UriBuilder ub = uriInfo.getBaseUriBuilder()
//									 .path(NutzerResource.class)
//									 .path(PartyResource.class, 
//									 "findPartyById");
//		final URI partyUri = 
	}
	
	public URI getUriParty(Party party, UriInfo uriInfo) {
		final UriBuilder ub = uriInfo.getBaseUriBuilder()
									 .path(PartyResource.class)
									 .path(PartyResource.class, "findPartyById");
		final URI uri = ub.build(party.getId());
		return uri;
									 
	}

}

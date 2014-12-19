package de.party.nutzer.rest;

import java.net.URI;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import de.party.nutzer.domain.Freundschaft;
import de.party.nutzer.domain.Nutzer;

@ApplicationScoped
public class UriHelperNutzer {
	public URI getUriNutzer(Nutzer nutzer, UriInfo uriInfo)
	{
		final UriBuilder ub = uriInfo.getBaseUriBuilder()
									.path(NutzerResource.class)
									.path(NutzerResource.class, "findNutzerById");
		final URI nutzerUri = ub.build(nutzer.getId());
		return nutzerUri;
	}
	
	public void updateUriNutzer(Nutzer nutzer, UriInfo uriInfo) {
		final UriBuilder ub = uriInfo.getBaseUriBuilder()
									 .path(NutzerResource.class)
									 .path(NutzerResource.class, "findFriendsByNutzerId");
		
		final URI friendsUri = ub.build(nutzer.getId());

		
		final UriBuilder ub_party = uriInfo.getBaseUriBuilder()
										   .path(NutzerResource.class)
										   .path(NutzerResource.class, "findPartiesByNutzerId");
		final URI partyUri = ub_party.build(nutzer.getId());
		
		
		nutzer.setPartyUri(partyUri);
		nutzer.setFriendsUri(friendsUri);
	}
}

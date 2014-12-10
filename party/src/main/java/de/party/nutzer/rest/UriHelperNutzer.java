package de.party.nutzer.rest;

import java.net.URI;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

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
									 .path(NutzerResource.class, "findFriendsById");
		final URI friendsUri = ub.build(nutzer.getId());
		
		nutzer.setFriendsUri(friendsUri);
	}
}

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
	
	//TODO Uri Freunde noch anpassen 
	public void updateUriNutzer(Nutzer nutzer, UriInfo uriInfo) {
	
		// Meine Party Zusagen anzeigen
		final UriBuilder ub_partyZusagen = uriInfo.getBaseUriBuilder()
										   .path(NutzerResource.class)
										   .path(NutzerResource.class, "findZugesagtePartiesyNutzerId");
		final URI partyZusagenUri = ub_partyZusagen.build(nutzer.getId());
		
		// Meine Party Absagen
		final UriBuilder ub_partyAbsagen = uriInfo.getBaseUriBuilder()
												  .path(NutzerResource.class)
												  .path(NutzerResource.class, "findAbgesagtePartiesyNutzerId");
		final URI partyAbsagenUri = ub_partyAbsagen.build(nutzer.getId());
		
		// Meine offenen Party- Einladungen
		final UriBuilder ub_offenePartyEinladungen = uriInfo.getBaseUriBuilder()
				  								  .path(NutzerResource.class)
				  								  .path(NutzerResource.class, "findAbgesagtePartiesyNutzerId");
		final URI partyOffenUri = ub_offenePartyEinladungen.build(nutzer.getId());
		
		nutzer.setMeinePartyEinladungenUri(partyOffenUri);
		nutzer.setMeinePartyZusagenUri(partyZusagenUri);
		nutzer.setMeinePartyAbsagenUri(partyAbsagenUri);
		
		
	}
}

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
	
	public void updateUriParty(Party party, UriInfo uriInfo) {
		// URL für Veranstalter setzen
		final Nutzer veranstalter = party.getVeranstalter();
		
		
		
//		if (veranstalter != null) {
//			final URI veranstalterUri = uriHelperNutzer
//									.getUriNutzer(party.getVeranstalter(), uriInfo);
//			
//			party.setVeranstalterUri(veranstalterUri);
//			
//		}
		
		}
//		// TODO URLs für Teilnehmer
//		final List<Nutzer> teilnehmer = party.getTeilnehmer();
//		if (teilnehmer != null && !teilnehmer.isEmpty()) {
//			for (Nutzer nutzer : teilnehmer) {
//				final URI nutzerUri = uriHelperNutzer.
//							getUriNutzer(nutzer, uriInfo);
//				party.setNutzerUri(nutzerUri);
//							
//			}
//		}
	
	
	public URI getUriParty(Party party, UriInfo uriInfo) {
		final UriBuilder ub = uriInfo.getBaseUriBuilder()
									 .path(PartyResource.class)
									 .path(PartyResource.class, "findPartyById");
		final URI uri = ub.build(party.getId());
		return uri;
									 
	}

}

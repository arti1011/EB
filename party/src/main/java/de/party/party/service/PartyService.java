package de.party.party.service;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.BadRequestException;



import de.party.nutzer.domain.Nutzer;
import de.party.party.domain.Party;
import de.party.party.domain.PartyTeilnahme;
import de.party.party.domain.StatusType;

public class PartyService {

//	private static final Logger LOGGER = Logger.getLogger(PartyService.class);
	
	@PersistenceContext
	private EntityManager em;
	
	public Party findPartyById(Long id) {

		Party party = em.find(Party.class, id);
		
		
		
		return party;
	}

	public List<Party> findOpenPartiesByNutzer(Nutzer veranstalter) {
		if (veranstalter == null) {
			return Collections.emptyList();
			
		}
		final List<Party> parties = em.createNamedQuery(Party.FIND_OPEN_PARTIES_BY_VERANSTALTER, 
										Party.class)
									  .setParameter(Party.PARAM_NUTZER, veranstalter)
									  .getResultList();
		return parties;
	}
	
	public List<Party> findClosedPartiesByNutzer(Nutzer veranstalter) {

		if (veranstalter == null) {
			return Collections.emptyList();
		}
		final List<Party> parties = em.createNamedQuery(Party.FIND_CLOSED_PARTIES_BY_VERANSTALTER,
										Party.class)
									  .setParameter(Party.PARAM_NUTZER, veranstalter)
									  .getResultList();
		
		return parties;
	}
	

	public List<Party> findOffeneEinladungenByNutzer(Nutzer nutzer) {
		if (nutzer == null) {
			return Collections.emptyList();
		}
		final List<Party> offeneEinladungen = em.createNamedQuery(Party.FIND_OFFENE_EINLADUNGEN_BY_NUTZER, Party.class)
																	   .setParameter(Party.PARAM_TEILNEHMER, nutzer)
																	   .setParameter(Party.PARAM_STATUS, StatusType.OFFEN)
																	   .getResultList();
		
		
		return offeneEinladungen;
	}

	public void partyZusagen(Nutzer nutzer, Party party) {
		
		final PartyTeilnahme partyTeilnahme = em.createNamedQuery(PartyTeilnahme.FIND_PARTY_TEILNAHME, PartyTeilnahme.class)
																				.setParameter(PartyTeilnahme.PARAM_TEILNEHMER, nutzer)
																				.setParameter(PartyTeilnahme.PARAM_PARTY, party)
																				.getSingleResult();
		partyTeilnahme.setStatus(StatusType.ZUSAGE);
		
		em.merge(partyTeilnahme);
		
	}

	
	public List<Party> findZugesagtePartiesByNutzer(Nutzer nutzer) {
		if (nutzer == null) {
			return Collections.emptyList();
		}
		final List<Party> zugesagteParties = em.createNamedQuery(Party.FIND_ZUGESAGTE_PARTIES_BY_NUTZER, Party.class)
																	   .setParameter(Party.PARAM_TEILNEHMER, nutzer)
																	   .setParameter(Party.PARAM_STATUS, StatusType.ZUSAGE)
																	   .getResultList();
		
		
		return zugesagteParties;
	}
	
	public void partyAbsagen(Nutzer nutzer, Party party) {
		
		final PartyTeilnahme partyTeilnahme = em.createNamedQuery(PartyTeilnahme.FIND_PARTY_TEILNAHME, PartyTeilnahme.class)
												.setParameter(PartyTeilnahme.PARAM_TEILNEHMER, nutzer)
												.setParameter(PartyTeilnahme.PARAM_PARTY, party)
												.getSingleResult();
		partyTeilnahme.setStatus(StatusType.ABSAGE);

		em.merge(partyTeilnahme);
		
	}
	
	public List<Party> findAbgesagtePartiesByNutzer(Nutzer nutzer) {
		if (nutzer == null) {
			return Collections.emptyList();
		}
		final List<Party> abgesagteParties = em.createNamedQuery(Party.FIND_ABGESAGTE_PARTIES_BY_NUTZER, Party.class)
											   .setParameter(Party.PARAM_TEILNEHMER, nutzer)
											   .setParameter(Party.PARAM_STATUS, StatusType.ABSAGE)
											   .getResultList();
		return abgesagteParties;
	}
	

	public Party createParty(Party party) {
		
		if (party == null) {
			return party;
		}
		
		
		//Prüfen ob ein Veranstalter eingetragen ist und dieser auch in der DB existiert
		final Nutzer veranstalter = em.find(Nutzer.class, party.getVeranstalter().getId());
		
		if (veranstalter == null) {
			throw new BadRequestException("Veranstalter nicht in der DB gefunden");
		}
				
		em.persist(party);
		
					
		
		return party;
		
	}

	public void inviteFriendToParty(Party party, Nutzer freund) {
		
		final PartyTeilnahme partyTeilnahme = new PartyTeilnahme(party, freund);
		
		em.persist(partyTeilnahme);
		
		
	}

	

	

	
}

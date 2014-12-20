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

	@PersistenceContext
	private EntityManager em;
	
	public Party findPartyById(Long id) {

		Party party = em.find(Party.class, id);
		
		return party;
	}

	public List<Party> findPartiesByNutzer(Nutzer nutzer) {
		if (nutzer == null) {
			return Collections.emptyList();
			
		}
		final List<Party> parties = em.createNamedQuery(Party.FIND_PARTIES_BY_NUTZER, 
										Party.class)
									  .setParameter(Party.PARAM_NUTZER, nutzer)
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

	public void offeneEinladungZusagen(Nutzer nutzer, Party party) {
		
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

	
}

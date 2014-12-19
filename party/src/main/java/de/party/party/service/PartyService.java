package de.party.party.service;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.party.nutzer.domain.Nutzer;
import de.party.party.domain.Party;
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

}

package de.party.party.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.BadRequestException;





import de.party.nutzer.domain.Nutzer;
import de.party.party.domain.Party;
import de.party.party.domain.ListenHolder;
import de.party.item.domain.PartyItem;
import de.party.party.domain.FreundeHolder;
import de.party.party.domain.PartyTeilnahme;
import de.party.party.domain.Rating;
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
	
	public void addEinkauflisteToParty(List<PartyItem> items) {
		
		
		for(PartyItem i : items) {
			em.persist(i);
			
		}
		
		
	}
	
	public List<PartyItem> findPartyItemsById(Long id) {

		
		List<PartyItem> list = em.createQuery(
			    "SELECT p FROM PartyItem p WHERE p.party_fk LIKE :partyFK AND p.mitbringer IS NULL")
			    .setParameter("partyFK", id)
			    .getResultList();
		
		
		
		return list;
		
		
	}
	
	
	public List<PartyItem> findPartyItemsByIdOwner(Long id) {
		
		List<PartyItem> list = em.createQuery(
			    "SELECT p FROM PartyItem p WHERE p.party_fk LIKE :partyFK AND p.mitbringer IS NOT NULL")
			    .setParameter("partyFK", id)
			    .getResultList();
		
		
		
		return list;
		
		
	}
	
	public List<PartyItem> findPartyItemsByMitbringerId(Long partyId, Long userId) {
		
		List<PartyItem> list = em.createQuery(
			    "SELECT p FROM PartyItem p WHERE p.party_fk LIKE :partyFK AND p.mitbringer_fk LIKE :userFK")
			    .setParameter("partyFK", partyId)
			    .setParameter("userFK", userId)
			    .getResultList();
		
		
		
		return list;
		
		
	}
	
	
	
	public void mitbringenToParty(String mitbringer, Long mitbringer_fk, List<PartyItem> items) {
		

		for (PartyItem item : items) {
			em.detach(item);
			final PartyItem persistentesItem = findItemById(item.getId());
			if (persistentesItem != null) {
				
				
				persistentesItem.setMitbringer(mitbringer);
				persistentesItem.setMitbringer_fk(mitbringer_fk);
				em.merge(persistentesItem);
				
				
			
			}
		}
	
		
		
	
			
			
		
		
		
		
		
		
		
	}
	
	
	public PartyItem findItemById(Long id) {
		if(id == null) {
			return null;
		}
		PartyItem item = em.find(PartyItem.class, id);
		return item;
		
	}

	public List<Party> findPartiesIAttendedByNutzer(Nutzer nutzer) {
		if (nutzer == null) {
			return Collections.emptyList();
		}
		final List<Party> partiesIAttended = em.createNamedQuery(Party.FIND_ATTENDED_PARTIES_BY_NUTZER, Party.class)
																	   .setParameter(Party.PARAM_TEILNEHMER, nutzer)
																	   .setParameter(Party.PARAM_STATUS, StatusType.ZUSAGE)
																	   .getResultList();
		if (partiesIAttended == null || partiesIAttended.isEmpty()) {
			return Collections.emptyList();
		}
		
		return partiesIAttended;
	}

	public Rating bewerteParty(Rating ranking) {
		
		if (ranking == null) {
			return ranking;
		}
						
		em.persist(ranking);
		
		return ranking;
		
	}

	public List<Rating> getRatingToParty(Party party) {
		
		//Ranking-Objekt zu dieser Party auslesen
		final List<Rating> ratings = em.createNamedQuery(Rating.FIND_RATINGS_TO_PARTY, Rating.class)
																  .setParameter(Rating.PARTY_PARAM, party)
																  .getResultList();
		//Wenn noch kein Ranking vorhanden, gib null zurück
		if(ratings == null || ratings.isEmpty()) {
			return Collections.emptyList();
		}
			
		//Ergebnis abrunden um nur ganzzahlige Ratings darzustellen
		return ratings;
	}

	public Rating findRankingsByParty(Party party) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Party> findPartiesIAttendedNotRatedByNutzer(Nutzer nutzer) {
		//Parties an denen der User teilgenommen hat
		final List<Party> parties = findPartiesIAttendedByNutzer(nutzer);
		
		if (parties == null || parties.isEmpty()) {
			return Collections.emptyList();
			
		}
		final List<Party> result = new ArrayList<>();
		//Anhand der gefundene Parties prüfen ob schon ein Rating abgegeben wurde
		for (Party party : parties) {
			final List<Rating> ratings = em.createNamedQuery(Rating.FIND_RATING_TO_PARTY_BY_TEILNEHMER, Rating.class)
									.setParameter(Rating.PARTY_PARAM, party)
									.setParameter(Rating.NUTZER_PARAM, nutzer)
									.getResultList();
			//Wenn kein Rating-Objekt gefunden wurde, Party in Ergebnisliste speichern
			if ((ratings == null || ratings.isEmpty())) {
				result.add(party);
			}
		}
		
		return result;
	}
	
	public ListenHolder findPartyListenById(Nutzer nutzer) {
		
		ListenHolder holder = new ListenHolder();
		
		List<Party> meinePartys = findOpenPartiesByNutzer(nutzer);
		List<Party> offeneEinladungen = findOffeneEinladungenByNutzer(nutzer);
		List<Party> ichNehmeTeil = findZugesagtePartiesByNutzer(nutzer);
		List<Party> abgesagtNochOffen = findAbgesagtePartiesByNutzer(nutzer);
		List<Party> ichHabeTeilgenommen = findPartiesIAttendedByNutzer(nutzer);
		List<Party> toRate = findPartiesIAttendedNotRatedByNutzer(nutzer);
		List<Party> meinePartysVergangenheit = findClosedPartiesByNutzer(nutzer);
		
		holder.setMeinepartys(meinePartys);
		holder.setOffene(offeneEinladungen);
		holder.setIchnehmeteil(ichNehmeTeil);
		holder.setAbgesagtnochzukunft(abgesagtNochOffen);
		holder.setIchhabeteilgenommen(ichHabeTeilgenommen);
		holder.setTorate(toRate);
		holder.setMeinepartysvergangen(meinePartysVergangenheit);

		return holder;
		
	}
	



	

	

	
}

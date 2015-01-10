package de.party.nutzer.service;

import static de.party.util.Constants.MAX_AUTOCOMPLETE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.google.common.base.Strings;

import de.party.nutzer.domain.Freundschaft;
import de.party.nutzer.domain.Nutzer;
import de.party.nutzer.domain.Adresse;
import de.party.nutzer.domain.ProfilePicture;
import de.party.party.domain.FreundeHolder;
import de.party.party.domain.Party;
import de.party.party.domain.StatusType;
import static de.party.util.Constants.DEFAULT_PICTURE;

public class NutzerService {

	@PersistenceContext
	private EntityManager em;
	
	public Nutzer registrateUser(Nutzer nutzer) {

		if (nutzer == null) {
			return null;
		}
		Nutzer temp = findNutzerByEmail(nutzer.getEmail());
		
		if (temp != null) {
			throw new EmailExistsException(nutzer.getEmail());
		}
		nutzer.setErzeugt(new Date());
		nutzer.setPicture_id(DEFAULT_PICTURE);
		
				
		em.persist(nutzer);
		
		return nutzer;
	}
	
	public Nutzer updateUser(Nutzer nutzer) {

		if (nutzer == null) {
			return null;
		}
		Long id = nutzer.getId();
		Long adid = nutzer.getAdresse().getId();
		em.detach(nutzer);
		
		Adresse tmpad = em.find(Adresse.class, adid);
		Nutzer tmp = em.find(Nutzer.class, id);
		if(tmp == null) {
			return null;
		}
		
		tmpad.setStrasse(nutzer.getAdresse().getStrasse());
		tmpad.setHausnr(nutzer.getAdresse().getHausnr());
		tmpad.setOrt(nutzer.getAdresse().getOrt());
		tmpad.setPlz(nutzer.getAdresse().getPlz());
		
		tmp.setAdresse(tmpad);

	
	
		nutzer = em.merge(tmp);
		
		return nutzer;
	}
	
	
	
	

	
	public Nutzer findNutzerByEmail(String email) {
		Nutzer nutzer;
		
		try {
			nutzer = em.createNamedQuery(Nutzer.FIND_NUTZER_BY_EMAIL, Nutzer.class)
											.setParameter(Nutzer.EMAIL_QUERY_PARAM, email)
											.getSingleResult();
			
		}
		catch (NoResultException e) {
			return null;
		}
		return nutzer;
	}
	
	
	public Nutzer findNutzerById(Long id) {
		if(id == null) {
			return null;
		}
		Nutzer nutzer = em.find(Nutzer.class, id);
		return nutzer;
		
	}

	/**
	 * Alle Nutzer zum gesuchten Namen auslesen
	 * 
	 * @param nachname
	 * @return Liste mit Nutzerobjekten
	 */
	public List<Nutzer> findNutzerByNachname(String nachname) {
		
		List<Nutzer> nutzer;
		
		nutzer = em.createNamedQuery(Nutzer.FIND_NUTZER_BY_NACHNAME, Nutzer.class)
					.setParameter(Nutzer.NACHNAME_QUERY_PARAM, nachname)
					.getResultList();
			
		
		return nutzer;
	}


	public List<Nutzer> findNutzerByNachnamePrefix(String nachnamePrefix) {
		
		if (Strings.isNullOrEmpty(nachnamePrefix))
			return Collections.emptyList();


		return em.createNamedQuery(Nutzer.FIND_NUTZER_BY_NACHNAME_PREFIX, Nutzer.class)
				 .setParameter(Nutzer.NACHNAME_QUERY_PARAM, nachnamePrefix.toUpperCase() + '%')
				 .setMaxResults(MAX_AUTOCOMPLETE)
				 .getResultList();
		
	}
	
	/**
	 *  Freund hinzufügen, Rückgabewert ist das NutzerObjekt das als Freund zugefügt wurde
	 * 
	 * @param request_id
	 * @param friend_id
	 * @return Nutzer (== Friend)
	 */
	public Nutzer addFriend(Long nutzer_id, Long[] friend_ids) {
		
		List<Nutzer> nutzer_liste = new ArrayList<Nutzer>();
		
		Freundschaft freundschaft = null;
		
		//Null-Check und ob überhaupt Ids enthalten sind
		if (nutzer_id == null || friend_ids.length == 0) {
			return null;
		}
		//Nutzer der die Anfrage stellt herausfiltern(immer erstes Objekt im Array)
		Nutzer requester = findNutzerById(nutzer_id);
		
		
		//Iteration über Ids und Bauen der Nutzer-Objekte und Zufügen in freundschafts_liste
		for (int i = 0 ; i < friend_ids.length ; ++i) {
			Nutzer friend = findNutzerById(Long.valueOf(friend_ids[i]));
			if (friend == null) {
				break;
			}
			nutzer_liste.add(friend);
		}
		
		//Iterieren über die Nutzer_Liste und Freundschaftsobjekte bauen, diese persistieren
		for (Nutzer friend : nutzer_liste) {
			freundschaft = new Freundschaft();
			freundschaft.setOwner(requester);
			freundschaft.setFriend(friend);
			
			em.persist(freundschaft);
		}
		
		return requester;
	}
	
	public ProfilePicture findMyProfilePicture(Long id) {
		
		ProfilePicture pp;
		
		try {
			pp = em.find(ProfilePicture.class, id);
			return pp;
			
		}
		catch (NoResultException e) {
			return null;
		}
			
		
	}
	
	
	public ProfilePicture addProfilePicture(ProfilePicture pp) {

		if (pp == null) {
			return null;
		}

		em.detach(pp);
		
		Nutzer user = findNutzerById(pp.getUser_id());
		user.setPicture_id(pp.getUser_id());
		user.setImageDataStringSmall(pp.getImageDataStringSmall());
		
		ProfilePicture tmp = null;



		// Gibt es ein anderes Objekt mit gleicher ID?
		tmp = findMyProfilePicture(pp.getUser_id());
		if (tmp != null) {
			em.remove(tmp);
		}
			
		
	
		
		em.persist(pp);
		em.merge(user);
		


		return pp;
	}


	public List<Nutzer> findFriendsById(Long id) {
		
		
		
		Nutzer owner = findNutzerById(id);
		
		List<Freundschaft> queryResult = (em.createNamedQuery(Freundschaft.FIND_FRIENDS_BY_ID, Freundschaft.class)
				.setParameter(Freundschaft.ID_QUERY_PARAM, owner)
				.getResultList());
		
		List<Nutzer> friends = new ArrayList<Nutzer>();
		ArrayList<Long> friendsids = new ArrayList<Long>();
		
		for (Freundschaft freund : queryResult) {
			
			Nutzer nutzer = findNutzerById(freund.getFriend().getId());
			if(! friendsids.contains(nutzer.getId())){
				friends.add(nutzer);
				friendsids.add(nutzer.getId());
			}
			
		}
		

		Collections.sort(friends);
		
		
//		myFriends = nutzer.getMyFriends();
		
		return friends;
		
		
	}


	public List<Nutzer> findFriendsByNutzer(Nutzer nutzer) {

		// Alle Nutzer-Objekte mit denen ich befreundet bin (ich = friend-Seite)
		List<Nutzer> friendOf = em.createNamedQuery(Nutzer.FIND_MY_FRIENDS_BY_NUTZER, Nutzer.class)
								 .setParameter(Nutzer.PARAM_NUTZER, nutzer)
								 .getResultList();
		
		// Alle Nutzer-Objekte die Freunde von mir sind (ich = owner-Seite)
		List<Nutzer> myFriends = em.createNamedQuery(Nutzer.FIND_NUTZER_I_AM_FRIEND_OF, Nutzer.class)
								  .setParameter(Nutzer.PARAM_NUTZER, nutzer)
								  .getResultList();
		
		// Ergebnis in einer Liste speichern und zürckgeben
		List<Nutzer> allFriends = new ArrayList<Nutzer>();
				
		allFriends.addAll(myFriends);
		allFriends.addAll(friendOf);
		
		return allFriends;
		
	}

	
	public List<Nutzer> findEingeladeneTeilnehmerByParty(Party party) {

		
		final List<Nutzer> teilnehmer = em.createNamedQuery(Nutzer.FIND_EINGELADENE_TEILNEHMER_BY_PARTY, Nutzer.class)
																  .setParameter(Nutzer.PARAM_PARTY, party)
																  .setParameter(Nutzer.PARAM_TEILNAHME_STATUS, StatusType.OFFEN)
																  .getResultList();
		if (teilnehmer == null || teilnehmer.isEmpty()) {
			return Collections.emptyList();
		}
		
		return teilnehmer;
		
		
													 
	}


	public List<Nutzer> findZugesagteTeilnehmerByParty(Party party) {
		
		final List<Nutzer> teilnehmer = em.createNamedQuery(Nutzer.FIND_EINGELADENE_TEILNEHMER_BY_PARTY, Nutzer.class)
																  .setParameter(Nutzer.PARAM_PARTY, party)
																  .setParameter(Nutzer.PARAM_TEILNAHME_STATUS, StatusType.ZUSAGE)
																  .getResultList();
		if (teilnehmer == null || teilnehmer.isEmpty()) {
			return Collections.emptyList();
		}
		
		return teilnehmer;
	}

	public List<Nutzer> findAbgesagteTeilnehmerByParty(Party party) {
		
		final List<Nutzer> teilnehmer = em.createNamedQuery(Nutzer.FIND_EINGELADENE_TEILNEHMER_BY_PARTY, Nutzer.class)
																  .setParameter(Nutzer.PARAM_PARTY, party)
																  .setParameter(Nutzer.PARAM_TEILNAHME_STATUS, StatusType.ABSAGE)
																  .getResultList();
		if (teilnehmer == null || teilnehmer.isEmpty()) {
			return Collections.emptyList();
		}
		
		return teilnehmer;
	}


	public List<Nutzer> findNotInvitedFriendsByParty(Nutzer veranstalter,
			Party party) {
		
		// Erst Freunde auslesen
		final List<Nutzer> friends = findFriendsByNutzer(veranstalter);
		// Jetzt eingeladene Nutzer auslesen
		final List<Nutzer> teilnehmer = findEingeladeneTeilnehmerByParty(party);
		
		//TODO Freunde suchen die zugesagt oder abgesagt haben
		final List<Nutzer> zugesagteTeilnehmer = findZugesagteTeilnehmerByParty(party);
		
		final List<Nutzer> abgesagteTeilnehmer = findAbgesagteTeilnehmerByParty(party); 
		
		// Wenn noch keine Teilnehmer vorhanden sind Freundes-Liste zurückgeben
		if (teilnehmer == null || teilnehmer.isEmpty()) {
				if (zugesagteTeilnehmer == null || zugesagteTeilnehmer.isEmpty()) {
					if (abgesagteTeilnehmer == null || abgesagteTeilnehmer.isEmpty()) {
						return friends;
					}
				}
		}
		
		final List<Nutzer> notInvitedFriends = new ArrayList<Nutzer>();
		
		// Freund mit ausgeben wenn er in keiner der drei Fälle auftaucht (OFFEN, ZUGESAGT, ABGESAGT)
		for (Nutzer friend : friends) {
			if (!(teilnehmer.contains(friend) || zugesagteTeilnehmer.contains(friend) 
					|| abgesagteTeilnehmer.contains(friend))) {
				notInvitedFriends.add(friend);
			}				
		}
		
		return notInvitedFriends;
		
		
		
		
	}
	
	public FreundeHolder findPartyFreundeListenById(Party party) {
		
		FreundeHolder holder = new FreundeHolder();
		
		List<Nutzer> zugesagt = findZugesagteTeilnehmerByParty(party);
		List<Nutzer> abgesagt = findAbgesagteTeilnehmerByParty(party);
		List<Nutzer> eingeladen = findEingeladeneTeilnehmerByParty(party);

		
		holder.setZugesagt(zugesagt);
		holder.setAbgesagt(abgesagt);
		holder.setEingeladen(eingeladen);
	

		return holder;
		
	}

	
	
}

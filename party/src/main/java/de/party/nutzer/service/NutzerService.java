package de.party.nutzer.service;

import static de.party.util.Constants.MAX_AUTOCOMPLETE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.google.common.base.Strings;

import de.party.nutzer.domain.Freundschaft;
import de.party.nutzer.domain.Nutzer;
import de.party.nutzer.domain.ProfilePicture;
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
		
		//TODO mail exception werfen
		
		em.persist(nutzer);
		
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
		
		ProfilePicture tmp = null;



		// Gibt es ein anderes Objekt mit gleicher ID?
		tmp = findMyProfilePicture(pp.getUser_id());
		if (tmp != null) {
			em.detach(tmp);
			pp = em.merge(pp);
			}
		
		em.persist(pp);
		


		return pp;
	}


	public List<Nutzer> findFriendsById(Long id) {
		
		
		
		Nutzer owner = findNutzerById(id);
		
		List<Freundschaft> queryResult = (em.createNamedQuery(Freundschaft.FIND_FRIENDS_BY_ID, Freundschaft.class)
				.setParameter(Freundschaft.ID_QUERY_PARAM, owner)
				.getResultList());
		
		List<Nutzer> friends = new ArrayList<Nutzer>();
		
		for (Freundschaft freund : queryResult) {
			
			Nutzer nutzer = findNutzerById(freund.getFriend().getId());
			friends.add(nutzer);
		}
		
		
				
		
//		myFriends = nutzer.getMyFriends();
		
		return friends;
		
		
	}


	
	
}

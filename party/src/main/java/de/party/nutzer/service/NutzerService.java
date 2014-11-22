package de.party.nutzer.service;

import static de.party.util.Constants.MAX_AUTOCOMPLETE;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import com.google.common.base.Strings;
import de.party.nutzer.domain.Nutzer;

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
	
	/**
	 * Service Methode 
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public Nutzer authService(String email, String password) {
		//Prüfen ob der User existiert, suche anhand der email
		Nutzer nutzer = findNutzerByEmail(email);
		if (nutzer == null) {
			return null;
		}
		//Prüfe ob Passwort des gefundenen Users dem eingegeben Password entspricht
		if (!nutzer.getPassword().equals(password)) {
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
	
	
}

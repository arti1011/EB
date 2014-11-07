package de.party.nutzer.service;


import java.util.Date;

import javax.persistence.EntityManager;
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
			return null;
		}
		nutzer.setErzeugt(new Date());
		
		//TODO mail exception werfen
		
		em.persist(nutzer);
		
		return nutzer;
	}

	public Nutzer findNutzerByEmail(String email) {
		if (Strings.isNullOrEmpty(email)) {
				return null;
		}
		return em.find(Nutzer.class, email);
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
	
	
}

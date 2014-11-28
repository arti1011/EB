package de.party.auth.service;

import javax.inject.Inject;

import de.party.nutzer.domain.Nutzer;
import de.party.nutzer.service.NutzerService;

public class AuthService {

	@Inject 
	private NutzerService ns;
	
		
	public Nutzer login(String email, String password) {
		//Prüfen ob der User existiert, suche anhand der email
		Nutzer nutzer = ns.findNutzerByEmail(email);
		if (nutzer == null) {
			return null;
		}
		//Prüfe ob Passwort des gefundenen Users dem eingegeben Password entspricht
		if (!nutzer.getPassword().equals(password)) {
			return null;
		}
		
				
		return nutzer;
		
	}
}

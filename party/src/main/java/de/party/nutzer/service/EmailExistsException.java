package de.party.nutzer.service;

public class EmailExistsException extends AbstractNutzerServiceException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String MESSAGE_KEY = "nutzer.emailExists";
	private final String email;
	
	public EmailExistsException(String email) {
		super("Die Email: " + email + " existiert bereits");
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	@Override
	public String getMessageKey() {
		return MESSAGE_KEY;
	}
	

}

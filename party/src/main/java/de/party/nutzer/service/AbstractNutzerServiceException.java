package de.party.nutzer.service;

import de.party.util.AbstractPartyException;


public abstract class AbstractNutzerServiceException extends AbstractPartyException {
	private static final long serialVersionUID = -2849585609393128387L;

	public AbstractNutzerServiceException(String msg) {
		super(msg);
	}
	
	public AbstractNutzerServiceException(String msg, Throwable t) {
		super(msg, t);
	}
}
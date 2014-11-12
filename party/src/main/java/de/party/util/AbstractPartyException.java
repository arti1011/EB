package de.party.util;

public abstract class AbstractPartyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4614306803726487544L;
	
	public AbstractPartyException(String msg) {
		super(msg);
	}
	public AbstractPartyException(String msg, Throwable t) {
		super(msg, t);
	}
	public abstract String getMessageKey();
	

}

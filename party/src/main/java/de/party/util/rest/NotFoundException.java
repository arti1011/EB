package de.party.util.rest;

public class NotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = -6162796032935524307L;
	
	private final Object[] args;
	
	public NotFoundException(String msg, Object... args) {
		super(msg);
		this.args = args;
	}
	
	public Object[] getArgs() {
		return args;
	}

}

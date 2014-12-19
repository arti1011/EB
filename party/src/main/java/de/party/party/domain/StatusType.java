package de.party.party.domain;

public enum StatusType {

	/**
	 * OFFEN = EINGELADEN
	 */
	OFFEN("offen"),
	ZUSAGE("zusage"),
	ABSAGE("absage");
	
	private String internal;
	
	private StatusType(String internal) {
		this.internal = internal;
	}
	
	public String getInternal() {
		return internal;
	}
	
	public static StatusType build(String internal) {
		if (internal == null) {
			return null;
		}
		return StatusType.valueOf(internal.toUpperCase());
	}
}

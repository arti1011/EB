package de.party.nutzer.domain;

import java.io.Serializable;



public class FreundschaftKey implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3720265679294541796L;
	
	
	private Long owner;
	

	private Long friend;
	
	public Long getOwner() {
		return owner;
	}
	public void setOwner(Long owner) {
		this.owner = owner;
	}
	public Long getFriend() {
		return friend;
	}
	public void setFriend(Long friend) {
		this.friend = friend;
	}
	
	public FreundschaftKey(final Long owner, final Long friend) {
		this.owner = owner;
		this.friend = friend;
	}
	
	public FreundschaftKey() {
		super();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((friend == null) ? 0 : friend.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FreundschaftKey other = (FreundschaftKey) obj;
		if (friend == null) {
			if (other.friend != null)
				return false;
		} else if (!friend.equals(other.friend))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		return true;
	}
	
	
	
	
}

package de.party.nutzer.domain;

import java.io.Serializable;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table
@XmlRootElement
@NamedQueries( {
@NamedQuery(name = Freundschaft.FIND_FRIENDS_BY_NUTZER,
query = "SELECT f"
		+ " FROM Freundschaft f  "
		+ " WHERE f.owner = :" + Freundschaft.ID_QUERY_PARAM
		+ " OR f.friend = :" + Freundschaft.ID_QUERY_PARAM)
})
@IdClass(FreundschaftKey.class)
public class Freundschaft implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4292729479002743257L;

	public final static String FIND_FRIENDS_BY_ID = "findFriendsById";
	public final static String ID_QUERY_PARAM = "owner";

	public final static String FIND_FRIENDS_BY_NUTZER = "findFriendsByNutzer";
	

	@Id
	@ManyToOne
	@XmlTransient
	private Nutzer owner;
	
	@Id
	@ManyToOne
	@XmlTransient
	private Nutzer friend;

	@XmlTransient
	public Nutzer getOwner() {
		return owner;
	}

	public void setOwner(Nutzer owner) {
		this.owner = owner;
	}
	
	@XmlTransient
	public Nutzer getFriend() {
		return friend;
	}

	public void setFriend(Nutzer friend) {
		this.friend = friend;
	}
	
	public Freundschaft() {
		super();
	}
	
}

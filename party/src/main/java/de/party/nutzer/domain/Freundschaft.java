package de.party.nutzer.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table
@XmlRootElement
public class Freundschaft implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4292729479002743257L;


	@Id
	@GeneratedValue
	@Column(nullable = false, updatable = false)
	private Long id;
	

	// Nutzer der Freundschaft anfrägt
	@ManyToOne()
	@JoinColumn(name = "owner_id")
	private Nutzer owner;
	
	
	// Nutzer der Freundschaft akzeptiert
	@ManyToOne()
	@JoinColumn(name = "person_id")
	private Nutzer person;


	public Nutzer getOwner() {
		return owner;
	}


	public void setOwner(Nutzer owner) {
		this.owner = owner;
	}


	public Nutzer getPerson() {
		return person;
	}


	public void setPerson(Nutzer person) {
		this.person = person;
	}


	public Long getId() {
		return id;
	}

	public Freundschaft(Nutzer requester, Nutzer friend) {
		this.owner = requester;
		this.person = friend;
	}
	
	public Freundschaft() {
		super();
	}
	
	
	
}

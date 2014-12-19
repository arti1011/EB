package de.party.party.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import static de.party.util.Constants.KEINE_ID;
import de.party.nutzer.domain.Nutzer;

@Entity
@Table
@XmlRootElement
public class PartyTeilnahme {

	@Id
	private Long id = KEINE_ID;
	
	@Column
	@Enumerated(EnumType.STRING)
	private StatusType status;
	
		
	
	@ManyToOne
	@JoinColumn(name="teilnehmer_id", updatable = false, insertable = false, referencedColumnName = "id")
	private Nutzer teilnehmer;
	
	
	@ManyToOne
	@JoinColumn(name="party_id", updatable = false, insertable = false, referencedColumnName = "id")
	private Party party;
	
	public PartyTeilnahme() {
		super();
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

	
	public Nutzer getTeilnehmer() {
		return teilnehmer;
	}

	public void setTeilnehmer(Nutzer teilnehmer) {
		this.teilnehmer = teilnehmer;
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}
	
	
	
	
	
}

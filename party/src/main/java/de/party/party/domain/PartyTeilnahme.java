package de.party.party.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import static de.party.util.Constants.KEINE_ID;
import de.party.nutzer.domain.Nutzer;

@Entity
@Table
@NamedQueries({
	@NamedQuery(name=PartyTeilnahme.FIND_PARTY_TEILNAHME, 
				query = "SELECT pt FROM PartyTeilnahme pt"
						+ " WHERE pt.teilnehmer= :" + PartyTeilnahme.PARAM_TEILNEHMER
						+ " AND pt.party= :" + PartyTeilnahme.PARAM_PARTY),
//	@NamedQuery(name=PartyTeilnahme.FIND_PARTY_TEILNAHME_EINGELADEN, 
//				query = "SELECT pt FROM PartyTeilnahme pt"
//						+ " WHERE pt.party= :" + PartyTeilnahme.PARAM_PARTY
//						+ " AND pt.status= :")	
})
@XmlRootElement
public class PartyTeilnahme {

	//Query Strings
	public static final String FIND_PARTY_TEILNAHME = "findPartyTeilnahme";
	public static final String FIND_PARTY_TEILNAHME_EINGELADEN = "findPartyTeilnahmeEingeladen";
	
	//Query Parameter
	public static final String PARAM_TEILNEHMER = "nutzer";
	public static final String PARAM_PARTY = "party";

	

	@Id
	@GeneratedValue
	private Long id = KEINE_ID;
	
	@Column
	@Enumerated(EnumType.STRING)
	private StatusType status;
	
	//TODO kombinierte PK aus den FKs	
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="teilnehmer_id", updatable = false, referencedColumnName = "id")
	private Nutzer teilnehmer;
	
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="party_id", updatable = false, referencedColumnName = "id")
	private Party party;
	
	public PartyTeilnahme() {
		super();
	}
	
	// Neue PartyTeilnahme, Freunde eingeladen
	public PartyTeilnahme(Party party, Nutzer nutzer) {
		super();
		this.setParty(party);
		this.setTeilnehmer(nutzer);
		this.setStatus(StatusType.OFFEN);
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

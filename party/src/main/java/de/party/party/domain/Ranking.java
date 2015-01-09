package de.party.party.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.UniqueConstraint;

import de.party.nutzer.domain.Nutzer;
import static de.party.util.Constants.KEINE_ID;


@Entity
@Table(uniqueConstraints=
	@UniqueConstraint(columnNames = {"party_fk", "nutzer_fk"})) 
@XmlRootElement
public class Ranking implements Serializable{

	private static final long serialVersionUID = 4197686411819694662L;
	
	@Id
	@GeneratedValue
	private Long id = KEINE_ID;
	
	@OneToOne
	@JoinColumn(name = "nutzer_fk")
	private Nutzer teilnehmer;
	
	@ManyToOne
	@JoinColumn(name="party_fk", referencedColumnName = "id")
	private Party party;
	
	@Column
	private int rating;

	@Column
	private String kommentar;
		
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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getKommentar() {
		return kommentar;
	}

	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}

	
	
}

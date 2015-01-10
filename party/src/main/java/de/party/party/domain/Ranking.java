package de.party.party.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
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
@NamedQuery(name = Ranking.FIND_RANKINGS_TO_PARTY, query = 
				"SELECT r"
			+	" FROM Ranking r"
			+	" WHERE r.party =:" + Ranking.PARTY_PARAM)
public class Ranking implements Serializable{

	private static final long serialVersionUID = 4197686411819694662L;
	
	//Queries
	public static final String FIND_RANKINGS_TO_PARTY = "findRankingsToParty";
	
	//Params
	public static final String PARTY_PARAM = "party";
	
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
	private Double rating;

	@Column
	private String kommentar;
	
	public Ranking(Party party, Nutzer nutzer, Double rating, String kommentar) {
		this.party = party;
		this.teilnehmer = nutzer;
		this.rating = rating;
		this.kommentar = kommentar;
	}
	
	public Ranking() {
		super();
	}
		
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getKommentar() {
		return kommentar;
	}

	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}

	
	
}

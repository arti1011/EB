package de.party.party.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.UniqueConstraint;

import de.party.nutzer.domain.Nutzer;
import static de.party.util.Constants.KEINE_ID;
import static javax.persistence.TemporalType.TIMESTAMP;


@Entity
@Table(uniqueConstraints=
	@UniqueConstraint(columnNames = {"party_fk", "nutzer_fk"})) 
@XmlRootElement
@NamedQueries({@NamedQuery(name = Rating.FIND_RATINGS_TO_PARTY, query = 
				"SELECT r"
			+	" FROM Rating r"
			+	" WHERE r.party =:" + Rating.PARTY_PARAM),
@NamedQuery(name = Rating.FIND_RATINGS_TO_VERANSTALTER, query = 
				"SELECT r"
			+	" FROM Rating r"
			+	" WHERE r.party =:" + Rating.PARTY_PARAM
			+	" AND r.teilnehmer =:" + Rating.NUTZER_PARAM
			+	" ORDER BY r.party"),
@NamedQuery(name = Rating.FIND_RATING_TO_PARTY_BY_TEILNEHMER, query = 
				"SELECT r"
			+	" FROM Rating r"
			+	" WHERE r.party =:" + Rating.PARTY_PARAM
			+	" AND r.teilnehmer =:" + Rating.NUTZER_PARAM)
})
public class Rating implements Serializable{

	private static final long serialVersionUID = 4197686411819694662L;
	
	//Queries
	public static final String FIND_RATINGS_TO_PARTY = "findRatingsToParty";
	public static final String FIND_RATINGS_TO_VERANSTALTER = "findRatingsToVeranstalter";
	public static final String FIND_RATING_TO_PARTY_BY_TEILNEHMER = "findRatingToPartyByTeilnehmer";
	
	//Params
	public static final String PARTY_PARAM = "party";
	public static final String NUTZER_PARAM = "teilnehmer";

	
	
	@Id
	@GeneratedValue
	private Long id = KEINE_ID;
	
	@OneToOne
	@JoinColumn(name = "nutzer_fk")
	private Nutzer teilnehmer;
	
	@OneToOne
	@JoinColumn(name="party_fk")
	private Party party;
	
	@Column
	private float value;

	@Column
	private String kommentar;
	
	@Basic(optional = false)
	@Temporal(TIMESTAMP)
	@XmlTransient
	private Date erzeugt;
	
	public Rating(Party party, Nutzer nutzer, float rating, String kommentar) {
		this.party = party;
		this.teilnehmer = nutzer;
		this.value = rating;
		this.kommentar = kommentar;
	}
	
	public Rating() {
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

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public String getKommentar() {
		return kommentar;
	}

	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}

	@PrePersist
	protected void prePerstist() {
		erzeugt = new Date();
	}
	
	public Date getErzeugt() {
		return erzeugt;
	}

	public void setErzeugt(Date erzeugt) {
		this.erzeugt = erzeugt;
	}
	
	
}

package de.party.party.domain;

import static de.party.util.Constants.KEINE_ID;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import de.party.nutzer.domain.Nutzer;


@Entity
@Table
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = Party.FIND_OPEN_PARTIES_BY_VERANSTALTER, query = 
						"SELECT p"
					   + " FROM Party p"
					   + " WHERE p.veranstalter = :"
					   + Party.PARAM_NUTZER
					   + " AND CURRENT_TIMESTAMP < p.datum"),
	@NamedQuery(name = Party.FIND_CLOSED_PARTIES_BY_VERANSTALTER, query = 
						"SELECT p"
					   + " FROM Party p"
					   + " WHERE p.veranstalter = :"
					   + Party.PARAM_NUTZER
					   + " AND CURRENT_TIMESTAMP > p.datum"),				   
    @NamedQuery(name = Party.FIND_OFFENE_EINLADUNGEN_BY_NUTZER, query = 
    					"SELECT p"
    				+ 	" FROM Party p"
    				+	" JOIN p.teilnehmer t"
    				+ 	" WHERE t.teilnehmer = :"+ Party.PARAM_TEILNEHMER
    				+	" AND t.status = :" + Party.PARAM_STATUS
    				+	" AND CURRENT_TIMESTAMP < p.datum"),
    @NamedQuery(name = Party.FIND_ZUGESAGTE_PARTIES_BY_NUTZER, query = 
						"SELECT p"
					+ 	" FROM Party p"
					+	" JOIN p.teilnehmer t"
					+ 	" WHERE t.teilnehmer = :"+ Party.PARAM_TEILNEHMER
					+	" AND t.status = :" + Party.PARAM_STATUS
					+	" AND CURRENT_TIMESTAMP < p.datum"),
	@NamedQuery(name = Party.FIND_ABGESAGTE_PARTIES_BY_NUTZER, query = 
						"SELECT p"
					+	" FROM Party p"
					+	" JOIN p.teilnehmer t"
					+	" WHERE t.teilnehmer = :"+ Party.PARAM_TEILNEHMER
					+	" AND t.status = :" + Party.PARAM_STATUS
					+	" AND CURRENT_TIMESTAMP < p.datum"),
	@NamedQuery(name = Party.FIND_ATTENDED_PARTIES_BY_NUTZER, query = 
						"SELECT p"
					+	" FROM Party p"
					+	" JOIN p.teilnehmer t"
					+	" WHERE t.teilnehmer = :"+ Party.PARAM_TEILNEHMER
					+	" AND t.status = :" + Party.PARAM_STATUS
					+	" AND CURRENT_TIMESTAMP > p.datum"
					)					
})
public class Party implements Serializable {

	private static final long serialVersionUID = -5326769944603732896L;
	
	// Queries
	//+	" AND t.status = :" + Party.PARAM_STATUS)
	public static final String PREFIX = "Party.";
	
	public static final String PARAM_NUTZER = "nutzer";
	
	public static final String PARAM_TEILNEHMER = "teilnehmer";
	public static final String PARAM_STATUS = "status";
	
	public static final String FIND_OPEN_PARTIES_BY_VERANSTALTER = PREFIX + "findOpenPartiesByVeranstalter";
	public static final String FIND_CLOSED_PARTIES_BY_VERANSTALTER = PREFIX + "findClosedPartiesByVeranstalter";
	public static final String FIND_OFFENE_EINLADUNGEN_BY_NUTZER = PREFIX + "findOffeneEinladungenByNutzer";

	public static final String FIND_ZUGESAGTE_PARTIES_BY_NUTZER = PREFIX + "findZugesagtePartiesByNutzer";
	public static final String FIND_ABGESAGTE_PARTIES_BY_NUTZER = PREFIX + "findAbgesagtePartiesByNutzer";

	public static final String FIND_ATTENDED_PARTIES_BY_NUTZER = "findAttendedPartiesByNutzer";
	
	
	@Id
	@GeneratedValue
	@Column(nullable = false, updatable = false)
	private Long id = KEINE_ID;
	
	@Column
	private String titel;
	
	@Column
	private String beschreibung;
	
	@Basic(optional = false)
	@Temporal(TIMESTAMP)
	@Future(message = "{party.datum.future}")
	private Date datum;
	
	@Column
	private String uhrzeit;
	
		
	@OneToOne
	@JoinColumn(name = "veranstalter", nullable = false)
	@NotNull(message = "{party.veranstalter.notNull}")
	private Nutzer veranstalter;
	
	@Column
	private String longitude;
	
	@Column
	private String latitude;
	
	
	@OneToMany(mappedBy="partis")
	private List<Nutzer> nutzer;
	
	// Relation für Queries	
	@OneToMany(mappedBy="party", cascade = CascadeType.PERSIST)
	private List<PartyTeilnahme> teilnehmer;
	
	@OneToMany(mappedBy="party")
	private List<Ranking> rankings;

	
//	@ManyToMany(mappedBy = "parties")
//	private List<Nutzer> teilnehmer;
	
//	@Transient
//	private URI einladungenUri;
//	
//	@Transient
//	private URI zusagenUri;
//	
//	@Transient
//	private URI absagenUri;
		
	public Party() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}
	
	public String getUhrzeit() {
		return uhrzeit;
			
	}
	public void setUhrzeit(String uhrzeit) {
		this.uhrzeit = uhrzeit;
	}

	
	
	public Nutzer getVeranstalter() {
		return veranstalter;
	}

	public void setVeranstalter(Nutzer veranstalter) {
		this.veranstalter = veranstalter;
	}

	
//	@XmlTransient
//	public List<Nutzer> getTeilnehmer() {
//		return teilnehmer;
//	}
//
//	public void setTeilnehmer(List<Nutzer> teilnehmer) {
//		this.teilnehmer = teilnehmer;
//	}

	@XmlTransient
	public List<PartyTeilnahme> getTeilnehmer() {
		return teilnehmer;
	}

	public void setTeilnehmer(List<PartyTeilnahme> teilnehmer) {
		this.teilnehmer = teilnehmer;
	}
	


	
//	public URI getEinladungenUri() {
//		return einladungenUri;
//	}
//
//	public void setEinladungenUri(URI einladungenUri) {
//		this.einladungenUri = einladungenUri;
//	}
//
//	public URI getZusagenUri() {
//		return zusagenUri;
//	}
//
//	public void setZusagenUri(URI zusagenUri) {
//		this.zusagenUri = zusagenUri;
//	}
//
//	public URI getAbsagenUri() {
//		return absagenUri;
//	}
//
//	public void setAbsagenUri(URI absagenUri) {
//		this.absagenUri = absagenUri;
//	}

	
	@XmlTransient
	public List<Nutzer> getNutzer() {
		return nutzer;
	}

	public void setNutzer(List<Nutzer> nutzer) {
		this.nutzer = nutzer;
	}

	
	@XmlTransient
	public List<Ranking> getRankings() {
		return rankings;
	}

	public void setRankings(List<Ranking> rankings) {
		this.rankings = rankings;
	}

	public void zusagen(Party party){
		
		
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "Party [id=" + id + ", titel=" + titel + ", beschreibung="
				+ beschreibung + ", datum=" + datum + ", uhrzeit=" + uhrzeit
				+ ", veranstalter=" + veranstalter + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", nutzer=" + nutzer
				+ ", teilnehmer=" + teilnehmer +  "]";
	}
	
	
	
	
	
	

}

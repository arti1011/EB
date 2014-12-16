package de.party.party.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

import de.party.nutzer.domain.Nutzer;
import static de.party.util.Constants.KEINE_ID;
import static javax.persistence.TemporalType.DATE;

@Entity
@Table
@XmlRootElement
public class Party implements Serializable {

	private static final long serialVersionUID = -5326769944603732896L;
	
	@Id
	@GeneratedValue
	@Column(nullable = false, updatable = false)
	private Long id = KEINE_ID;
	
	@Column
	private String titel;
	
	@Column
	private String beschreibung;
	
	@Column
	@Temporal(DATE)
	private Date datum;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="veranstalter", nullable = false, insertable = false, updatable = false)
	private Nutzer veranstalter;
	
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

	public Nutzer getVeranstalter() {
		return veranstalter;
	}

	public void setVeranstalter(Nutzer veranstalter) {
		this.veranstalter = veranstalter;
	}
	
	
	
	

}

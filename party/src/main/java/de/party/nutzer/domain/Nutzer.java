package de.party.nutzer.domain;



import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table
public class Nutzer implements Serializable {
	private static final long serialVersionUID = 4618817696314640065L;
	
	private static final int PLZ_LENGTH_MAX = 5;
//	private static final int ORT_LENGTH_MIN = 2;
	private static final int ORT_LENGTH_MAX = 32;
//	private static final int STRASSE_LENGTH_MIN = 2;
	private static final int STRASSE_LENGTH_MAX = 32;
	private static final int HAUSNR_LENGTH_MAX = 4;

	
	@Id
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String nachname;
	
	@Column(nullable = false)
	private String vorname;	
	
	
	@Version
	@Basic(optional = false)
	private int version = 1;

	@Column(length = PLZ_LENGTH_MAX, nullable = false)
//	@NotNull(message = "{adresse.plz.notNull}")
//	@Pattern(regexp = "\\d{5}", message = "{adresse.plz}")
	private String plz;

	@Column(length = ORT_LENGTH_MAX, nullable = false)
//	@NotNull(message = "{adresse.ort.notNull}")
//	@Size(min = ORT_LENGTH_MIN, max = ORT_LENGTH_MAX, message = "{adresse.ort.length}")
	private String ort;

	@Column(length = STRASSE_LENGTH_MAX, nullable = false)
//	@NotNull(message = "{adresse.strasse.notNull}")
//	@Size(min = STRASSE_LENGTH_MIN, max = STRASSE_LENGTH_MAX, message = "{adresse.strasse.length}")
	private String strasse;

	@Column(length = HAUSNR_LENGTH_MAX)
//	@Size(max = HAUSNR_LENGTH_MAX, message = "{adresse.hausnr.length}")
	private String hausnr;

	//TODO eventuell @JsonIgnore damit das Password nicht im Response übermittelt wird
	@NotNull(message = "PASSWORD DARF NET NULL SEI DU DAPP")
	@Column(nullable = false)
	private String password;
	
	@Basic(optional = false)
	@Temporal(TIMESTAMP)
	@XmlTransient
	private Date erzeugt;

	@Basic(optional = false)
	@Temporal(TIMESTAMP)
	@XmlTransient
	private Date aktualisiert;

	public Nutzer() {
		super();
	}

	public Nutzer(String plz, String ort, String strasse, String hausnr) {
		super();
		this.plz = plz;
		this.ort = ort;
		this.strasse = strasse;
		this.hausnr = hausnr;
	}
	@PrePersist
	protected void prePerstist() {
		aktualisiert = new Date();
	}
	
	@PreUpdate
	protected void preUpdate() {
		aktualisiert = new Date();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getHausnr() {
		return hausnr;
	}

	public void setHausnr(String hausnr) {
		this.hausnr = hausnr;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Date getErzeugt() {
		return erzeugt;
	}

	public void setErzeugt(Date erzeugt) {
		this.erzeugt = erzeugt;
	}

	public Date getAktualisiert() {
		return aktualisiert;
	}

	public void setAktualisiert(Date aktualisiert) {
		this.aktualisiert = aktualisiert;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((nachname == null) ? 0 : nachname.hashCode());
		result = prime * result + ((vorname == null) ? 0 : vorname.hashCode());
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
		Nutzer other = (Nutzer) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (nachname == null) {
			if (other.nachname != null)
				return false;
		} else if (!nachname.equals(other.nachname))
			return false;
		if (vorname == null) {
			if (other.vorname != null)
				return false;
		} else if (!vorname.equals(other.vorname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Nutzer [email=" + email + ", nachname=" + nachname
				+ ", vorname=" + vorname + ", plz=" + plz + ", ort=" + ort
				+ ", strasse=" + strasse + ", hausnr=" + hausnr + "]";
	}

	



	



}

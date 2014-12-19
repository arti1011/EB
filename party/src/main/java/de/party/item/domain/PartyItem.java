package de.party.item.domain;

import static de.party.util.Constants.KEINE_ID;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import de.party.party.domain.Party;


@Entity
@Table
@XmlRootElement
public class PartyItem implements Serializable {
	
	private static final long serialVersionUID = 2632447781498478734L;
	
	@Id
	@GeneratedValue
	@Column(nullable = false, updatable = false)
	private Long id = KEINE_ID;
	
	@OneToOne
	@JoinColumn(name="party_id", updatable = false, insertable = false, referencedColumnName = "id")
	private Party party;
	
	@OneToMany(mappedBy="partyItem", cascade = CascadeType.ALL)
	private List<ItemMitbringer> mitbringer;
	
	@Column(nullable = false)
	private String beschreibung;
	
	@Column(nullable = false)
	private String picture_id;
	
	@Column
	private int anzahl;
	
	@Column
	private int noch_benötigt;

	@Column(nullable = false)
	@Temporal(TIMESTAMP)
	private Date erzeugt;
	
	@Basic(optional = false)
	@Temporal(TIMESTAMP)
	@XmlTransient
	private Date aktualisiert;
	
	public PartyItem() {
		super();
	}

	
	@PrePersist
	protected void prePerstist() {
		aktualisiert = new Date();
	}
	
	@PreUpdate
	protected void preUpdate() {
		aktualisiert = new Date();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Party getParty() {
		return party;
	}


	public void setParty(Party party) {
		this.party = party;
	}


	public List<ItemMitbringer> getMitbringer() {
		return mitbringer;
	}


	public void setMitbringer(List<ItemMitbringer> mitbringer) {
		this.mitbringer = mitbringer;
	}


	public String getBeschreibung() {
		return beschreibung;
	}


	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}


	public String getPicture_id() {
		return picture_id;
	}


	public void setPicture_id(String picture_id) {
		this.picture_id = picture_id;
	}


	public int getAnzahl() {
		return anzahl;
	}


	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}


	public int getNoch_benötigt() {
		return noch_benötigt;
	}


	public void setNoch_benötigt(int noch_benötigt) {
		this.noch_benötigt = noch_benötigt;
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
		result = prime * result
				+ ((aktualisiert == null) ? 0 : aktualisiert.hashCode());
		result = prime * result + anzahl;
		result = prime * result
				+ ((beschreibung == null) ? 0 : beschreibung.hashCode());
		result = prime * result + ((erzeugt == null) ? 0 : erzeugt.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((mitbringer == null) ? 0 : mitbringer.hashCode());
		result = prime * result + noch_benötigt;
		result = prime * result + ((party == null) ? 0 : party.hashCode());
		result = prime * result
				+ ((picture_id == null) ? 0 : picture_id.hashCode());
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
		PartyItem other = (PartyItem) obj;
		if (aktualisiert == null) {
			if (other.aktualisiert != null)
				return false;
		} else if (!aktualisiert.equals(other.aktualisiert))
			return false;
		if (anzahl != other.anzahl)
			return false;
		if (beschreibung == null) {
			if (other.beschreibung != null)
				return false;
		} else if (!beschreibung.equals(other.beschreibung))
			return false;
		if (erzeugt == null) {
			if (other.erzeugt != null)
				return false;
		} else if (!erzeugt.equals(other.erzeugt))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mitbringer == null) {
			if (other.mitbringer != null)
				return false;
		} else if (!mitbringer.equals(other.mitbringer))
			return false;
		if (noch_benötigt != other.noch_benötigt)
			return false;
		if (party == null) {
			if (other.party != null)
				return false;
		} else if (!party.equals(other.party))
			return false;
		if (picture_id == null) {
			if (other.picture_id != null)
				return false;
		} else if (!picture_id.equals(other.picture_id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "PartyItem [id=" + id + ", party=" + party + ", mitbringer="
				+ mitbringer + ", beschreibung=" + beschreibung
				+ ", picture_id=" + picture_id + ", anzahl=" + anzahl
				+ ", noch_benötigt=" + noch_benötigt + ", erzeugt=" + erzeugt
				+ ", aktualisiert=" + aktualisiert + "]";
	}


}

package de.party.item.domain;

import static de.party.util.Constants.KEINE_ID;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;




@Entity
@Table
@XmlRootElement
public class Item implements Serializable {
	private static final long serialVersionUID = 2632441781498478734L;

	@Id
	@GeneratedValue
	@Column(nullable = false, updatable = false)
	private Long id = KEINE_ID;

	@Lob
	@Column(nullable = false)
	private String imageDataString; 

	@Column(unique = true, nullable = false)
	private String beschreibung;

	@Column(nullable = false)
	@Temporal(TIMESTAMP)
	private Date erzeugt;

	public Item() {
		super();
	}

	public Item(String imageDataString, String beschreibung) {
		super();
		set(imageDataString, beschreibung);
	}

	@PrePersist
	protected void prePersist() {
		erzeugt = new Date();
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getImageDataString() {
		return imageDataString;
	}

	public void setImageDataString(String imageDataString) {
		this.imageDataString = imageDataString;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	

	public Date getErzeugt() {
		return erzeugt == null ? null : (Date) erzeugt.clone();
	}

	public void setErzeugt(Date erzeugt) {
		this.erzeugt = erzeugt == null ? null : (Date) erzeugt.clone();
	}

	

	public final void set(String imageDataString, String filename) {
		
		setImageDataString(imageDataString);
		setBeschreibung(filename);
		
		
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((beschreibung == null) ? 0 : beschreibung.hashCode());
		result = prime * result + ((erzeugt == null) ? 0 : erzeugt.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((imageDataString == null) ? 0 : imageDataString.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", imageDataString=" + imageDataString
				+ ", beschreibung=" + beschreibung + ", erzeugt=" + erzeugt
				+ "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
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
		if (imageDataString == null) {
			if (other.imageDataString != null)
				return false;
		} else if (!imageDataString.equals(other.imageDataString))
			return false;
		return true;
	}
	
	


	



}

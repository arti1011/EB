package de.party.item.domain;

import static de.party.util.Constants.KEINE_ID;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table
@XmlRootElement
public class PartyItem implements Serializable {
	
	private static final long serialVersionUID = 2632447781498478734L;
	
	@Id
	@GeneratedValue
	@Column(nullable = false, updatable = false)
	private Long id = KEINE_ID;
	
    @Column
	private Long party_fk;
    
    @Column
   	private Long mitbringer_fk;
	
	@Column
	private String mitbringer;
	
	@Column(nullable = false)
	private String beschreibung;
	
	
	public PartyItem() {
		super();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}



	public String getBeschreibung() {
		return beschreibung;
	}


	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}


	public Long getParty_fk() {
		return party_fk;
	}


	public void setParty_fk(Long party_fk) {
		this.party_fk = party_fk;
	}


	public String getMitbringer() {
		return mitbringer;
	}


	public void setMitbringer(String mitbringer) {
		this.mitbringer = mitbringer;
	}
	
	


	public Long getMitbringer_fk() {
		return mitbringer_fk;
	}


	public void setMitbringer_fk(Long mitbringer_fk) {
		this.mitbringer_fk = mitbringer_fk;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "PartyItem [id=" + id + ", party_fk=" + party_fk
				+ ", mitbringer=" + mitbringer + ", beschreibung="
				+ beschreibung + "]";
	}
	
	





}

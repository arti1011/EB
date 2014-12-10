package de.party.item.domain;

import static de.party.util.Constants.KEINE_ID;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table
@XmlRootElement
public class PartyItem implements Serializable {
	
	private static final long serialVersionUID = 2632447781498478734L;
	
	@Id
	@GeneratedValue
	@Column(nullable = false, updatable = false)
	private Long id = KEINE_ID;
	
	@Column(nullable = false)
	private Long party_id;
	
	@Column(nullable = false)
	private Long item_id;
	
	@Column(nullable = true)
	private String mitbringer;
	
	@Column(nullable = false)
	private boolean nochoffen;
	
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

	public PartyItem(Long party_id, Long item_id, String mitbringer, boolean nochoffen) {
		super();
		set(party_id, item_id, mitbringer, nochoffen);
	}
	
	@PrePersist
	protected void prePerstist() {
		aktualisiert = new Date();
	}
	
	@PreUpdate
	protected void preUpdate() {
		aktualisiert = new Date();
	}
	

	public boolean isNochoffen() {
		return nochoffen;
	}

	public void setNochoffen(boolean nochoffen) {
		this.nochoffen = nochoffen;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParty_id() {
		return party_id;
	}

	public void setParty_id(Long party_id) {
		this.party_id = party_id;
	}

	public Long getItem_id() {
		return item_id;
	}

	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}

	public String getMitbringer() {
		return mitbringer;
	}

	public void setMitbringer(String mitbringer) {
		this.mitbringer = mitbringer;
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
		result = prime * result + ((erzeugt == null) ? 0 : erzeugt.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((item_id == null) ? 0 : item_id.hashCode());
		result = prime * result
				+ ((mitbringer == null) ? 0 : mitbringer.hashCode());
		result = prime * result + (nochoffen ? 1231 : 1237);
		result = prime * result
				+ ((party_id == null) ? 0 : party_id.hashCode());
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
		if (item_id == null) {
			if (other.item_id != null)
				return false;
		} else if (!item_id.equals(other.item_id))
			return false;
		if (mitbringer == null) {
			if (other.mitbringer != null)
				return false;
		} else if (!mitbringer.equals(other.mitbringer))
			return false;
		if (nochoffen != other.nochoffen)
			return false;
		if (party_id == null) {
			if (other.party_id != null)
				return false;
		} else if (!party_id.equals(other.party_id))
			return false;
		return true;
	}
	
	

	@Override
	public String toString() {
		return "PartyItem [id=" + id + ", party_id=" + party_id + ", item_id="
				+ item_id + ", mitbringer=" + mitbringer + ", nochoffen="
				+ nochoffen + ", erzeugt=" + erzeugt + ", aktualisiert="
				+ aktualisiert + "]";
	}

	public final void set(Long party_id, Long item_id, String mitbringer, boolean nochoffen) {
		
		setParty_id(party_id);
		setItem_id(item_id);
		setMitbringer(mitbringer);
		setNochoffen(nochoffen);
		
		
	}
	
	
	

}

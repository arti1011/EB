package de.party.nutzer.domain;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table
@XmlRootElement
public class ProfilePicture implements Serializable {
	private static final long serialVersionUID = 2632441781425778734L;

	@Id
	@Column(unique = true, nullable = false)
	private Long user_id;
	
	@Lob
	@Column(nullable = false)
	private String imageDataString; 
	
	@Lob
	@Column(nullable = false)
	private String imageDataStringSmall; 

	@Column(nullable = false)
	@Temporal(TIMESTAMP)
	private Date erzeugt;

	public ProfilePicture() {
		super();
	}
	

	public ProfilePicture(String imageDataString, String imageDataStringSmall, Long user_id) {
		super();
		set(imageDataString, user_id, imageDataStringSmall);
	}

	@PrePersist
	protected void prePersist() {
		erzeugt = new Date();
	}
	
	public final void set(String imageDataString, Long userid, String imageDataStringSmall) {
		
		setImageDataString(imageDataString);
		setUser_id(userid);
		setImageDataStringSmall(imageDataStringSmall);
		
		
	}

	public String getImageDataString() {
		return imageDataString;
	}

	

	public String getImageDataStringSmall() {
		return imageDataStringSmall;
	}


	public void setImageDataStringSmall(String imageDataStringSmall) {
		this.imageDataStringSmall = imageDataStringSmall;
	}


	public void setImageDataString(String imageDataString) {
		this.imageDataString = imageDataString;
	}


	public Long getUser_id() {
		return user_id;
	}


	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}


	public Date getErzeugt() {
		return erzeugt;
	}


	public void setErzeugt(Date erzeugt) {
		this.erzeugt = erzeugt;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((erzeugt == null) ? 0 : erzeugt.hashCode());
		result = prime * result
				+ ((imageDataString == null) ? 0 : imageDataString.hashCode());
		result = prime * result + ((user_id == null) ? 0 : user_id.hashCode());
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
		ProfilePicture other = (ProfilePicture) obj;
		if (erzeugt == null) {
			if (other.erzeugt != null)
				return false;
		} else if (!erzeugt.equals(other.erzeugt))
			return false;
		if (imageDataString == null) {
			if (other.imageDataString != null)
				return false;
		} else if (!imageDataString.equals(other.imageDataString))
			return false;
		if (user_id == null) {
			if (other.user_id != null)
				return false;
		} else if (!user_id.equals(other.user_id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "ProfilePicture [user_id=" + user_id + ", imageDataString="
				+ imageDataString + ", erzeugt=" + erzeugt + "]";
	}
	
	

	
	
	
	
}
	
package de.party.nutzer.domain;



import static javax.persistence.TemporalType.TIMESTAMP;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.CascadeType.PERSIST;

import java.io.Serializable;
import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import de.party.party.domain.Party;
import static de.party.util.Constants.KEINE_ID;
import static de.party.util.Constants.DEFAULT_PICTURE;




@Entity
@Table
@NamedQueries( {
	@NamedQuery(name = Nutzer.FIND_NUTZER_BY_EMAIL, 
					query = "SELECT n"
								+ " FROM Nutzer n"
									+ " WHERE n.email = :" + Nutzer.EMAIL_QUERY_PARAM) ,
	@NamedQuery(name = Nutzer.FIND_NUTZER_BY_NACHNAME,
					query = "SELECT n"
							+ " FROM Nutzer n"
							+ " WHERE UPPER(n.nachname) = UPPER(:" + Nutzer.NACHNAME_QUERY_PARAM + ")") ,
	@NamedQuery(name = Nutzer.FIND_NUTZER_BY_NACHNAME_PREFIX,
						query = "SELECT n"
									+ " FROM Nutzer n"
									+ " WHERE UPPER (n.nachname) LIKE :" + Nutzer.NACHNAME_QUERY_PARAM),
//	@NamedQuery(name = Nutzer.FIND_FRIENDS_BY_ID,
//						query = "SELECT DISTINCT n"
//								+ " FROM Nutzer n  LEFT JOIN FETCH n.myFriends"
//								+ " WHERE n.id = :" + Nutzer.ID_QUERY_PARAM)		
})
@XmlRootElement
public class Nutzer implements Serializable {
	private static final long serialVersionUID = 4618817696314640065L;
	
	// Konstanten für Queries
	public final static String EMAIL_QUERY_PARAM  = "email";
	public final static String FIND_NUTZER_BY_EMAIL = "findNutzerByEmail";
	
	public final static String FIND_NUTZER_BY_NACHNAME = "findNutzerByNachname";
	public final static String FIND_NUTZER_BY_NACHNAME_PREFIX = "findNutzerByNachnamePrefix";
	public final static String NACHNAME_QUERY_PARAM = "nachname";
	
	public final static String FIND_FRIENDS_BY_ID = "findFriendsById";
	public final static String ID_QUERY_PARAM = "id";
	
		
		
	@Id
	@GeneratedValue
	@Column(nullable = false, updatable = false)
	private Long id = KEINE_ID;
	
	
	@Column(nullable = false)
	@NotNull
	private Long picture_id = DEFAULT_PICTURE;
	

	// Freunde Relation
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
	private Set<Freundschaft> myFriends = new HashSet<>();
	
	// Ist Veranstalter
	@OneToMany
	@JoinColumn(name="veranstalter", nullable = false)
	@XmlTransient
	private List<Party> veranstalter;
	
	@Column(nullable = false)
	@NotNull(message = "{nutzerverwaltung.email.notNull")
	@Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
		        +"[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
		        +"(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
		             message="{nutzerverwaltung.email.invalid}")
	private String email;
	
	@Column(nullable = false)
	@NotNull(message = "{nutzerverwaltung.nachname.notNull}")
	private String nachname;
	
	@Column(nullable = false)
	@NotNull(message = "{nutzerverwaltung.vorname.notNull}")
	private String vorname;	
	
		
	@Version
	@Basic(optional = false)
	private int version = 1;

	//TODO JSON IGNORE
	@NotNull(message = "{nutzerverwaltung.password.notNull}")
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

	@OneToOne(cascade = {PERSIST, REMOVE}, mappedBy = "nutzer")
	private Adresse adresse;
	
	public Nutzer() {
		super();
	}
	
	

	public Long getPicture_id() {
		return picture_id;
	}



	public void setPicture_id(Long picture_id) {
		this.picture_id = picture_id;
	}



	public Nutzer(String nachname, String vorname, String email) {
		super();
		this.nachname = nachname;
		this.vorname = vorname;
		this.email = email;
		
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
	
	@XmlTransient
	public List<Party> getVeranstalter() {
		if (veranstalter == null) {
			return null;
		}
		return Collections.unmodifiableList(veranstalter);
	}

	public void setVeranstalter(List<Party> veranstalter) {
		
		if (this.veranstalter == null) {
			this.veranstalter = veranstalter;
			return;
		}
		
		// Wiederverwendung der vorhandenen Collection
		this.veranstalter.clear();
		if (veranstalter != null) {
			this.veranstalter.addAll(veranstalter);
		}
		
	}

	
	public Adresse getAdresse() {
		return adresse;
	}


	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	@XmlTransient
	public Set<Freundschaft> getMyFriends() {
		return myFriends;
	}

	@Transient
	private URI friendsUri;
	
	public void setMyFriends(Set<Freundschaft> myFriends) {
		this.myFriends = myFriends;
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

	public URI getFriendsUri() {
		return friendsUri;
	}

	public void setFriendsUri(URI friendsUri) {
		this.friendsUri = friendsUri;
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
		return "Nutzer [id=" + id + ", email=" + email + ", nachname="
				+ nachname + ", vorname=" + vorname + ", password=" + password
				+ ", aktualisiert=" + aktualisiert + "]";
	}

	

	



	



}

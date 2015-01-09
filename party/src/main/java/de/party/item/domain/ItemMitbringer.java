package de.party.item.domain;

import static de.party.util.Constants.KEINE_ID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import de.party.nutzer.domain.Nutzer;

/**
 * Relation zwischen PartyItem und dem Teilnehmer (=Mitbringer) einer Party.
 * 
 * 1 Partyitem kann von n Personen mitgebracht werden
 * 
 * @author Michael
 *
 */
@Entity
@Table
@XmlRootElement
public class ItemMitbringer {
	
	@Id
	@GeneratedValue
	@Column(nullable = false, updatable = false)
	private Long id = KEINE_ID;
	
	@ManyToOne
	@JoinColumn(name="mitbringer_id", updatable = false, insertable = false, referencedColumnName = "id")
	private Nutzer mitbringer;
	
	@ManyToOne
	@JoinColumn(name="partyitem_id", updatable = false, insertable = false, referencedColumnName = "id")
	private PartyItem partyItem;
	
	@Column(nullable = false)
	private int anzahl;
	
	
	

}

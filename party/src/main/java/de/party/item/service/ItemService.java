package de.party.item.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.party.item.domain.Item;
import de.party.item.domain.PartyItem;

public class ItemService {
	
	
	
	@PersistenceContext
	private EntityManager em;
	
	
	public List<Item> findAllItems() {
		
		
		Query query = em.createQuery("SELECT i FROM Item i");
	    return (List<Item>) query.getResultList();
			
		
	}
	
	
	public Item addItem(Item item) {

		
		
		if (item == null) {
			return null;
		}
		
		

		item.setErzeugt(new Date());
		
		
		em.persist(item);
		
		return item;
	}
	
	public List<PartyItem> addPartyItems(List<PartyItem> items) {

		
		
		if (items == null) {
			return null;
		}
		
		for (PartyItem item : items) {
		    item.setErzeugt(new Date());
		    item.setAktualisiert(new Date());
		}

		
		
		em.persist(items);
		
		return items;
	}
	
	public List<PartyItem> findPartyItemsById(Long id) {
		
		Query query = em.createQuery("SELECT i FROM PartyItem i where i.party_id =" + id+"");
	    return (List<PartyItem>) query.getResultList();
		
	}
	
	
	

}
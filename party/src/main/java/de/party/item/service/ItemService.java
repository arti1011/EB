//package de.party.item.service;
//
//import java.util.Date;
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//
//import de.party.item.domain.PartyItem;
//
//public class ItemService {
//	
//	
//	
//	@PersistenceContext
//	private EntityManager em;
//	
//	
//	
//
//	
//	public List<PartyItem> findPartyItemsById(Long id) {
//		
//		Query query = em.createQuery("SELECT i FROM PartyItem i where i.party_id =" + id+"");
//	    return (List<PartyItem>) query.getResultList();
//		
//	}
//	
//	
//	
//
//}

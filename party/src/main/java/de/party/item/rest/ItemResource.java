//package de.party.item.rest;
//
//import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
//import static javax.ws.rs.core.MediaType.APPLICATION_XML;
//
//import java.util.List;
//import java.util.NoSuchElementException;
//
//import javax.enterprise.context.RequestScoped;
//import javax.inject.Inject;
//import javax.transaction.Transactional;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.UriInfo;
//
//
//
//
//
//import org.jboss.logging.Logger;
//
//import de.party.item.service.ItemService;
//import de.party.item.domain.PartyItem;
//import de.party.nutzer.domain.Nutzer;
//import de.party.nutzer.rest.NutzerResource;
//
//
//
//
//
//
//
//@Path("/partyitem")
//@Produces(APPLICATION_JSON)
//@Consumes
//@RequestScoped
//public class ItemResource {
//	
//	private static final Logger LOGGER = Logger.getLogger(ItemResource.class);
//	
//	
//	@Context
//	private UriInfo uriInfo;
//	
//	@Inject 
//	private ItemService is;
//	
//	@GET
//	public Response findAllItems() {
//		
////		List<PartyItem> items;
//		
//		items = is.findAllItems();
//		
//		if(items ==  null || items.isEmpty()) {
//			throw new NoSuchElementException();
////		}
//		
//		
//		return Response.ok(items).build();
//	}
//	
//	@POST
//	@Consumes(APPLICATION_JSON)
//	@Produces({APPLICATION_JSON, APPLICATION_XML})
//	@Transactional
//	public Response addItem(PartyItem item) {
//		
//		
//		item = is.addItem(item);
//		
//		
//	
//		return Response.ok(item).build();
//				
//				
//		
//	}
//	
//	@Path("/partyitems")
//	@POST
//	@Consumes(APPLICATION_JSON)
//	@Produces({APPLICATION_JSON, APPLICATION_XML})
//	@Transactional
//	public Response addPartyItem(List<PartyItem> items) {
//		
//		if(items.isEmpty())
//		{
//			return Response.notAcceptable(null).build();
//		}
//		
//		items = is.addPartyItems(items);
//		
//		
//	
//		return Response.noContent().build();
//				
//				
//		
//	}
//	
//	@GET
//	@Path("{id:[1-9][0-9]*}")
//	public Response findPartyItemsById(@PathParam("id") Long id) {
//		final List<PartyItem> items = is.findPartyItemsById(id);
//		
//		if(items ==  null || items.isEmpty()) {
//			throw new NoSuchElementException();
//		}
//		
//		
//		return Response.ok(items).build();
//	}
//	
//	
//	
//	
//	
//
//}

package de.party.nutzer.rest;

import java.util.NoSuchElementException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import de.party.nutzer.domain.Nutzer;
import de.party.nutzer.service.NutzerService;


@Path("/user")
@Produces(APPLICATION_JSON)
@Consumes
@RequestScoped
public class NutzerResource {

		
	@Context
	private UriInfo uriInfo;
	
	@Inject 
	private NutzerService ns;
	
	@GET
	@Produces(TEXT_PLAIN)
	@Path("version")
	public String version() {
		return "1.0";
	}
	
	@Deprecated
	@GET
	@Path("/login")
	public Response login(@QueryParam("email") String email, 
						  @QueryParam("password") String password) {
		Nutzer nutzer = ns.authService(email, password);
		if (nutzer == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(nutzer).build();
	}
	
	@GET
	@Path("{email}")
	public Response findNutzerByEmail(@PathParam("email") String email) {
		final Nutzer nutzer = ns.findNutzerByEmail(email);
		
		if(nutzer ==  null) {
			throw new NoSuchElementException();
		}
		
		return Response.ok().build();
	}
	
	@GET
	@Path("{id:[1-9][0-9]*}")
	public Response findNutzerById(@PathParam("id") Long id) {
		final Nutzer nutzer = ns.findNutzerById(id);
		
		if(nutzer ==  null) {
			throw new NoSuchElementException();
		}
		
		
		return Response.ok(nutzer).build();
	}
	
	
	@Deprecated
	@POST
	@Consumes(APPLICATION_JSON)
	@Produces({APPLICATION_JSON, APPLICATION_XML})
	@Transactional
	public Response registrateUser(@Valid Nutzer nutzer) {
		
		
		nutzer = ns.registrateUser(nutzer);
		//TODO EmailExists Exception anhand Jürgen Stack einbinden
		// Erstmal nur ein HTTP-OK zurückgeben
		return Response.ok(nutzer).build();
				
				
		
	}
}

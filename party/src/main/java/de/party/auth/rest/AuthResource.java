package de.party.auth.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;




import de.party.auth.service.AuthService;
import de.party.nutzer.domain.Nutzer;
import de.party.nutzer.service.NutzerService;

@Path("/auth")
@Produces(APPLICATION_JSON)
@Consumes
@RequestScoped
public class AuthResource {
	
	@Inject
	private NutzerService ns;
	
	@Inject
	private AuthService as;

	
	//TODO NotFoundException einbauen
	@GET
	@Path("/login")
	public Response login(@QueryParam("email") String email,
			@QueryParam("password") String password) {
		Nutzer nutzer = as.login(email, password);
		if (nutzer == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(nutzer).build();
	}
	
	@POST
	@Path("/register")
	@Consumes(APPLICATION_JSON)
	@Produces({APPLICATION_JSON, APPLICATION_XML})
	@Transactional
	public Response registrateUser(@Valid Nutzer nutzer) {
		
		
		nutzer = ns.registrateUser(nutzer);
		
		return Response.ok(nutzer).build();
	}
	
}

package de.party.nutzer.rest;

import java.util.List;
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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;






import org.jboss.logging.Logger;

import com.google.common.base.Strings;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import de.party.nutzer.domain.Nutzer;
import de.party.nutzer.service.NutzerService;
import de.party.util.rest.NotFoundException;


@Path("/user")
@Produces(APPLICATION_JSON)
@Consumes
@RequestScoped
public class NutzerResource {

			
	private static final Logger LOGGER = Logger.getLogger(NutzerResource.class);
	
	// Exception Message Keys
	private static final String NOT_FOUND_MAIL  = "nutzer.notFound.email";
	private static final String NOT_FOUND_NACHNAME = "nutzer.notFound.nachname";
	
	@Context
	private UriInfo uriInfo;
	
	@Inject 
	private NutzerService ns;
	
	@GET
	@Produces(TEXT_PLAIN)
	@Path("version")
	public String version() {
		
		LOGGER.info("VERSION REQUESTED");
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
	
	/**
	 * Nutzer anhand der Email suchen, QueryParam = email
	 * 
	 * Aufruf: /?email=mumi1012@test.de
	 * 
	 * @param email
	 * @return Nutzer
	 * 
	 * @throws NotFoundException
	 */
	@GET
	public Response findNutzer(@QueryParam(Nutzer.EMAIL_QUERY_PARAM) String email,
							   @QueryParam(Nutzer.NACHNAME_QUERY_PARAM) String nachname
			) {
		
		
		Nutzer nutzer = null;
		
		List<Nutzer> nutzerListe = null;
		
		if (!Strings.isNullOrEmpty(email)) {
			nutzer = ns.findNutzerByEmail(email);
			
			if (nutzer == null) {
				throw new NotFoundException(NOT_FOUND_MAIL, email);
			}
		}
		
		else if (!Strings.isNullOrEmpty(nachname)) {
			nutzerListe = ns.findNutzerByNachname(nachname);
			
			if (nutzerListe.isEmpty()) {
				throw new NotFoundException(NOT_FOUND_NACHNAME, nachname);
			}
		}
		
		Object entity = null;
		if (nutzerListe != null) {
			entity = new GenericEntity<List<Nutzer>>(nutzerListe) { };
		}
		
		else if (nutzer != null) {
			entity = nutzer;
		}
		
		return Response.ok(entity).build();
	}
	
	/**
	 * Nutzer anhand Nachnamen-Prefix suchen (Wildcard-Suche)
	 * 
	 * Beispiel: user/prefix/nachname/mue
	 *  
	 * @param nachnamePrefix
	 * @return Nutzer
	 * 
	 * @throws NotFoundException
	 */
	@GET
	@Path("/prefix/nachname/{nachname}")
	public Response findNutzerByNachnamePrefix(@PathParam("nachname") String nachnamePrefix) {
		final List<Nutzer> nutzer = ns.findNutzerByNachnamePrefix(nachnamePrefix);
		
		if (nutzer.isEmpty()) {
			throw new NotFoundException(NOT_FOUND_NACHNAME, nachnamePrefix);
		}
		
		return Response.ok(nutzer).build();
		
	}
	
	
	/**
	 * Methode um einen Nutzer anhand der internen ID auszulesen
	 * 
	 * @param id
	 * @return Nutzer
	 */
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

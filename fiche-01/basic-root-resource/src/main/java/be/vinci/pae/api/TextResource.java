package be.vinci.pae.api;

import java.util.List;
import java.util.stream.Stream;

import be.vinci.pae.domain.Text;
import be.vinci.pae.domain.Text.Level;
import be.vinci.pae.services.DataServiceTextCollection;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Singleton
@Path("/texts")
public class TextResource {
	
	
	/*
	 * READ ALL FILTERED : Lire toutes les ressources de la collection selon le filtre donné
	 * */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Text> getAllTextFilter(@QueryParam("level") String level){
		System.err.println("p9 "+level);
		if(!Stream.of(Level.values()).anyMatch(x->x.getLevel1().equals(level))) {
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity("Lacks of mandatory info or unauthorized text level").type("text/plain").build());
		}
		
		return DataServiceTextCollection.getAllTextFilter(level);
		
		
		
	}
	/*
	 * READ ALL : Lire toutes les ressources de la collection
	 * */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Text> getAllText(){
		System.out.println(DataServiceTextCollection.getAllText().size());
		return DataServiceTextCollection.getAllText();
		
	}
	
	
	
	
	/*
	 * CREATE ONE : Créer une ressource basée sur les données de la requête
	 * */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Text create(Text t) {
		
		if(t==null || t.getContent().isEmpty() )
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity("Lacks of mandatory info").type("text/plain").build());
		
		DataServiceTextCollection.addText(t);
		
		
		return t;
	}
	
}

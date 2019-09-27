package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import dtomappers.CA2PersonDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Path("/demo")
public class CA2PersonResource {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Find pet by ID",
            tags = {"persons"},
            description = "Returns a person when id matches a record in the database",
            responses = {
                @ApiResponse(description = "The person", content = @Content(
                        schema = @Schema(implementation = CA2PersonDTO.class)
                )),
                @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
                @ApiResponse(responseCode = "404", description = "Person not found")
            })
    
    public CA2PersonDTO getPerson(@PathParam("id") int id) {
        //Eventually we will fetch from DB
        Set phones = new HashSet();
        phones.add("12345");
        phones.add("3435");
        //return gson.toJson(new CA2PersonDTO(1,"ib","Lyngbyvej 34","Lyngby","2800","233131313,2313131312","Beer, Football, Programming"));
        return new CA2PersonDTO(1, "ib", "Lyngbyvej 34", "Lyngby", "2800", phones, "Beer, Football, Programming");
    }

    @GET
    @Path("simple/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CA2PersonDTO getPersonSimple(@PathParam("id") int id) {
        //Eventually we will fetch from DB
        Set phones = new HashSet();
        phones.add("12345");
        phones.add("3435");
        //return gson.toJson(new CA2PersonDTO(1,"ib","Lyngbyvej 34","Lyngby","2800","233131313,2313131312","Beer, Football, Programming"));
        return new CA2PersonDTO("ib Hansen", phones);
    }

//      @GET
//      @Path("simple/{id}")
//      @Produces(MediaType.APPLICATION_JSON)
//      public String getPersonSimple(@PathParam("id") int id){
//          //Eventually we will fetch from DB
//          Set phones = new HashSet();
//          phones.add("12345");
//          phones.add("3435");
//          //return gson.toJson(new CA2PersonDTO(1,"ib","Lyngbyvej 34","Lyngby","2800","233131313,2313131312","Beer, Football, Programming"));
//          return gson.toJson(new CA2PersonDTO("ib Hansen",phones));
//      }
}

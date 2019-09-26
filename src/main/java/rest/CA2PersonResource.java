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

class PersonDTO {
    private int id;
    private String name;
    private String street;
    private String city;
    private String zip;
    private String phones;
    private Set<String> phones2 = new HashSet();
    private String hobbies;

    public PersonDTO(int id, String name, String street, String city, String zip, String phones, String hobbies) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.phones = phones;
        this.hobbies = hobbies;
    }
    public PersonDTO(int id, String name, String street, String city, String zip, Set phones, String hobbies) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.phones2 = phones;
        this.hobbies = hobbies;
    }

    public PersonDTO(String name, String street, String city, String zip) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.zip = zip;
    }
    public PersonDTO(String name, Set<String> phones) {
        this.name = name;
        this.phones2 = phones;
    }
    
    
}
@Path("demo")
public class CA2PersonResource {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
      @GET
      @Path("{id}")
      @Produces(MediaType.APPLICATION_JSON)
      public String getPerson(@PathParam("id") int id){
          //Eventually we will fetch from DB
          Set phones = new HashSet();
          phones.add("12345");
          phones.add("3435");
          //return gson.toJson(new PersonDTO(1,"ib","Lyngbyvej 34","Lyngby","2800","233131313,2313131312","Beer, Football, Programming"));
          return gson.toJson(new PersonDTO(1,"ib","Lyngbyvej 34","Lyngby","2800",phones,"Beer, Football, Programming"));
      }
      
      @GET
      @Path("simple/{id}")
      @Produces(MediaType.APPLICATION_JSON)
      public String getPersonSimple(@PathParam("id") int id){
          //Eventually we will fetch from DB
          Set phones = new HashSet();
          phones.add("12345");
          phones.add("3435");
          //return gson.toJson(new PersonDTO(1,"ib","Lyngbyvej 34","Lyngby","2800","233131313,2313131312","Beer, Football, Programming"));
          return gson.toJson(new PersonDTO("ib Hansen",phones));
      }
}

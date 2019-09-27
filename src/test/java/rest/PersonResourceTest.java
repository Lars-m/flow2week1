package rest;

import dtomappers.PersonDTO;
import entities.Person;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

//Uncomment the line below, to temporarily disable this test
@Disabled
public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Person p1, p2;
   
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.CREATE);
        httpServer = startServer();
        
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
   
        RestAssured.defaultParser = Parser.JSON;
    }
    
    @AfterAll
    public static void closeTestServer(){
         httpServer.shutdownNow();
         EMF_Creator.endREST_TestWithDB();        
    }
    
    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        p1 =new Person("Kim","Hansen","123456789");
        p2 = new Person("pia","Hansen","111111111");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.persist(p1);
            em.persist(p2);
           
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/person").then().statusCode(200);
    }
      
    @Test
    public void testCount() throws Exception {
        given()
        .contentType("application/json")
        .get("/person/count").then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("count", equalTo(2));   
    }
    
    @Test
    public void all()  {
        List<PersonDTO> personDTOs;
        personDTOs= given()    
        .contentType("application/json")
        .when()
        .get("/person/all")
        .then()
        .extract().body().jsonPath().getList("all",PersonDTO.class);
        PersonDTO p1DTO = new PersonDTO(p1);
        PersonDTO p2DTO = new PersonDTO(p2);
        assertThat(personDTOs,containsInAnyOrder(p1DTO,p2DTO));            
    }
    
     @Test
    public void addPerson() {
        given()    
        .contentType("application/json")
        .body(new PersonDTO("Ib","Ibsen","123"))
        .when()
        .post("/person")
        .then()
        .body("fName",equalTo("Ib"))
        .body("lName",equalTo("Ibsen"));
    }
    
    @Test
    public void findPerson()  {
         given()    
        .contentType("application/json")
        .when()
        .get("/person/{id}",p1.getId())
        .then()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("id", equalTo(p1.getId().intValue()));           
    }
    
     @Test
    public void FindNonExistingPerson() {
         given()    
        .contentType("application/json")
        .accept(ContentType.JSON)        
        .when()
        .get("/person/find/{id}",p1.getId()+10)
        .then()
        .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode())
        .body("code",equalTo(404))
        .body("$",hasKey("message")); //Don't bother to check for the actual value  
    }
       
    @Test
    public void editPerson() {
        //change Kim's phone number and last name
        p1.setPhone("1111");
        p1.setLastName("Petersen");
        given()    
        .contentType("application/json")
        .body(new PersonDTO(p1.getFirstName(),p1.getLastName(),p1.getPhone()))
        .when()
        .put("/person/{id}",p1.getId())
        .then()
        //.log().body()
        .body("lName",equalTo("Petersen"))
        .body("phone",equalTo("1111"));
    }
    @Test
    public void deletePerson() {
        System.out.println("------> "+p1.getId());
         given()    
        .contentType("application/json")
        .accept(ContentType.JSON)        
        .when()
        .delete("/person/{id}",p1.getId())
        .then()
        .body("status",equalTo("deleted"));
         
         //Finally check that item was removed from DB
         EntityManager em = emf.createEntityManager();
         try{
             assertThat(em.find(Person.class, p1.getId()), is(nullValue()));
         }finally{
             em.close();
         } 
    }
    @Test
    public void deleteNonExistingPerson() {
         given()    
        .contentType("application/json")
        .accept(ContentType.JSON)        
        .when()
        .delete("/person/{id}",p1.getId()+10)
        .then()
        .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode())
        .body("code",equalTo(404))
        .body("$",hasKey("message")); //Don't bother to check for the actual value  
    }

}

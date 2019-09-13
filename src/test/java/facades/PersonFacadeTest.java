package facades;

import utils.EMF_Creator;
import entities.Person;
import exceptions.PersonNotFoundException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static Person p1, p2;

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = PersonFacade.getFacadeExample(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        p1 = new Person("Kim", "Hansen", "123456789");
        p2 = new Person("Pia", "Hansen", "111111111");
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

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testGetPersonsCount() {
        assertEquals(2, facade.getPersonCount(), "Expects two rows in the database");
    }

    @Test
    public void addPerson() {
        Person p = facade.addPerson("aaa", "bbb", "ccc");
        assertNotNull(p.getId());

        EntityManager em = emf.createEntityManager();
        try {
            List<Person> persons = em.createQuery("select p from Person p").getResultList();
            assertEquals(3, persons.size(), "Expects 3 persons in the DB");
        } finally {
            em.close();
        }
    }

    @Test
    public void deleteExcistingPerson() throws PersonNotFoundException {
        facade.deletePerson(p1.getId()); //Delete Kim
        EntityManager em = emf.createEntityManager();
        try {
            List<Person> persons = em.createQuery("select p from Person p").getResultList();
            assertEquals(1, persons.size(), "Expects 1 person in the DB");
        } finally {
            em.close();
        }
    }

    @Test
    public void deleteNotExcistingPerson() throws PersonNotFoundException {
        Assertions.assertThrows(PersonNotFoundException.class, () -> {
            facade.deletePerson(786545242);
        });
    }
    
    @Test
    public void findExcistingPerson() throws PersonNotFoundException{
        Person person = facade.getPerson(p1.getId());
        assertEquals("Kim", person.getFirstName(),"Expects to find Kim");
    }

    @Test
    public void findNonExcistingPerson() throws PersonNotFoundException {
        Assertions.assertThrows(PersonNotFoundException.class, () -> {
            facade.getPerson(786545242);
        });
    }
    
    @Test
    public void getAllPersons(){
        List<Person> persons = facade.getAllPersons();
        assertThat(persons, hasSize(2));
        //assertThat(persons,hasItems(p1,p2));
        assertThat(persons,containsInAnyOrder(p1,p2));
    }
    
    @Test
    public void editExistingPerson() throws PersonNotFoundException{
        p1.setPhone("555");
        long lastEditedBefore = p1.getLastEdited().getTime();
        Person pEdited = facade.editPerson(p1);
        assertEquals(p1.getPhone(), pEdited.getPhone());
        assertThat(pEdited.getLastEdited().getTime(),greaterThan(lastEditedBefore));
    }
    
}

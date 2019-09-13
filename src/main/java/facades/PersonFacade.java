package facades;

import entities.Person;
import exceptions.PersonNotFoundException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //TODO Remove/Change this before use
    public long getPersonCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long renameMeCount = (long) em.createQuery("SELECT COUNT(r) FROM Person r").getSingleResult();
            return renameMeCount;
        } finally {
            em.close();
        }

    }

    @Override
    public Person addPerson(String fName, String lName, String phone) {
        EntityManager em = getEntityManager();
        Person person = new Person(fName, lName, phone);
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return person;
    }

    @Override
    public Person deletePerson(long id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, id);
        if (person == null) {
            throw new PersonNotFoundException(String.format("Person with id: (%d) not found", id));
        }
        try {
            em.getTransaction().begin();
            em.remove(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return person;
    }

    @Override
    public Person getPerson(long id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        try {
            Person person = em.find(Person.class, id);
            if (person == null) {
                throw new PersonNotFoundException(String.format("Person with id: (%d) not found", id));
            }
            return person;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Person> getAllPersons() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT p from Person p").getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Person editPerson(Person p) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, p.getId());
        if (person == null) {
            throw new PersonNotFoundException(String.format("Person with id: (%d) not found", p.getId()));
        }
        person.setFirstName(p.getFirstName());
        person.setLastName(p.getLastName());
        person.setPhone(p.getPhone());
        try {
            em.getTransaction().begin();
            person.setLastEdited(new Date());
            em.merge(person);
            em.getTransaction().commit();
            return person;
        } finally {
            em.close();
        }
    }

}

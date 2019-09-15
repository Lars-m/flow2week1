package facades;

import entities.Person;
import exceptions.PersonNotFoundException;
import java.util.List;

public interface IPersonFacade {
  public Person addPerson(String fName, String lName, String phone);  
  public Person deletePerson(long id) throws PersonNotFoundException;  
  public Person getPerson(long id) throws PersonNotFoundException;  
  public List<Person> getAllPersons();  
  public Person editPerson(Person p) throws PersonNotFoundException;  
}

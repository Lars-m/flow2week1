package dtomappers;

import entities.Person;
import java.util.ArrayList;
import java.util.List;


public class PersonsDTO {
    
    List<PersonDTO> all = new ArrayList();

    public PersonsDTO(List<Person> personEntities) {
        personEntities.forEach((p) -> {
            all.add(new PersonDTO(p));
        });
    }

    public PersonsDTO() {}

    public List<PersonDTO> getPersonDTOs() {
        return all;
    }
//
//    public void setPersonDTOs(List<PersonDTO> personDTOs) {
//        this.all = personDTOs;
//    }
    
}

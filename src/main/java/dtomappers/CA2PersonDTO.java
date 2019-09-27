package dtomappers;


import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;

@Schema(name = "Person")
@XmlRootElement(name = "Person")
public class CA2PersonDTO {
    private Integer id;
    @Schema(required = true)
    private String name;
    private String street;
    private String by;
    private String zip;
    private String phones;
    private Set<String> phones2 = new HashSet();
    private String hobbies;

    public CA2PersonDTO() {
    }
    

    public CA2PersonDTO(int id, String name, String street, String city, String zip, String phones, String hobbies) {
        this.name = name;
        this.street = street;
        this.by = city;
        this.zip = zip;
        this.phones = phones;
        this.hobbies = hobbies;
    }
    public CA2PersonDTO(int id, String name, String street, String city, String zip, Set phones, String hobbies) {
        this.name = name;
        this.street = street;
        this.by = city;
        this.zip = zip;
        this.phones2 = phones;
        this.hobbies = hobbies;
    }

    public CA2PersonDTO(String name, String street, String city, String zip) {
        this.name = name;
        this.street = street;
        this.by = city;
        this.zip = zip;
    }
    public CA2PersonDTO(String name, Set<String> phones) {
        this.name = name;
        this.phones2 = phones;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ApiModelProperty(value = "vej", required = true)
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public Set<String> getPhones2() {
        return phones2;
    }

    public void setPhones2(Set<String> phones2) {
        this.phones2 = phones2;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }
    
    
    
}
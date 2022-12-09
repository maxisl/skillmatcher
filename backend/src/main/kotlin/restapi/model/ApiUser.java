package restapi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


import javax.persistence.*;

@Entity
@Data
public class ApiUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id; //TODO: Rewrite services to java

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

}
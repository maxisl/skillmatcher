package restapi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


import javax.persistence.*;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(mappedBy = "attendees")
    @JsonIgnore
    public List<Project> attends;

}
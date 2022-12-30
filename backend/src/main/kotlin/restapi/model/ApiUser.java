package restapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import restapi.jsonView.DataView;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class ApiUser {

    @JsonView({DataView.User.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id; //TODO: Rewrite services to java

    @JsonView({DataView.User.class})
    @Column(unique = true)
    private String email;

    // do not show on request
    @JsonIgnore
    private String password;

    @JsonView({DataView.UserWithProjects.class})
    @ManyToMany(mappedBy = "attendees")
    public List<Project> attends;
}
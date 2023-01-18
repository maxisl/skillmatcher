package restapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import restapi.jsonView.DataView;
import javax.persistence.*;

/*
@Entity
@Data
public class ApiUser {

    // user id
    @JsonView({DataView.User.class})
    @Id     // id to indicate that it is the primary key of the table
    @GeneratedValue(strategy = GenerationType.IDENTITY) //  specifies that primary key value should be generated automatically
    public Long id; //TODO: Rewrite services to java

    // user email
    @JsonView({DataView.User.class})
    @Column(unique = true)
    private String email;

    // user password
    @JsonIgnore // do not show on request
    private String password;

    // user attends
    @JsonView({DataView.UserWithProjects.class})
    @ManyToMany(mappedBy = "attendees")
    public List<Project> attends;
}*/
/*

@Entity(name = "User")         // @Entity to map to table
@Data
class User {

    // user id
    @JsonView(DataView.User::class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    // user email
    @JsonView(DataView.User::class)
    @Column(unique = true)
    var email: String? = null

    // user password
    @JsonIgnore // do not show on request
    var password: String? = null

    */
/**  // user attends
@JsonView(DataView.UserWithProjects::class)
@ManyToMany(mappedBy = "attendees")
var attends: MutableList<Project>? = null *//*


    */
/**@JsonView(DataView.UserWithSkill::class)
@ManyToMany(mappedBy = "has_skill")
var has_skill: MutableList<Project>? = null*//*


    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    lateinit var userSkill: Set<UserSkill>

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    lateinit var projectUser: Set<ProjectUser>


    // TODO add skills specified by user - additional column with category of skill mandatory?
    // TODO add profile picture
}
*/

@Entity(name = "users")
data class User(
    @JsonView(DataView.User::class)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,

    // user email
    @JsonView(DataView.User::class)
    @Column(unique = true)
    var email: String? = null,

    // user password
    @JsonIgnore // do not show on request
    var password: String? = null,

    @JsonView(DataView.User::class)
    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "user_project",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "project_id", referencedColumnName = "id")]
    )
    @JsonManagedReference
    var projects: MutableList<Project>,

    @JsonView(DataView.User::class)
    @ManyToMany
    @JoinTable(
        name = "user_skills",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "skill_id", referencedColumnName = "id")]
    )
    var skills: MutableList<Skill> = mutableListOf()

)
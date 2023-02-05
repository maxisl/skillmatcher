package restapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.JsonView;
import restapi.jsonView.DataView;
import javax.persistence.*;

@Entity(name = "users")
data class User(
    @JsonView(DataView.User::class)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long?,

    // user email
    @JsonView(DataView.User::class)
    @Column(unique = true)
    var email: String? = null,

    // user password
    @JsonIgnore // do not show on request
    var password: String? = null,

    @Lob
    @Column
    @JsonView(DataView.Project::class)
    var image: String?,

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
    @JsonBackReference
    var skills: MutableList<Skill> = mutableListOf(),
)

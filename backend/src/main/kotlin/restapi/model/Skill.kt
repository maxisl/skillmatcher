package restapi.model

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonView
import lombok.Data
=======
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.JsonView
import lombok.Data
import org.apache.commons.lang3.mutable.Mutable
>>>>>>> new-db-schema
import restapi.jsonView.DataView
import javax.persistence.*

@Entity
@Data
@Table(name = "skill")
class Skill {
    //skillId
    @JsonView(DataView.Skill::class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    //skill name
    @JsonView(DataView.Skill::class)
    @Column(unique = true)
    lateinit var name: String

<<<<<<< HEAD
    // a user can have many skills
    @OneToMany(mappedBy = "skill", cascade = [CascadeType.ALL], orphanRemoval = true)
    lateinit var userSkill:Set<UserSkill>

    // a project can require many skills
    @OneToMany(mappedBy = "skill", cascade = [CascadeType.ALL], orphanRemoval = true)
    lateinit var projectSkill:Set<ProjectSkill>

    // LEGACY
   /** @JsonView(DataView.UserWithSkill::class)
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
            name= "User_has_skill",
            joinColumns = [JoinColumn(name = "id")],                // names of the columns in the join table that will store the foreign keys to the Project and ApiUser tables
            inverseJoinColumns = [JoinColumn(name = "user_id")])
    @OrderColumn(name = "id")
    val has_skill: MutableList<Skill>? = null*/

}
=======
    @JsonView(DataView.Skill::class)
    @ManyToMany(mappedBy = "requiredSkills")
    @JsonManagedReference
    var requiredByProjects: MutableList<Project> = mutableListOf()

    @JsonView(DataView.Skill::class)
    @ManyToMany(mappedBy = "skills")
    @JsonManagedReference
    var usersWithSkill: MutableList<User> = mutableListOf()
}

// only return skill id and name from getUserSkillsByEmail()
data class SkillDTO(val id: Long?, val name: String)
>>>>>>> new-db-schema

package restapi.model

import com.fasterxml.jackson.annotation.JsonView
import lombok.Data
import org.apache.commons.lang3.mutable.Mutable
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

    @JsonView(DataView.Skill::class)
    @ManyToMany(mappedBy = "requiredSkills")
    var requiredByProjects: MutableList<Project> = mutableListOf()

    @JsonView(DataView.Skill::class)
    @ManyToMany(mappedBy = "skills")
    var usersWithSkill: MutableList<User> = mutableListOf()
}
    // LEGACY
   /** @JsonView(DataView.UserWithSkill::class)
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
            name= "User_has_skill",
            joinColumns = [JoinColumn(name = "id")],                // names of the columns in the join table that will store the foreign keys to the Project and ApiUser tables
            inverseJoinColumns = [JoinColumn(name = "user_id")])
    @OrderColumn(name = "id")
    val has_skill: MutableList<Skill>? = null*/
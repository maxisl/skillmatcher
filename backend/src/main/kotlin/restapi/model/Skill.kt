package restapi.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.JsonView
import lombok.Data
import restapi.jsonView.DataView
import javax.persistence.*

@Entity
@Data
@Table(name = "skills")
class Skill {
    @JsonView(DataView.Skill::class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @JsonView(DataView.Skill::class)
    @Column(unique = true)
    lateinit var name: String

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

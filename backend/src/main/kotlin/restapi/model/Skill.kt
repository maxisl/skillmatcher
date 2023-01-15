package restapi.model

import com.fasterxml.jackson.annotation.JsonView
import lombok.Data
import restapi.jsonView.DataView
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Data
@Table(name = "skill")
class Skill {
    //skillId
    @JsonView(DataView.Skill::class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var skill_id: Long? = null

    //skillname
    @JsonView(DataView.Skill::class)
    @Column(unique = true)
    lateinit var name: String


    @OneToMany(mappedBy = "skill", cascade = [CascadeType.ALL], orphanRemoval = true)
    lateinit var userSkill:Set<UserSkill>

    @OneToMany(mappedBy = "skill", cascade = [CascadeType.ALL], orphanRemoval = true)
    lateinit var projectSkill:Set<ProjectSkill>

    // LEGACY
   /** @JsonView(DataView.UserWithSkill::class)
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
            name= "User_has_skill",
            joinColumns = [JoinColumn(name = "skill_id")],                // names of the columns in the join table that will store the foreign keys to the Project and ApiUser tables
            inverseJoinColumns = [JoinColumn(name = "user_id")])
    @OrderColumn(name = "id")
    val has_skill: MutableList<Skill>? = null*/

}
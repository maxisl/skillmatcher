package restapi.model

import com.fasterxml.jackson.annotation.JsonView
import lombok.Data
import restapi.jsonView.DataView
import javax.persistence.*

@Entity
@Data
@Table(name = "UserSkill")
class UserSkill {
    //userskillId
    @JsonView(DataView.UserWithSkill::class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userskill_id: Long? = null

    @ManyToOne
    @JoinColumn(name = "User_id")
    lateinit var user: User

    @ManyToOne
    @JoinColumn(name = "Skill_id")
    lateinit var skill: Skill

    @Column(name = "Weight")
    var weight: Long? = null

}
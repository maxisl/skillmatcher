/*
package restapi.model

import com.fasterxml.jackson.annotation.JsonView
import lombok.Data
import restapi.jsonView.DataView
import javax.persistence.*

@Entity
@Data
@Table(name = "ProjectSkill")
class ProjectSkill {
    @JsonView(DataView.ProjectWithSkill::class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var projectSkill_id: Long? = null

    @ManyToOne
    @JoinColumn(name="Project_id")
    lateinit var project: Project

    @ManyToOne
    @JoinColumn(name="Skill_id")
    lateinit var skill: Skill

    // TODO restrict to value 1-5?
    @Column(name ="Weight")
    var weight: Long? = null
}*/

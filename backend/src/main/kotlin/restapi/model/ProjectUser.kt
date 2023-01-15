package restapi.model

import com.fasterxml.jackson.annotation.JsonView
import lombok.Data
import restapi.jsonView.DataView
import javax.persistence.*

@Entity
@Data
@Table(name = "ProjectUser")
class ProjectUser {

    @JsonView(DataView.UserWithProjects::class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var projectUser_id: Long? = null

    @ManyToOne
    @JoinColumn(name="User_id")
    lateinit var user:User

    @ManyToOne
    @JoinColumn(name="Project_id")
    lateinit var project: Project
}
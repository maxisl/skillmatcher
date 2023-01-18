package restapi.model

import com.fasterxml.jackson.annotation.JsonView
import lombok.Data
import restapi.jsonView.DataView
import java.io.Serializable
import javax.persistence.*

/*@Entity
@Data
@Table(name = "projectUser")
class ProjectUser {

    @JsonView(DataView.UserWithProjects::class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var projectUserId: Long? = null

    // attendees
    @ManyToOne
    @JoinColumn(name = "User_id")
    lateinit var user: User

    @ManyToOne
    @JoinColumn(name = "Project_id")
    lateinit var project: Project
}*/
/*

@Embeddable
data class UserProjectId(
    var userId: Long,
    var projectId: Long
) : Serializable {
    fun getUserId(): Long {
        return this.userId
    }

    fun setUserId(userId: Long) {
        this.userId = userId
    }

    fun getProjectId(): Long {
        return this.projectId
    }

    fun setProjectId(projectId: Long) {
        this.projectId = projectId
    }

    companion object {
        private const val serialVersionUID = -7375504002986800558L
    }
}


@Entity
data class ProjectUser(
    @EmbeddedId
    val id: UserProjectId,

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    var user: User,

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    var project: Project
)*/

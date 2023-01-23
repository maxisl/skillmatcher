package restapi.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonView
import restapi.jsonView.DataView
import java.sql.Blob
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "projects")
data class Project(
    @JsonView(DataView.Project::class)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long?,

    @JsonView(DataView.Project::class)
    @NotBlank(message = "Name is mandatory")
    val name: String,

    @JsonView(DataView.Project::class)
    @NotBlank(message = "Description is mandatory")
    val description: String,

    @JsonView(DataView.Project::class)
    @NotBlank(message = "MaxAttendees is mandatory")
    val maxAttendees: String,

    @JsonView(DataView.Project::class)
    @ManyToMany(mappedBy = "projects")
    @JsonBackReference
    var attendees: MutableList<User>,

    @JsonView(DataView.Project::class)
    @NotBlank(message = "Start date is mandatory")
    val startDate: String,

    @JsonView(DataView.Project::class)
    @NotBlank(message = "End date is mandatory")
    val endDate: String,

    @JsonView(DataView.Project::class)
    @Lob
    var image: Blob? = null,

    @ManyToMany
    @JoinTable(
        name = "project_required_skills",
        joinColumns = [JoinColumn(name = "project_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "skill_id", referencedColumnName = "id")]
    )
    @JsonBackReference
    var requiredSkills: MutableList<Skill> = mutableListOf()

)

data class ProjectRequest(
    val name: String,
    val description: String,
    val maxAttendees: String,
    val startDate: String,
    val endDate: String,
    val image: Blob?,
    val requiredSkillsIds: List<Long>?
)


package restapi.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonView
import restapi.jsonView.DataView
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
    var name: String,

    @JsonView(DataView.Project::class)
    @NotBlank(message = "Description is mandatory")
    var description: String,

    @JsonView(DataView.Project::class)
    @NotBlank(message = "MaxAttendees is mandatory")
    var maxAttendees: String,

    @JsonView(DataView.Project::class)
    @ManyToMany(mappedBy = "projects")
    @JsonBackReference
    var attendees: MutableList<User>,

    @JsonView(DataView.Project::class)
    @NotBlank(message = "Start date is mandatory")
    var startDate: String,

    @JsonView(DataView.Project::class)
    @NotBlank(message = "End date is mandatory")
    var endDate: String,

    @Lob
    @Column
    @JsonView(DataView.Project::class)
    var image: String?,

    @ManyToMany
    @JoinTable(
        name = "project_required_skills",
        joinColumns = [JoinColumn(name = "project_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "skill_id", referencedColumnName = "id")]
    )
    @JsonBackReference
    var requiredSkills: MutableList<Skill> = mutableListOf(),

)

data class ProjectRequest(
    val name: String,
    val description: String,
    val maxAttendees: String,
    val startDate: String,
    val endDate: String,
    var image: String?,
    val requiredSkillsIds: List<Long>?
)

@JsonView(DataView.Project::class)
data class ProjectWithAttendees(
    val project: Project,
    val attendees: List<User>
)

data class ProjectUpdateDto(
    var name: String?,
    var description: String?,
    var maxAttendees: String?,
    var startDate: String?,
    var endDate: String?
)



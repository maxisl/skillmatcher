package restapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonView
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import restapi.jsonView.DataView
import restapi.jsonView.DataView.UserWithProjects
import javax.persistence.*
import javax.validation.constraints.NotBlank

// It is discouraged to use data classes for entities:
// ------------------------------------------------------
// Naturally, we might argue that we could use Kotlin Data Classes as JPA entities.
// As opposed to what comes naturally here, using data classes as JPA entities is generally discouraged.
// This is mostly because of the comple interactions between the JPA world and those
// default implementations provided by the Kotlin compiler for each data class.


// TODO: NotBlank not working in Kotlin!

@Entity
@Table(name = "tb_projects")
data class Project(
    @JsonView(DataView.Project::class)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,

    @JsonView(DataView.Project::class)
    @NotBlank(message = "Name is mandatory")
    val name: String,

    @JsonView(DataView.Project::class)
    @NotBlank(message = "Description is mandatory")
    val description: String,

    @JsonView(DataView.Project::class)
    @NotBlank(message = "MaxAttendees is mandatory")
    val maxAttendees: String,

    // val skillsNeeded: String,

    // val startDate: Date,

    // val endDate: Date,

    // A User can be the owner of many projects
    @JsonView(DataView.ProjectWithOwner::class)             // specify which fields should be included in the JSON representation of the object
    @ManyToOne(fetch = FetchType.EAGER, optional = false)   // a single ApiUser object can be related to multiple Project objects
    @JoinColumn(name = "owner_id", nullable = true)         // specifies the name of the column in the Project table to store the foreign key pointing to the ApiUser object
    @OnDelete(action = OnDeleteAction.CASCADE)              // when an ApiUser object is deleted, any related Project objects should also be deleted
    var owner: ApiUser?,

    // Many user attend many projects
    @JsonView(DataView.ProjectWithAttendeesAndOwner::class)
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "project_attendees",
        joinColumns = [JoinColumn(name = "project_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")])
    var attendees: MutableList<ApiUser>?
)

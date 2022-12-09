package restapi.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*
import javax.validation.constraints.NotBlank

// It is discouraged to use data classes for entities:
// ------------------------------------------------------
// Naturally, we might argue that we could use Kotlin Data Classes as JPA entities.
// As opposed to what comes naturally here, using data classes as JPA entities is generally discouraged.
// This is mostly because of the comple interactions between the JPA world and those
// default implementations provided by the Kotlin compiler for each data class.

@Entity
@Table(name = "tb_projects")
data class Project(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,

    // TODO: NotBlank not working in Kotlin!
    @NotBlank(message = "Name is mandatory")
    val name: String,

    @NotBlank(message = "Description is mandatory")
    val description: String,

    @NotBlank(message = "MaxAttendees is mandatory")
    val maxAttendees: String,

    // val skillsNeeded: String,

    // val startDate: Date,

    // val endDate: Date,

    // A User can be the owner of many projects
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "owner_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    var owner: ApiUser?,

    // Many user attend many projects
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "project_attendees",
        joinColumns = [JoinColumn(name = "project_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")])
    var attendees: MutableList<ApiUser>?
)

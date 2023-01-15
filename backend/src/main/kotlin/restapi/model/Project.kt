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
@Table(name = "Projects")
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

    //Wie viele reindürfen
    // TODO should be Int not String?
    @JsonView(DataView.Project::class)
    @NotBlank(message = "MaxAttendees is mandatory")
    val maxAttendees: String,

    // = attendees?
    @OneToMany(mappedBy = "project", cascade = [CascadeType.ALL], orphanRemoval = true)
    var projectUser:Set<ProjectUser>,

    @OneToMany(mappedBy = "project", cascade = [CascadeType.ALL], orphanRemoval = true)
    var projectSkill:Set<ProjectSkill>


        // val skillsNeeded: String,

    // val startDate: Date,

    // val endDate: Date,

  /**  // A User can be the owner of many projects
    @JsonView(DataView.ProjectWithOwner::class)             // specify which fields should be included in the JSON representation of the object
    @ManyToOne(fetch = FetchType.EAGER, optional = false)   // a single ApiUser object can be related to multiple Project objects
    @JoinColumn(name = "owner_id", nullable = true)         // specifies the name of the column in the Project table to store the foreign key pointing to the ApiUser object
    @OnDelete(action = OnDeleteAction.CASCADE)              // when an ApiUser object is deleted, any related Project objects should also be deleted
    var owner: ApiUser?,*/

   /** // Many user attend many projects
    @JsonView(DataView.ProjectWithAttendeesAndOwner::class)             // a single Project object can be related to multiple ApiUser objects, and a single ApiUser object can be related to multiple Project objects
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)    // specifies the name of the join table that will be used to store the many-to-many relationship between Project and ApiUser objects
    @JoinTable(
        name = "project_attendees",                                     // name of the join table that will be used to store the many-to-many relationship between Project and ApiUser objects
        joinColumns = [JoinColumn(name = "project_id")],                // names of the columns in the join table that will store the foreign keys to the Project and ApiUser tables
        inverseJoinColumns = [JoinColumn(name = "user_id")])
    @OrderColumn(name = "id")
    var attendees: MutableList<ApiUser>?                                // attendees field is a nullable list of ApiUser objects
     */
)

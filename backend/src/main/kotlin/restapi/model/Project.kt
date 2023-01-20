package restapi.model

import com.fasterxml.jackson.annotation.JsonBackReference
<<<<<<< HEAD
=======
import com.fasterxml.jackson.annotation.JsonManagedReference
>>>>>>> new-db-schema
import com.fasterxml.jackson.annotation.JsonView
import restapi.jsonView.DataView
import javax.persistence.*
import javax.validation.constraints.NotBlank

// It is discouraged to use data classes for entities:
// ------------------------------------------------------
// Naturally, we might argue that we could use Kotlin Data Classes as JPA entities.
// As opposed to what comes naturally here, using data classes as JPA entities is generally discouraged.
// This is mostly because of the comple interactions between the JPA world and those
// default implementations provided by the Kotlin compiler for each data class.


// TODO: NotBlank not working in Kotlin!
/*
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

    // TODO mappedBy really correct?
    // attendees
    @OneToMany(mappedBy = "project", cascade = [CascadeType.ALL], orphanRemoval = true)
    var projectUser:Set<ProjectUser>,
<<<<<<< HEAD

    @OneToMany(mappedBy = "project", cascade = [CascadeType.ALL], orphanRemoval = true)
    var projectSkill:Set<ProjectSkill>*/


        // val skillsNeeded: String,
=======
>>>>>>> new-db-schema

    @OneToMany(mappedBy = "project", cascade = [CascadeType.ALL], orphanRemoval = true)
    var projectSkill:Set<ProjectSkill>*/


<<<<<<< HEAD
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
//)

   @Entity
   @Table(name = "Projects")
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
       var users: MutableList<User>,

       @JsonView(DataView.Project::class)
       @OneToMany(mappedBy = "project", cascade = [CascadeType.ALL], orphanRemoval = true)
       var projectSkill:MutableList<ProjectSkill>
   )

data class ProjectRequest(val name: String, val description: String, val maxAttendees: String)
=======
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
//)

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
    val imageLink: String,

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
    val imageLink: String
)

>>>>>>> new-db-schema

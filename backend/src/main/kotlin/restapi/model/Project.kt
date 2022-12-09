package restapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

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

    @ManyToOne(fetch = FetchType.EAGER, optional = false) //LAZY
    @JoinColumn(name = "api_user_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    var apiUser: ApiUser?,

    val name: String,

    val age: Int,

    val nationality: String
)
package restapi.model

import javax.persistence.*

@Entity
@Table(name = "tb_user")
data class User (
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long,
    val name: String,
    val age: Int,
    val nationality: String
)

/*
@Entity
@Table(name = "users")
class sdaf (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long,

    @Column(nullable = false, unique = true, length = 45)
    private val email: String,

    @Column(nullable = false, length = 64)
    private val password: String,

    @Column(name = "first_name", nullable = false, length = 20)
    private val firstName: String,

    @Column(name = "last_name", nullable = false, length = 20)
    private val lastName: String,
)
 */
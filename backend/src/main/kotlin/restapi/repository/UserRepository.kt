package restapi.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import restapi.model.User

@Repository
interface UserRepository : JpaRepository<User, Long>{
    fun findUserByEmail(email: String): User?

    // TODO get projects for user
    // @Query("SELECT u.projects FROM User u WHERE u.email = :userEmail")
    //fun findByEmail(userEmail: String): MutableList<Project>
}
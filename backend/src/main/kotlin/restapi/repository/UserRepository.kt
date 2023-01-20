package restapi.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
<<<<<<< HEAD
import restapi.model.ProjectUser
=======
import restapi.model.Project
>>>>>>> new-db-schema
import restapi.model.User

@Repository
interface UserRepository : JpaRepository<User, Long>{
    fun findUserByEmail(email: String): User?
<<<<<<< HEAD
=======

    // TODO get projects for user
    // @Query("SELECT u.projects FROM User u WHERE u.email = :userEmail")
    //fun findByEmail(userEmail: String): MutableList<Project>
>>>>>>> new-db-schema
}
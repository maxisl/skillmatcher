package restapi.repository

import restapi.model.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import restapi.model.User
import javax.validation.constraints.Email

@Repository
interface ProjectRepository : JpaRepository<Project, Long> {
    fun findByNameContaining(name: String): MutableList<Project>
<<<<<<< HEAD
    // fun findByProjectUserEmail(userEmail: String): MutableList<Project>
=======

    // fun findByEmail(userEmail: String): MutableList<Project>
    @Query("SELECT p.attendees FROM Project p WHERE p.id = :id")
    fun findProjectAttendeesById(id: Long): List<User>

    // @Query("SELECT u.projects FROM User u WHERE u.email = :userEmail")
    // fun findByEmail(userEmail: String): MutableList<Project>
>>>>>>> new-db-schema
}
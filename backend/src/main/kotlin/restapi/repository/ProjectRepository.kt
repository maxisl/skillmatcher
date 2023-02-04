package restapi.repository

import restapi.model.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import restapi.model.User

@Repository
interface ProjectRepository : JpaRepository<Project, Long> {
    fun findByName(name: String): Project?

    @Query("SELECT p.attendees FROM Project p WHERE p.id = :id")
    fun findProjectAttendeesById(id: Long): List<User>
}
package restapi.repository

import restapi.model.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepository : JpaRepository<Project, Long>{
    fun findByNameContaining(name: String): MutableList<Project>
    // fun findByProjectUserEmail(userEmail: String): MutableList<Project>
}
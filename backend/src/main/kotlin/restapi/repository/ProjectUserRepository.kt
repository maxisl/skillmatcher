/*
package restapi.projectRepository

import org.springframework.data.jpa.projectRepository.JpaRepository
import org.springframework.stereotype.Repository
import restapi.model.ProjectUser
import restapi.model.User

@Repository
interface ProjectUserRepository : JpaRepository<ProjectUser, Long>{
    // fun findProjectUserByProjectUser_id(id: Long): ProjectUser?
    fun findByUser(user: User): Set<ProjectUser>
}

*/

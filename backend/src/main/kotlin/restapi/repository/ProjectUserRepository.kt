<<<<<<< HEAD
package restapi.repository

import org.springframework.data.jpa.repository.JpaRepository
=======
/*
package restapi.projectRepository

import org.springframework.data.jpa.projectRepository.JpaRepository
>>>>>>> new-db-schema
import org.springframework.stereotype.Repository
import restapi.model.ProjectUser
import restapi.model.User

@Repository
interface ProjectUserRepository : JpaRepository<ProjectUser, Long>{
    // fun findProjectUserByProjectUser_id(id: Long): ProjectUser?
    fun findByUser(user: User): Set<ProjectUser>
}

<<<<<<< HEAD
=======
*/
>>>>>>> new-db-schema

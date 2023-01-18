/*
package restapi.projectService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.projectRepository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import restapi.model.ProjectUser
import restapi.model.User
import restapi.projectRepository.ProjectUserRepository


@Service
class ProjectUserService(@Autowired val projectRepository: ProjectUserRepository) {

    fun getAll(): MutableList<ProjectUser> = projectRepository.findAll()

    fun getById(id: Long): ProjectUser = projectRepository.findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    fun getByUser(user: User): Set<ProjectUser> = projectRepository.findByUser(user)
*/
/*

    fun getById(id: Long?): ProjectUser =
        id?.let { projectRepository.findProjectUserByProjectUser_id(it) } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "No User with this Id found!")
*//*


    */
/*fun update(email: String, user: User): User {
        val dbUser = this.getByEmail(email);
        user.id = dbUser.id;
        return projectRepository.save(user);
    }*//*


    */
/*fun remove(email: String) {
        val dbUser = this.getByEmail(email);
        dbUser.id?.let { projectRepository.deleteById(it) };
        return
    }*//*



}

*/

/*
package restapi.projectService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import restapi.model.ProjectUser
import restapi.model.User
import restapi.repository.ProjectUserRepository


@Service
class ProjectUserService(@Autowired val repository: ProjectUserRepository) {

    fun getAll(): MutableList<ProjectUser> = repository.findAll()

    fun getById(id: Long): ProjectUser = repository.findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    fun getByUser(user: User): Set<ProjectUser> = repository.findByUser(user)
*/
/*

    fun getById(id: Long?): ProjectUser =
        id?.let { repository.findProjectUserByProjectUser_id(it) } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "No User with this Id found!")
*//*


    */
/*fun update(email: String, user: User): User {
        val dbUser = this.getByEmail(email);
        user.id = dbUser.id;
        return repository.save(user);
    }*//*


    */
/*fun remove(email: String) {
        val dbUser = this.getByEmail(email);
        dbUser.id?.let { repository.deleteById(it) };
        return
    }*//*



}

*/

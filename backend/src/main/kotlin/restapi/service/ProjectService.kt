package restapi.service

import restapi.model.Project
import restapi.repository.UserRepository
import restapi.repository.ProjectRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

/**
 * @author  Simon Burmer
 * @date  02/12/22
 * @version 1.0
 */


@Service
class ProjectService(
    @Autowired val repository: ProjectRepository,
    @Autowired val userService: UserService
    ) {

    fun getAll(): MutableList<Project> = repository.findAll()

    // fun getAllByUser(userEmail: String): MutableList<Project> = repository.findByOwnerEmail(userEmail);

    fun getAllByName(id: String): MutableList<Project> = repository.findByNameContaining(id)

    fun getById(id: Long): Project = repository.findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND,"No project with this Id found!")

    fun create(userEmail: String, project: Project): Project {
        var user = userService.getByEmail(userEmail)
        // TODO comment out because owner does not exist atm
        /*project.owner = user
        project.attendees = mutableListOf(user)*/
        return repository.save(project)
    }

    fun remove(id: Long) {
        if (repository.existsById(id)) repository.deleteById(id)
        else throw ResponseStatusException(HttpStatus.NOT_FOUND, "No Project with this Id found!")
    }

    fun update(id: Long, project: Project): Project {
        return if (repository.existsById(id)) {
            project.id = id
            repository.save(project)
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND, "No Project with this Id found!")
    }

    fun attend(id: Long, userEmail: String): Project { // TODO: User should not be able to attend two times!
        var project = this.getById(id) // TODO: Maybe this code belongs to the controller?
        var user = userService.getByEmail(userEmail) // TODO: Maybe this code belongs to the controller?
        /*project.projectUser.add(user)*/ // TODO var user is ApiUser atm, projectUser is Set<ProjectUser>
        /*project.attendees?.add(user)*/
        repository.save(project)
        return project
    }
}
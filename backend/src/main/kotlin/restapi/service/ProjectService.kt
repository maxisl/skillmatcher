package restapi.service

import org.springdoc.core.converters.AdditionalModelsConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import restapi.model.Project
import restapi.model.ProjectUser
import restapi.repository.ProjectRepository
import restapi.repository.ProjectUserRepository

/**
 * @author  Simon Burmer
 * @date  02/12/22
 * @version 1.0
 */


@Service
class ProjectService(
    @Autowired val repository: ProjectRepository,
    @Autowired val projectUserRepository: ProjectUserRepository,
    @Autowired val userService: UserService,
    @Autowired val projectUserService: ProjectUserService,
    private val additionalModelsConverter: AdditionalModelsConverter
) {

    fun getAll(): MutableList<Project> = repository.findAll()

    // fun getAllByUser(userEmail: String): MutableList<Project> = repository.findByOwnerEmail(userEmail);

    fun getAllByName(id: String): MutableList<Project> = repository.findByNameContaining(id)

    fun getById(id: Long): Project = repository.findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND,"No project with this Id found!")

    fun create(userEmail: String, project: Project): Project {
        // TODO https://stackoverflow.com/questions/1795649/jpa-persisting-a-one-to-many-relationship
        val user = userService.getByEmail(userEmail)

        // additionally save relationship
        val projectUser = ProjectUser()
        projectUser.user = user
        projectUser.project = project

        project.projectUser.add(projectUser)

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
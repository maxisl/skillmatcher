package restapi.service

import org.springdoc.core.converters.AdditionalModelsConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import restapi.model.Project
import restapi.repository.ProjectRepository
import restapi.repository.UserRepository

/**
 * @author  Simon Burmer
 * @date  02/12/22
 * @version 1.0
 */


@Service
class ProjectService(
    @Autowired val repository: ProjectRepository,
    @Autowired val userRepository: UserRepository,
    @Autowired val userService: UserService,
    private val additionalModelsConverter: AdditionalModelsConverter
) {

    fun getAll(): MutableList<Project> = repository.findAll()

    // fun getAllByUser(userEmail: String): MutableList<Project> = repository.findByOwnerEmail(userEmail);

    fun getAllByName(id: String): MutableList<Project> = repository.findByNameContaining(id)

    fun getById(id: Long): Project = repository.findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND,"No project with this Id found!")

    fun create(userEmail: String, name: String, description: String, maxAttendees: String): Project {
        // TODO https://stackoverflow.com/questions/1795649/jpa-persisting-a-one-to-many-relationship

        // first create empty project to generate id
        // then populate project with parameters from request
        // then save current user to project <users> set
        // then store completed project

        val project = Project(
            id = null,
            name = name,
            description = description,
            maxAttendees = maxAttendees,
            attendees = mutableListOf(),
            requiredSkills = mutableListOf()
        )
        val user = userRepository.findUserByEmail(userEmail)
        if(user != null) {
            user.projects += project
            project.attendees += user
            repository.save(project)
            userRepository.save(user)
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "No User with this Email found!")
        }
        return project
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
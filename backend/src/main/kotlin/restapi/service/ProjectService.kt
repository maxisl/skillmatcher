package restapi.service

import org.springdoc.core.converters.AdditionalModelsConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import org.webjars.NotFoundException
import restapi.model.Project
import restapi.model.ProjectAttendeesDTO
import restapi.model.ProjectRequest
import restapi.model.User
import restapi.repository.ProjectRepository
import restapi.repository.UserRepository

/**
 * @author  Simon Burmer
 * @date  02/12/22
 * @version 1.0
 */


@Service
class ProjectService(
    @Autowired val projectRepository: ProjectRepository,
    @Autowired val userRepository: UserRepository,
    @Autowired val userService: UserService,
    private val additionalModelsConverter: AdditionalModelsConverter
) {

    fun getAll(): MutableList<Project> {
        val projects = projectRepository.findAll()
        projects.forEach {
            it.attendees.size
        }
        return projects
    }

    fun getAllByName(id: String): MutableList<Project> = projectRepository.findByNameContaining(id)

    // fun getAllByUser(userEmail: String): MutableList<Project> = userRepository.findByEmail(userEmail);

    fun getById(id: Long): Project =
        projectRepository.findByIdOrNull(id) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "No project with this Id found!"
        )

    fun getAttendeesById(id: Long): List<User> {
        return projectRepository.findProjectAttendeesById(id)
    }

    fun create(projectRequest: ProjectRequest): Project {
        val project = Project(
            id = null,
            name = projectRequest.name,
            description = projectRequest.description,
            maxAttendees = projectRequest.maxAttendees,
            attendees = mutableListOf()
        )
        return projectRepository.save(project)
    }

    fun remove(id: Long) {
        if (projectRepository.existsById(id)) projectRepository.deleteById(id)
        else throw ResponseStatusException(HttpStatus.NOT_FOUND, "No Project with this Id found!")
    }

    fun update(id: Long, project: Project): Project {
        return if (projectRepository.existsById(id)) {
            project.id = id
            projectRepository.save(project)
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND, "No Project with this Id found!")
    }

    fun attendProject(userId: Long, projectId: Long) {
        val user =
            userRepository.findById(userId)
                .orElseThrow { NotFoundException("User not found") }
        val project =
            projectRepository.findById(projectId)
                .orElseThrow { NotFoundException("Project not found") }
        project.attendees.add(user)
        user.projects.add(project)
        projectRepository.save(project)
        userRepository.save(user)
    }
}
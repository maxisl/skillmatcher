package restapi.service

<<<<<<< HEAD
import org.springdoc.core.converters.AdditionalModelsConverter
=======
>>>>>>> new-db-schema
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
<<<<<<< HEAD
import restapi.model.Project
import restapi.repository.ProjectRepository
import restapi.repository.ProjectUserRepository
import restapi.repository.UserRepository

/**
 * @author  Simon Burmer
 * @date  02/12/22
 * @version 1.0
 */
=======
import org.webjars.NotFoundException
import restapi.model.*
import restapi.repository.ProjectRepository
import restapi.repository.SkillRepository
import restapi.repository.UserRepository
>>>>>>> new-db-schema


@Service
class ProjectService(
<<<<<<< HEAD
    @Autowired val repository: ProjectRepository,
    @Autowired val userRepository: UserRepository,
    @Autowired val projectUserRepository: ProjectUserRepository,
    @Autowired val userService: UserService,
    @Autowired val projectUserService: ProjectUserService,
    private val additionalModelsConverter: AdditionalModelsConverter
=======
    @Autowired val projectRepository: ProjectRepository,
    @Autowired val userRepository: UserRepository,
    private val skillRepository: SkillRepository,
>>>>>>> new-db-schema
) {

    fun getAll(): MutableList<Project> {
        val projects = projectRepository.findAll()
        projects.forEach {
            it.attendees.size
        }
        return projects
    }

<<<<<<< HEAD
    // fun getAllByUser(userEmail: String): MutableList<Project> = repository.findByOwnerEmail(userEmail);
=======
    fun getAllByName(id: String): MutableList<Project> = projectRepository.findByNameContaining(id)
>>>>>>> new-db-schema

    // fun getAllByUser(userEmail: String): MutableList<Project> = userRepository.findByEmail(userEmail);

    fun getById(id: Long): Project =
        projectRepository.findByIdOrNull(id) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "No project with this Id found!"
        )

<<<<<<< HEAD
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
            users = mutableListOf(),
            projectSkill = mutableListOf()
        )
        val user = userRepository.findUserByEmail(userEmail)
        if(user != null) {
            user.projects += project
            project.users.add(user)
            repository.save(project)
            userRepository.save(user)
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "No User with this Email found!")
        }
        return project
=======
    fun getAttendeesById(id: Long): List<User> {
        return projectRepository.findProjectAttendeesById(id)
    }

    fun create(projectRequest: ProjectRequest): Project {
        val project = Project(
            id = null,
            name = projectRequest.name,
            description = projectRequest.description,
            maxAttendees = projectRequest.maxAttendees,
            attendees = mutableListOf(),
            startDate = projectRequest.startDate,
            endDate = projectRequest.endDate,
            imageLink = projectRequest.imageLink
        )
        return projectRepository.save(project)
>>>>>>> new-db-schema
    }

    fun remove(id: Long) {
        if (projectRepository.existsById(id)) projectRepository.deleteById(id)
        else throw ResponseStatusException(HttpStatus.NOT_FOUND, "No Project with this Id found!")
    }

    // TODO check if works
    fun update(id: Long, project: Project): Project {
        return if (projectRepository.existsById(id)) {
            project.id = id
            projectRepository.save(project)
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND, "No Project with this Id found!")
    }

<<<<<<< HEAD
    fun attend(id: Long, userEmail: String): Project { // TODO: User should not be able to attend two times!
        var project = this.getById(id) // TODO: Maybe this code belongs to the controller?
        var user = userService.getByEmail(userEmail) // TODO: Maybe this code belongs to the controller?
        /*project.projectUser.add(user)*/ // TODO var user is ApiUser atm, projectUser is Set<ProjectUser>
        /*project.attendees?.add(user)*/
        repository.save(project)
        return project
=======
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

    fun addRequiredSkillsToProject(projectId: Long, skillIds: List<Long>) {
        val project = projectRepository.findById(projectId)
        val skills = skillRepository.findAllById(skillIds)
        project.ifPresent {
            it.requiredSkills.addAll(skills)
            projectRepository.save(it)
        }
    }

    fun getRequiredSkills(project: Project) : List<SkillDTO> {
        return project.requiredSkills.map { skill -> SkillDTO(skill.id!!, skill.name) }
>>>>>>> new-db-schema
    }
}
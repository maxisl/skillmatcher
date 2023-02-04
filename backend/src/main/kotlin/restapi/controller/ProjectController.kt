package restapi.controller

import com.fasterxml.jackson.annotation.JsonView
import restapi.model.Project
import restapi.service.ProjectService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import restapi.jsonView.DataView
import restapi.model.ProjectRequest
import restapi.model.ProjectUpdateDto
import restapi.model.SkillDTO
import restapi.repository.ProjectRepository
import restapi.repository.UserRepository
import restapi.service.UserService
import java.util.*
import javax.validation.Valid


@RequestMapping("projects")
@RestController
class ProjectController(
    val projectService: ProjectService,
    val projectRepository: ProjectRepository,
    val userRepository: UserRepository,
    private val userService: UserService
) {

/*
********************************** GET **********************************
*/
    @JsonView(DataView.Project::class)
    @GetMapping
    fun getAllProjects() = projectService.getAll()

    @JsonView(DataView.Project::class)
    @GetMapping("/{id}")
    fun getProject(@PathVariable id: Long) = projectService.getById(id)

    @JsonView(DataView.Project::class)
    @GetMapping("/byUserEmail/{userEmail}")
    fun getAllProjectsByUserEmail(@PathVariable userEmail: String): ResponseEntity<List<Project>> {
        val userId = userService.getByEmail(userEmail).id
        val projects = userId?.let { userService.getUserProjects(it) }
        return if (projects != null) {
            ResponseEntity.ok(projects)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @JsonView(DataView.User::class)
    @GetMapping("/attendees/{projectId}")
    fun getAttendeesById(@PathVariable projectId: Long) = projectService.getAttendeesById(projectId)

    @GetMapping("/{id}/requiredSkills")
    fun getRequiredSkills(@PathVariable id: Long): ResponseEntity<List<SkillDTO>> {
        val project = projectService.getById(id)
        val requiredSkills = projectService.getRequiredSkills(project)
        return ResponseEntity.ok(requiredSkills)
    }

/*
********************************** POST **********************************
*/

    @JsonView(DataView.Project::class)
    @PostMapping("/{userEmail}")
    @ResponseStatus(HttpStatus.CREATED)
    fun createProject(
        @PathVariable userEmail: String,
        @Valid @RequestBody projectRequest: ProjectRequest
    ): ResponseEntity<Project> {
        val user = userRepository.findUserByEmail(userEmail)
        val project = projectService.create(projectRequest)
        if (user != null) {
            project.attendees.add(user)
            user.projects.add(project)
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        }
        return ResponseEntity.ok(projectRepository.save(project))
    }

    @JsonView(DataView.Project::class)
    @PostMapping("/{projectId}/attendees/{userEmail}")
    fun attendProject(@PathVariable userEmail: String, @PathVariable projectId: Long) {
        val user = userRepository.findUserByEmail(userEmail)
        val userId = user?.id
        if (userId != null) {
            val project = projectRepository.findById(projectId)
            if (project.isPresent) {
                val requiredSkills = project.get().requiredSkills
                val userSkills = user.skills
                if (userSkills.any { skill -> requiredSkills.contains(skill) }) {
                    projectService.attendProject(userId, projectId)
                } else {
                    throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not have required skills")
                }
            } else {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found")
            }
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        }
    }


    @PostMapping("/{id}/requiredSkills")
    fun addRequiredSkillsToProject(@PathVariable id: Long, @RequestBody skillIds: List<Long>) {
        if (skillIds == null || skillIds.isEmpty()) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "skillIds cannot be null or empty"
            )
        }
        projectService.addRequiredSkillsToProject(id, skillIds)
    }

/*
********************************** PUT **********************************
*/

    @JsonView(DataView.Project::class)
    @PutMapping("/{id}")
    fun updateProject(
        @PathVariable id: Long,
        @RequestBody projectUpdateDto: ProjectUpdateDto
    ): ResponseEntity<Project> {
        return projectService.updateProject(id, projectUpdateDto)
    }

/*
********************************** DELETE **********************************
*/

    // DEACTIVATED - only enable automatic deletion when everyone has left the project
    /*@DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProject(@PathVariable id: Long): ResponseEntity<String> {
        projectService.remove(id)
        return ResponseEntity.ok("Project with id $id successfully deleted!")
    }*/

    @JsonView(DataView.Project::class)
    @DeleteMapping("/{projectId}/attendees/{userEmail}")
    fun leaveProject(@PathVariable userEmail: String, @PathVariable projectId: Long) {
        val user = userRepository.findUserByEmail(userEmail)
        val userId = user?.id
        if (userId != null) {
            projectService.leaveProject(userId, projectId)
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        }
    }
}
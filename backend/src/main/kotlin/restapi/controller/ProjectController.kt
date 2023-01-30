package restapi.controller

import com.fasterxml.jackson.annotation.JsonView
import restapi.model.Project
import restapi.service.ProjectService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import org.webjars.NotFoundException
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
    @JsonView(DataView.ProjectWithOwner::class)
    @GetMapping
    fun getAllProjects() = projectService.getAll()

    @JsonView(DataView.ProjectWithAttendeesAndOwner::class)
    @GetMapping("/{id}")
    fun getProject(@PathVariable id: Long) = projectService.getById(id)

    @JsonView(DataView.ProjectWithAttendeesAndOwner::class)
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

    // LEGACY - DOES NOT MAKE SENSE? NAME IS NOT UNIQUE
    /*    @JsonView(DataView.Project::class)
        @GetMapping("/byName/{name}")
        fun findByNameContaining(@PathVariable name: String): ResponseEntity<List<Project>> {
            return ResponseEntity.ok(projectService.getAllByName(name))
        }*/

    @JsonView(DataView.ProjectWithAttendeesAndOwner::class)
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

    // TODO adapt JSON View? old interface
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
    @PostMapping("/{projectId}/attendees/{userId}")
    fun attendProject(@PathVariable userId: Long, @PathVariable projectId: Long) {
        projectService.attendProject(userId, projectId)
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

    // TODO deactivate? only enable automatic deletion when everyone has left the project?
    /*@DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProject(@PathVariable id: Long): ResponseEntity<String> {
        projectService.remove(id)
        return ResponseEntity.ok("Project with id $id successfully deleted!")
    }*/

    @JsonView(DataView.Project::class)
    @DeleteMapping("/{projectId}/attendees/{userId}")
    fun leaveProject(@PathVariable userId: Long, @PathVariable projectId: Long) {
        projectService.leaveProject(userId, projectId)
    }

    // helper function
    fun base64ToByteArray(base64: String): ByteArray {
        return Base64.getDecoder().decode(base64)
    }

    fun fromBase64(base64: String): ByteArray {
        return Base64.getDecoder().decode(base64)
    }



}
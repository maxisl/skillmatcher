package restapi.controller

import com.fasterxml.jackson.annotation.JsonView
import restapi.model.Project
import restapi.service.ProjectService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import restapi.jsonView.DataView
import restapi.model.ProjectRequest
import restapi.repository.ProjectRepository
import restapi.repository.UserRepository
import java.security.Principal


@RequestMapping("projects")
@RestController
class ProjectController(
    val projectService: ProjectService,
    val projectRepository: ProjectRepository,
    val userRepository: UserRepository
) {

    @JsonView(DataView.ProjectWithOwner::class)
    @GetMapping
    fun getAllProjects() = projectService.getAll()

    @JsonView(DataView.ProjectWithAttendeesAndOwner::class)
    @GetMapping("/{id}")
    fun getProject(@PathVariable id: Long) = projectService.getById(id)

    /*@JsonView(DataView.ProjectWithAttendeesAndOwner::class)
    @GetMapping("/byUserEmail/{userEmail}")
    fun getAllProjectsByUserEmail(@PathVariable userEmail: String) = projectService.getAllByUser(userEmail)
    */
    @JsonView(DataView.ProjectWithAttendeesAndOwner::class)
    @GetMapping("/byName/{name}")
    fun findByNameContaining(@PathVariable name: String) = projectService.getAllByName(name)

    @JsonView(DataView.ProjectWithAttendeesAndOwner::class)
    @GetMapping("/attendees/{projectId}")
    fun getAttendeesById(@PathVariable projectId: Long) = projectService.getAttendeesById(projectId)

    @JsonView(DataView.ProjectWithAttendeesAndOwner::class)
    @PostMapping("/{userEmail}")
    @ResponseStatus(HttpStatus.CREATED)
    fun createProject(
        @PathVariable userEmail: String,
        @RequestBody projectRequest: ProjectRequest
    ): ResponseEntity<Project> {
        val user = userRepository.findUserByEmail(userEmail)
        val project = projectService.create(projectRequest)
        if (user != null) {
            project.attendees.add(user)
            user.projects.add(project)
        }
        return ResponseEntity.ok(projectRepository.save(project))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProject(@PathVariable id: Long): ResponseEntity<String> {
        projectService.remove(id)
        return ResponseEntity.ok("Project successfully deleted!")
    }

    @JsonView(DataView.ProjectWithAttendeesAndOwner::class)
    @PutMapping("/{id}") // TODO:Not Working Jet / Only owner should can alter project!
    fun updateProject(@PathVariable id: Long, @RequestBody project: Project) =
        projectService.update(id, project)

    @JsonView(DataView.ProjectWithAttendeesAndOwner::class)
    @PutMapping("/attend/{id}")
    fun attendProject(@PathVariable id: Long, principal: Principal) =
        projectService.attend(id, principal.getName())
}
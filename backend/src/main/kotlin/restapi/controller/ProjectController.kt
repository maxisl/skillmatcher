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
/*
********************************** GET **********************************
*/
    @JsonView(DataView.ProjectWithOwner::class)
    @GetMapping
    fun getAllProjects() = projectService.getAll()

    @JsonView(DataView.ProjectWithAttendeesAndOwner::class)
    @GetMapping("/{id}")
    fun getProject(@PathVariable id: Long) = projectService.getById(id)

    // TODO getAllProjectsByUserEmail
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

/*
********************************** POST **********************************
 */

    // TODO adapt JSON View? old interface
    @JsonView(DataView.ProjectWithOwner::class)
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

    @PostMapping("/{projectId}/attendees/{userId}")
    fun attendProject(@PathVariable userId: Long, @PathVariable projectId: Long) {
        projectService.attendProject(userId, projectId)
    }

    @PostMapping("/{id}/requiredSkills")
    fun addRequiredSkillsToProject(@PathVariable id: Long, @RequestBody skillIds: List<Long>) {
        projectService.addRequiredSkillsToProject(id, skillIds)
    }

/*
********************************** PUT **********************************
*/

    @JsonView(DataView.ProjectWithAttendeesAndOwner::class)
    @PutMapping("/{id}") // TODO:Not Working Jet / Only owner should can alter project!
    fun updateProject(@PathVariable id: Long, @RequestBody project: Project) =
        projectService.update(id, project)

/*
********************************** DELETE **********************************
*/

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProject(@PathVariable id: Long): ResponseEntity<String> {
        projectService.remove(id)
        return ResponseEntity.ok("Project successfully deleted!")
    }

}
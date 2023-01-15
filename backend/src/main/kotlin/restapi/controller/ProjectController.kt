package restapi.controller

import com.fasterxml.jackson.annotation.JsonView
import restapi.model.Project
import restapi.service.ProjectService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import restapi.jsonView.DataView
import java.security.Principal
import javax.validation.Valid


@RequestMapping("projects")
@RestController
class ProjectController(val service: ProjectService) {

    @JsonView(DataView.ProjectWithOwner::class)
    @GetMapping
    fun getAllProjects() = service.getAll()

    @JsonView(DataView.ProjectWithAttendeesAndOwner::class)
    @GetMapping("/{id}")
    fun getProject(@PathVariable id: Long) = service.getById(id)

    /*@JsonView(DataView.ProjectWithAttendeesAndOwner::class)
    @GetMapping("/byUserEmail/{userEmail}")
    fun getAllProjectsFromUserEmail(@PathVariable userEmail: String) = service.getAllByUser(userEmail)
*/
    @JsonView(DataView.ProjectWithAttendeesAndOwner::class)
    @GetMapping("/byName/{name}")
    fun findByNameContaining(@PathVariable name: String) = service.getAllByName(name)

    @JsonView(DataView.ProjectWithAttendeesAndOwner::class)
    @PostMapping("/{userEmail}")
    @ResponseStatus(HttpStatus.CREATED)
    fun createProject(@PathVariable userEmail: String,@Valid @RequestBody project: Project): Project = service.create(userEmail,project)

    @DeleteMapping("/{id}") // TODO: Only owner can delete projects
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProject(@PathVariable id: Long): ResponseEntity<String> {
        service.remove(id)
        return ResponseEntity.ok("Project successfully deleted!")
    }

    @JsonView(DataView.ProjectWithAttendeesAndOwner::class)
    @PutMapping("/{id}") // TODO:Not Working Jet / Only owner should can alter project!
    fun updateProject(@PathVariable id: Long, @RequestBody project: Project) = service.update(id, project)

    @JsonView(DataView.ProjectWithAttendeesAndOwner::class)
    @PutMapping("/attend/{id}")
    fun attendProject(@PathVariable id: Long, principal: Principal) = service.attend(id,principal.getName())
}
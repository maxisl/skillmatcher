package restapi.controller

import restapi.model.Project
import restapi.service.ProjectService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RequestMapping("api/v1/projects")
@RestController
class ProjectController(val service: ProjectService) {

    @GetMapping
    fun getAllProjects() = service.getAll()

    @GetMapping("/by/{userId}")
    fun getAllProjectsFromUser(@PathVariable userId: Long) = service.getAllByUser(userId)

    @GetMapping("/by2/{name}")
    fun findByNameContaining(@PathVariable name: String) = service.getAllByName(name)

    @GetMapping("/{id}")
    fun getProject(@PathVariable id: Long) = service.getById(id)

    @PostMapping("/by/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveProject(@PathVariable userId: Long,@RequestBody project: Project): Project = service.create(userId,project)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProject(@PathVariable id: Long) = service.remove(id)

    @PutMapping("/{id}")
    fun updateProject(@PathVariable id: Long, @RequestBody project: Project) = service.update(id, project)
}
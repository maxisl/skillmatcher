package restapi.controller

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import restapi.jsonView.DataView
import restapi.model.Project
import restapi.model.Skill
import restapi.model.User
import restapi.service.SkillService
import restapi.service.UserService
import java.security.Principal
import javax.validation.Valid


@RequestMapping("skill")
@RestController
class SkillController(val service: SkillService) {

    @JsonView(DataView.Skill::class)
    @GetMapping
    fun getAllSkills() = service.getAll()

    @JsonView(DataView.UserWithSkill::class)
    @GetMapping("/{id}")
    fun getSkill(@PathVariable id: Long, principal: Principal) = service.getById(id)

    @JsonView(DataView.UserWithSkill::class)
    @GetMapping("/byName/{name}")
    fun getSkillByName(@PathVariable name: String, principal: Principal) = service.getByName(name)

    @JsonView(DataView.Skill::class)
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    // use Map to only extract name value and not pass whole JSON object to create()
    fun createSkill(@RequestBody requestBody: Map<String, String>, principal: Principal) =
        requestBody["name"]?.let { service.create(it) }

    @PutMapping("/{id}")
    fun updateSkill(@PathVariable id: Long, @RequestBody requestBody: Map<String, String>): ResponseEntity<String> {
        val name = requestBody["name"].toString()
        return service.update(id, name)
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSkill(@PathVariable id: Long) {
        service.remove(id)
    }
}



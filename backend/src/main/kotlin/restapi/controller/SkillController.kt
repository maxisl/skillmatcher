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

    @JsonView(DataView.Skill::class)
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun createSkill(@Valid @RequestBody name: String) = service.create(name)
    //fun createSkill(@PathVariable skillname: String,@Valid @RequestBody skillname: String): Project = service.create(userEmail,project)

    /* TODO update
    @JsonView(DataView.User::class)
    @PutMapping("/{email}")
    fun updateUser(@PathVariable email: String, @RequestBody user: User) = service.update(email, user)
    */

    /* TODO delete
    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable email: String): ResponseEntity<String> {
        service.remove(email)
        return ResponseEntity.ok("User successfully deleted!")
    }*/
}



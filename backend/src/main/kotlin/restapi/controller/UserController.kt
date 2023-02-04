package restapi.controller

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import restapi.jsonView.DataView
import restapi.model.SkillDTO
import restapi.service.UserService
import java.security.Principal


@RequestMapping("user")
@RestController
class UserController(
    val userService: UserService,
) {

/*
********************************** GET **********************************
*/

    @JsonView(DataView.User::class)
    @GetMapping
    fun getAllUsers() = userService.getAll()

    @JsonView(DataView.UserWithProjects::class)
    @GetMapping("/byMail/{email}")
    fun getUser(@PathVariable email: String, principal: Principal) = userService.getByEmail(email)

    @JsonView(DataView.UserWithProjects::class)
    @GetMapping("/byId/{id}")
    fun getUserById(@PathVariable id: Long, principal: Principal) = userService.getById(id)

    @GetMapping("/{email}/skills")
    fun getUserSkills(@PathVariable email: String): List<SkillDTO> {
        val userSkills = userService.getUserSkillsByEmail(email)
        return userSkills.map { SkillDTO(it.id, it.name) }
    }

    @GetMapping("/skill/{skillId}")
    fun getUsersBySkillId(@PathVariable skillId: Long) = userService.getUsersBySkillId(skillId)

/*
********************************** POST **********************************
*/

    @PostMapping("/{email}/skill")
    fun addSkill(@PathVariable email: String, @RequestBody skillIds: List<Long>) =
        userService.addSkill(email, skillIds)

/*
********************************** DELETE **********************************
*/

    // DEACTIVATED - not possible to delete users in production
    @DeleteMapping("/byMail/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable email: String): ResponseEntity<String> {
        userService.remove(email)
        return ResponseEntity.ok("User successfully deleted!")
    }
}



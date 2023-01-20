package restapi.controller

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import restapi.jsonView.DataView
<<<<<<< HEAD
=======
import restapi.model.Skill
import restapi.model.SkillDTO
>>>>>>> new-db-schema
import restapi.model.User
import restapi.service.UserService
import java.security.Principal


@RequestMapping("user")
@RestController
class UserController(val userService: UserService) {
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

/*
********************************** PUT **********************************
 */
    // TODO: Not working yet
    @JsonView(DataView.User::class)
<<<<<<< HEAD
    @PutMapping("/{email}")
    fun updateUser(@PathVariable email: String, @RequestBody user: User) = service.update(email, user)
=======
    @PutMapping("/byMail/{email}")
    fun updateUser(@PathVariable email: String, @RequestBody user: User) =
        userService.update(email, user)
>>>>>>> new-db-schema

/*
********************************** POST **********************************
*/

    @PostMapping("/{email}/skill/{skillId}")
    fun addSkill(@PathVariable email: String, @PathVariable skillId: Long) =
        userService.addSkill(email, skillId)

/*
********************************** DELETE **********************************
 */
    @DeleteMapping("/byMail/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable email: String): ResponseEntity<String> {
        userService.remove(email)
        return ResponseEntity.ok("User successfully deleted!")
    }
}

// TODO can be deleted after testing: test route that is unauthorized - does not require JWT
@RequestMapping("excluded")
@RestController
class UserTestController(val service: UserService) {
    @JsonView(DataView.User::class)
    @GetMapping()
    fun getUsers() = service.getAll()
}


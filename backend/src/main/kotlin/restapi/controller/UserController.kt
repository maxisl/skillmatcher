package restapi.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import restapi.model.ApiUser
import restapi.service.UserService
import java.security.Principal


@RequestMapping("user")
@RestController
class UserController(val service: UserService) {

    @GetMapping
    fun getAllUsers() = service.getAll()

    @GetMapping("/{email}")
    fun getUser(@PathVariable email: String, principal: Principal): Any { //TODO: use: https://stackoverflow.com/questions/51712724/how-to-allow-a-user-only-access-their-own-data-in-spring-boot-spring-security
        if (email != principal.getName()) {
            return ResponseEntity.badRequest().body("Error SImon");
        }

        return service.getByEmail(email)
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody user: ApiUser) = service.update(id, user)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable id: Long) = service.remove(id)


    // TODO: get all users of project


    // Test
    // https://docs.oracle.com/javase/7/docs/api/java/security/Principal.html
    @GetMapping("/username")
    @ResponseBody
    fun currentUserName(principal: Principal): String? {
        return principal.getName();
    }
}


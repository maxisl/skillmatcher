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

    // TODO: Zukunft: {email} wird nicht gebraucht, es ist auch nur mit /get, /update, /delete sicher!

    @GetMapping
    fun getAllUsers() = service.getAll()

    @GetMapping("/{email}")
    fun getUser(@PathVariable email: String, principal: Principal) = service.getByEmail(email)

    @PutMapping("/{email}") // TODO: Not working yet
    fun updateUser(@PathVariable email: String, @RequestBody user: ApiUser) = service.update(email, user)

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable email: String): ResponseEntity<String> {
        service.remove(email)
        return ResponseEntity.ok("User successfully deleted!")
    }
}


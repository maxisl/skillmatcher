package restapi.controller

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import restapi.jsonView.DataView
import restapi.model.ApiUser
import restapi.service.UserService
import java.security.Principal


@RequestMapping("user")
@RestController
class UserController(val service: UserService) {

    // TODO: Zukunft: {email} wird nicht gebraucht, es ist auch nur mit /get, /update, /delete sicher!

    @JsonView(DataView.User::class)
    @PreAuthorize("")
    @GetMapping
    fun getAllUsers() = service.getAll()

    @JsonView(DataView.UserWithProjects::class)
    @GetMapping("/{email}")
    fun getUser(@PathVariable email: String, principal: Principal) = service.getByEmail(email)

    @JsonView(DataView.User::class)
    @PutMapping("/{email}") // TODO: Not working yet
    fun updateUser(@PathVariable email: String, @RequestBody user: ApiUser) = service.update(email, user)

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable email: String): ResponseEntity<String> {
        service.remove(email)
        return ResponseEntity.ok("User successfully deleted!")
    }

    /*@PostMapping("/")
    // @RequestBody to be able to convert JSON (sent as HTTP body) into object
    fun post(@RequestBody message: Message) {
        fun updateUser(@PathVariable email: String, @RequestBody user: ApiUser) = service.update(email, user)
    }*/
}


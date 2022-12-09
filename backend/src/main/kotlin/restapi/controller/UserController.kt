package restapi.controller

import restapi.model.ApiUser
import restapi.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RequestMapping("api/v1/users")
@RestController
class UserController(val service: UserService) {

    @GetMapping
    fun getAllUsers() = service.getAll()

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long) = service.getById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun saveUser(@RequestBody apiUser: ApiUser): ApiUser = service.create(apiUser)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable id: Long) = service.remove(id)

}


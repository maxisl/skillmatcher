package restapi.service

import restapi.model.User
import restapi.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(val repository: UserRepository) {

    fun getAll(): List<User> = repository.findAll()

    fun getById(id: Long): User = repository.findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    fun create(user: User): User = repository.save(user)

    fun remove(id: Long) {
        if (repository.existsById(id)) repository.deleteById(id)
        else throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    fun update(id: Long, user: User): User {
        return if (repository.existsById(id)) {
            user.id = id
            repository.save(user)
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

}

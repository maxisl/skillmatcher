package restapi.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import restapi.model.ApiUser
import restapi.repository.UserRepository
import java.util.*


@Service
class UserService(val repository: UserRepository) {

    fun getAll(): List<ApiUser> = repository.findAll()

    // fun getById(id: Long): ApiUser = repository.findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    fun getByEmail(email: String): Optional<ApiUser> = repository.findUserByEmail(email) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)


    fun update(id: Long, user: ApiUser): ApiUser {
        return if (repository.existsById(id)) {
            user.id = id
            repository.save(user)
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    fun remove(id: Long) {
        if (repository.existsById(id)) repository.deleteById(id)
        else throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

}


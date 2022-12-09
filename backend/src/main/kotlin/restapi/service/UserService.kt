package restapi.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import restapi.model.ApiUser
import restapi.repository.UserRepository


@Service
class UserService(val repository: UserRepository) {

    fun getAll(): List<ApiUser> = repository.findAll()

    fun getById(id: Long): ApiUser = repository.findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    fun create(apiUser: ApiUser): ApiUser = repository.save(apiUser)

    fun remove(id: Long) {
        if (repository.existsById(id)) repository.deleteById(id)
        else throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    }


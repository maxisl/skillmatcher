package restapi.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import restapi.model.User
import restapi.repository.UserRepository


@Service
class UserService(val repository: UserRepository) {

    fun getAll(): List<User> = repository.findAll()

    // fun getById(id: Long): ApiUser = repository.findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    fun getByEmail(email: String): User =
        repository.findUserByEmail(email) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "No User with this Email found!")

    fun update(email: String, user: User): User {
        val dbUser = this.getByEmail(email);
        user.id = dbUser.id;
        return repository.save(user);
    }

    fun remove(email: String) {
        val dbUser = this.getByEmail(email);
        dbUser.id?.let { repository.deleteById(it) };
        return
    }


}


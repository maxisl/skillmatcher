package restapi.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import restapi.model.User

@Repository
interface UserRepository : JpaRepository<User, Long>{
    fun findUserByEmail(email: String): User?
}
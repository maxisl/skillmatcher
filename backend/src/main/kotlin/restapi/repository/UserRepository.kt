package restapi.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import restapi.model.ApiUser
import java.util.*

@Repository
interface UserRepository : JpaRepository<ApiUser, Long>{
    fun findUserByEmail(email: String): ApiUser?
}
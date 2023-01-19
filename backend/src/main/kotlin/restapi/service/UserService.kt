package restapi.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import org.webjars.NotFoundException
import restapi.model.User
import restapi.repository.ProjectRepository
import restapi.repository.SkillRepository
import restapi.repository.UserRepository


@Service
class UserService(
    val userRepository: UserRepository,
    private val projectRepository: ProjectRepository,
    private val skillRepository: SkillRepository
) {

    fun getAll(): List<User> = userRepository.findAll()

    fun getById(id: Long): User = userRepository.findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    fun getByEmail(email: String): User =
        userRepository.findUserByEmail(email) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "No User with this Email found!")

    fun update(email: String, user: User): User {
        val dbUser = this.getByEmail(email);
        user.id = dbUser.id;
        return userRepository.save(user);
    }

    fun remove(email: String) {
        val dbUser = this.getByEmail(email);
        dbUser.id.let { userRepository.deleteById(it) };
        return
    }

    fun addSkill(userId: Long, skillId: Long) {
        val user = userRepository.findById(userId).orElseThrow { NotFoundException("User not found") }
        val skill = skillRepository.findById(skillId).orElseThrow { NotFoundException("Skill not found") }
        user.skills.add(skill)
        skill.usersWithSkill.add(user)
        userRepository.save(user)
        skillRepository.save(skill)
    }


}


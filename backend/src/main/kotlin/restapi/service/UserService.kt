package restapi.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import restapi.model.Project
import restapi.model.Skill
import restapi.model.User
import restapi.repository.SkillRepository
import restapi.repository.UserRepository


@Service
class UserService(
    val userRepository: UserRepository,
    private val skillRepository: SkillRepository
) {

    fun getAll(): List<User> = userRepository.findAll()

    fun getById(id: Long): User =
        userRepository.findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    fun getByEmail(email: String): User =
        userRepository.findUserByEmail(email) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "No User with this Email found!"
        )

    fun getUserSkillsByEmail(email: String): MutableList<Skill> {
        val user =
            userRepository.findUserByEmail(email)
                ?: throw ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found"
                )
        return user.skills
    }

    fun getUserProjects(userId: Long): MutableList<Project> {
        val user = userRepository.findById(userId)
        return user.map { it.projects }.orElse(mutableListOf())
    }

    fun remove(email: String) {
        val user = this.getByEmail(email);
        user.id.let {
            if (it != null) {
                userRepository.deleteById(it)
            }
        };
        return
    }

    fun addSkill(email: String, skillIds: List<Long>) {
        val user =
            userRepository.findUserByEmail(email)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        val newSkills = skillRepository.findAllById(skillIds)
        val existingSkills = user.skills.toMutableList()
        newSkills.forEach { skill ->
            if (!existingSkills.contains(skill)) {
                existingSkills.add(skill)
                skill.usersWithSkill.add(user)
            } else {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Skill already added")
            }
        }
        user.skills = existingSkills
        userRepository.save(user)
    }

    fun getUsersBySkillId(skillId: Long): List<User> {
        val skill = skillRepository.findByIdOrNull(skillId) ?: throw Exception("Skill not found")
        return skill.usersWithSkill
    }
}


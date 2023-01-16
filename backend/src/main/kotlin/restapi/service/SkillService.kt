package restapi.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import restapi.model.Skill
import restapi.model.User
import restapi.repository.SkillRepository


@Service
class SkillService(val repository: SkillRepository) {

    fun getAll(): MutableList<Skill> = repository.findAll()

    fun getById(id: Long) =
        repository.findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    fun getByName(name: String): Skill =
        repository.findSkillByName(name) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "No skill with this name found!")

    fun create(name: String)/*: ResponseEntity<Skill> */: Skill{
        val skillAvailable: Skill? =
            repository.findSkillByName(name)

        if (skillAvailable != null) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Skill with this name already exists!"
            )
        }

        val skill = Skill()
        skill.name = name
        println("Newly created Skill: $skill")

        return repository.save(skill)
    }

    /* TODO
    fun update(email: String, user: User): User {
        val dbUser = this.getByEmail(email);
        user.id = dbUser.id;
        return repository.save(user);
    }*/

    fun remove(id: Long) {
        val skill = this.getById(id);
        skill.skill_id?.let { repository.deleteById(it) };
        return
    }


}


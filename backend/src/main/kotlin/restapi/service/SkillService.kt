package restapi.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import restapi.model.Skill
import restapi.repository.SkillRepository


@Service
class SkillService(val repository: SkillRepository) {

    fun getAll(): MutableList<Skill> {
        val skills = repository.findAll()
        return skills
    }

    fun getById(id: Long): Skill =
        repository.findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    fun getByName(name: String): Skill =
        repository.findSkillByName(name) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "No skill with this name found!"
        )

    fun create(name: String): Skill {
        if (name.isBlank()) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Skill name cannot be null or blank"
            )
        }

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

        return repository.save(skill)
    }

    fun update(id: Long, name: String): ResponseEntity<String> {
        val skillAvailable: Skill? =
            repository.findSkillByName(name)

        if (skillAvailable != null) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Skill with this name already exists!"
            )
        }
        val skill = repository.findById(id)
        if (skill.isPresent) {
            skill.get().name = name
            repository.save(skill.get())
        } else {
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.ok("Skill $id updated successfully: New name: \"$name\"")
    }

    fun remove(id: Long): ResponseEntity<String> {
        val skillAvailable: Skill? =
            repository.findSkillById(id)

        if (skillAvailable != null) {
            val skill = this.getById(id);
            skill.id?.let { repository.deleteById(it) };
            return ResponseEntity.ok("Skill successfully deleted")
        }
        throw ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Skill with this id doesn't exist!"
        )
    }
}


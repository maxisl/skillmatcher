package restapi.controller

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import restapi.jsonView.DataView
import restapi.model.Skill
import restapi.service.SkillService
import java.security.Principal


@RequestMapping("skill")
@RestController
class SkillController(
    val skillService: SkillService
) {

/*
********************************** GET **********************************
*/

    @JsonView(DataView.Skill::class)
    @GetMapping
    fun getAllSkills() = skillService.getAll()

    @JsonView(DataView.UserWithSkill::class)
    @GetMapping("/{id}")
    fun getSkill(@PathVariable id: Long, principal: Principal): Skill = skillService.getById(id)

    @JsonView(DataView.UserWithSkill::class)
    @GetMapping("/byName/{name}")
    fun getSkillByName(@PathVariable name: String, principal: Principal) =
        skillService.getByName(name)


    /*
    ********************************** POST **********************************
         */

    @JsonView(DataView.Skill::class)
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun createSkill(@RequestBody requestBody: Map<String, String>, principal: Principal) {
        val name = requestBody["name"]
        if (name.isNullOrBlank()) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Attribute 'name' is required and cannot be blank or empty"
            )
        }
        skillService.create(name)
    }

/*
********************************** PUT **********************************
*/

    @PutMapping("/{id}")
    fun updateSkill(
        @PathVariable id: Long,
        @RequestBody requestBody: Map<String, String>
    ): ResponseEntity<String> {
        val name = requestBody["name"].toString()
        return skillService.update(id, name)
    }

/*
********************************** DELETE **********************************
*/

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSkill(@PathVariable id: Long): ResponseEntity<String> {
        return skillService.remove(id)
    }
}



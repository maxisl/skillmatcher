package restapi.repository

import restapi.model.Skill
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SkillRepository : JpaRepository<Skill, Long>{
    fun findSkillByName(name: String): Skill?
    fun findSkillById(id: Long): Skill?
}
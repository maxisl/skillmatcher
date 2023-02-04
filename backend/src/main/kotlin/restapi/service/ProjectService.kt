package restapi.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import restapi.model.*
import restapi.repository.ProjectRepository
import restapi.repository.SkillRepository
import restapi.repository.UserRepository
import java.util.*


@Service
class ProjectService(
    @Autowired val projectRepository: ProjectRepository,
    @Autowired val userRepository: UserRepository,
    private val skillRepository: SkillRepository,
) {

    fun getAll(): MutableList<Project> {
        val projects = projectRepository.findAll()
        projects.forEach {
            it.attendees.size
        }
        return projects
    }

    fun getById(id: Long): Project =
        projectRepository.findByIdOrNull(id) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "No project with this Id found!"
        )

    fun getAttendeesById(id: Long): List<User> {
        val project = projectRepository.findById(id)
        if (!project.isPresent) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No project with this Id found!"
            )
        } else {
            val attendees = projectRepository.findProjectAttendeesById(id)
            if (attendees.isNotEmpty()) {
                return attendees
            } else {
                throw ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No attendees for project with this Id found!"
                )
            }
        }
    }


    fun create(projectRequest: ProjectRequest): Project {
        val project = Project(
            id = null,
            name = projectRequest.name,
            description = projectRequest.description,
            maxAttendees = projectRequest.maxAttendees,
            attendees = mutableListOf(),
            startDate = projectRequest.startDate,
            endDate = projectRequest.endDate,
            image = projectRequest.image
        )
        val projectAvailable: Project? =
            projectRepository.findByName(projectRequest.name)
        if (projectAvailable != null) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Project with this name already exists!"
            )
        }
        val requiredSkillsIds = projectRequest.requiredSkillsIds
        if (requiredSkillsIds != null) {
            val skills = skillRepository.findAllById(requiredSkillsIds)
            skills.removeIf(Objects::isNull)
            project.requiredSkills.addAll(skills)
        }
        return projectRepository.save(project)
    }

    // DEACTIVATED - only delete projects by leaving
    /*fun remove(id: Long) {
        if (projectRepository.existsById(id)) projectRepository.deleteById(id)
        else throw ResponseStatusException(HttpStatus.NOT_FOUND, "No Project with this Id found!")
    }*/

    fun updateProject(id: Long, projectUpdateDto: ProjectUpdateDto): ResponseEntity<Project> {
        val project = projectRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found") }
        project.apply {
            name = projectUpdateDto.name ?: name
            description = projectUpdateDto.description ?: description
            maxAttendees = projectUpdateDto.maxAttendees ?: maxAttendees
            startDate = projectUpdateDto.startDate ?: startDate
            endDate = projectUpdateDto.endDate ?: endDate
        }
        return ResponseEntity.ok(projectRepository.save(project))
    }


    fun attendProject(userId: Long, projectId: Long) {
        val user =
            userRepository.findById(userId)
                .orElseThrow {
                    ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"
                    )
                }
        val project =
            projectRepository.findById(projectId)
                .orElseThrow {
                    ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Project not found"
                    )
                }
        if (project.attendees.contains(user)) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST, "User already attends this project"
            )
        } else {
            project.attendees.add(user)
            user.projects.add(project)
            projectRepository.save(project)
            userRepository.save(user)
        }
    }

    fun leaveProject(userId: Long, projectId: Long) {
        val user =
            userRepository.findById(userId)
                .orElseThrow {
                    ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"
                    )
                }
        val project = projectRepository.findById(projectId)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Project not found"
                )
            }
        if (!project.attendees.contains(user)) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not part of project"
            )
        }
        project.attendees.remove(user)
        user.projects.remove(project)
        if (project.attendees.size == 0) {
            projectRepository.delete(project)
        } else {
            projectRepository.save(project)
        }
        userRepository.save(user)
    }

    fun addRequiredSkillsToProject(projectId: Long, skillIds: List<Long>) {
        val project = projectRepository.findById(projectId)
        if (skillIds == null || skillIds.isEmpty()) {
            throw IllegalArgumentException("skillIds cannot be null or empty")
        }
        val skills = skillRepository.findAllById(skillIds)
        skills.removeIf(Objects::isNull)
        if (skills.isEmpty()) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid request, all skill ids are null"
            )
        }
        project.ifPresent {
            it.requiredSkills.addAll(skills)
            projectRepository.save(it)
        }
    }


    fun getRequiredSkills(project: Project): List<SkillDTO> {
        return project.requiredSkills.map { skill -> SkillDTO(skill.id!!, skill.name) }
    }
}
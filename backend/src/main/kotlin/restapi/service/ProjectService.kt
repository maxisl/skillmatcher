package restapi.service

import restapi.model.Project
import restapi.repository.UserRepository
import restapi.repository.ProjectRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

/**
 * @author  Simon Burmer
 * @date  02/12/22
 * @version 1.0
 */


@Service
class ProjectService(
    @Autowired val repository: ProjectRepository,
    @Autowired val userRepository: UserRepository
    ) {


    fun getAll(): MutableList<Project> = repository.findAll()

    fun getAllByUser(userId: Long): MutableList<Project> = repository.findByApiUserId(userId);

    fun getAllByName(id: String): MutableList<Project> = repository.findByNameContaining(id)

    fun getById(id: Long): Project = repository.findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    fun create(userid: Long, project: Project): Project {

        var user = userRepository.findByIdOrNull(userid) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        project.apiUser = user
        return repository.save(project)
    }

    fun remove(id: Long) {
        if (repository.existsById(id)) repository.deleteById(id)
        else throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    fun update(id: Long, project: Project): Project {
        return if (repository.existsById(id)) {
            project.id = id
            repository.save(project)
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

}
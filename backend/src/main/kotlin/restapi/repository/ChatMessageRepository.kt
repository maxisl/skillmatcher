package restapi.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import restapi.model.ChatMessage

@Repository
interface ChatMessageRepository : JpaRepository<ChatMessage, Long> {
    fun findByProjectId(projectId: Long): List<ChatMessage>
}

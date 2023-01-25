package restapi.service

import org.springframework.stereotype.Service
import restapi.model.ChatMessage
import restapi.model.ChatMessageRequest
import restapi.repository.ChatMessageRepository
import restapi.repository.UserRepository
import java.time.LocalDateTime

@Service
class ChatMessageService(
    private val chatMessageRepository: ChatMessageRepository,
    private val userRepository: UserRepository,
    private val projectService: ProjectService
) {
    fun getAll(): MutableList<ChatMessage> {
        return chatMessageRepository.findAll()
    }

    fun getAllMessagesByProject(projectId: Long): List<ChatMessage> {
        return chatMessageRepository.findByProjectId(projectId)
    }

    fun create(messageRequest: ChatMessageRequest): ChatMessage {
        val project = projectService.getById(messageRequest.projectId)
        val sender = userRepository.findUserByEmail(messageRequest.senderEmail)
        val chatMessage = ChatMessage(
            id = null,
            message = messageRequest.message,
            sender = sender ?: throw IllegalArgumentException("Invalid sender id"),
            project = project,
            timestamp = LocalDateTime.now().toString()
        )
        return chatMessageRepository.save(chatMessage)
    }
}
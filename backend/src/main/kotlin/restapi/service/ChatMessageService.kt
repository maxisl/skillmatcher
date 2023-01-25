package restapi.service

import org.springframework.stereotype.Service
import restapi.model.ChatMessage
import restapi.model.ChatMessageRequest
import restapi.repository.ChatMessageRepository

@Service
class ChatMessageService(private val chatMessageRepository: ChatMessageRepository) {
    fun create(messageRequest: ChatMessageRequest): ChatMessage {
        val chatMessage = ChatMessage(
            id = null,
            message = messageRequest.message,
            sender =
        )
        return chatMessageRepository.save(message)
    }
}
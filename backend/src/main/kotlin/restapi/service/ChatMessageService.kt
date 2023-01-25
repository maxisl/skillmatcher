package restapi.service

import org.springframework.stereotype.Service
import restapi.model.ChatMessage
import restapi.repository.ChatMessageRepository

@Service
class ChatMessageService(private val chatMessageRepository: ChatMessageRepository) {
    fun saveMessage(message: ChatMessage): ChatMessage {
        return chatMessageRepository.save(message)
    }
}
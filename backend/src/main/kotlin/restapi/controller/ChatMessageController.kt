package restapi.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import restapi.model.*
import restapi.repository.UserRepository
import restapi.service.ChatMessageService

@RequestMapping("chat-messages")
@RestController
class ChatMessageController(
    private val chatMessageService: ChatMessageService,
    private val userRepository: UserRepository
) {

    @PostMapping("/{projectId}/{userEmail}")
    fun saveMessage(
        @PathVariable projectId: Long,
        @PathVariable userEmail: String,
        @RequestBody messageRequest: ChatMessageRequest
    ): ResponseEntity<ChatMessage> {
        val user = userRepository.findUserByEmail(userEmail)
        val chatMessage = chatMessageService.create(ChatMessageRequest)
        return ResponseEntity.ok(chatMessageService.save(chatMessage))
    }

}
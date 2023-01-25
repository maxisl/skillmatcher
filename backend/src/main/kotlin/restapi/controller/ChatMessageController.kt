package restapi.controller

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import restapi.jsonView.DataView
import restapi.model.*
import restapi.repository.ChatMessageRepository
import restapi.repository.UserRepository
import restapi.service.ChatMessageService
import restapi.service.ProjectService

@RequestMapping("chat-messages")
@RestController
class ChatMessageController(
    private val chatMessageService: ChatMessageService,
    private val userRepository: UserRepository,
    private val projectService: ProjectService,
    private val chatMessageRepository: ChatMessageRepository
) {

    // TODO check if user attends projects before returning messages for that project?
    @JsonView(DataView.ChatMessage::class)
    @GetMapping
    fun getAllChatMessages() = chatMessageService.getAll()

    @GetMapping("/project/{projectId}")
    fun getAllMessagesByProject(@PathVariable projectId: Long): List<ChatMessage> {
        return chatMessageService.getAllMessagesByProject(projectId)
    }


    @JsonView(DataView.ChatMessage::class)
    @PostMapping("/")
    fun saveMessage(
        @RequestBody messageRequest: ChatMessageRequest
    ): ResponseEntity<ChatMessage> {
        return ResponseEntity.ok(chatMessageService.create(messageRequest))
    }
}
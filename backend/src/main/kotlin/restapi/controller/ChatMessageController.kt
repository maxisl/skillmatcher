package restapi.controller

import com.fasterxml.jackson.annotation.JsonView
import restapi.model.Project
import restapi.service.ProjectService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import restapi.jsonView.DataView
import restapi.model.ChatMessage
import restapi.model.ProjectRequest
import restapi.model.SkillDTO
import restapi.repository.ProjectRepository
import restapi.repository.UserRepository
import restapi.service.ChatMessageService
import restapi.service.UserService

@RequestMapping("chat-messages")
@RestController
class ChatMessageController(private val chatMessageService: ChatMessageService) {

    @PostMapping("/")
    fun saveMessage(@RequestBody message: ChatMessage): ChatMessage {
        return chatMessageService.saveMessage(message)
    }
}
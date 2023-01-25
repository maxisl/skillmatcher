package restapi.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonView
import restapi.jsonView.DataView
import java.time.LocalDateTime
import javax.persistence.*;

@Entity
@Table(name = "chat_messages")
data class ChatMessage(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long?,

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @JsonBackReference
    var project: Project,

    @JsonView(DataView.ChatMessage::class)
    @Column(nullable = false)
    var message: String,

    @JsonView(DataView.ChatMessage::class)
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    var sender: User,

    @JsonView(DataView.ChatMessage::class)
    @JsonProperty("senderEmail")
    val senderEmail: String? = sender.email,

    @JsonView(DataView.ChatMessage::class)
    @Column(nullable = false)
    var timestamp: String
)

data class ChatMessageRequest(
    val projectId: Long,
    val message: String,
    val senderEmail: String
)
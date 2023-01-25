package restapi.model

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
    var project: Project,

    @Column(nullable = false)
    var message: String,

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    var sender: User,

    @Column(nullable = false)
    var timestamp: String
)

data class ChatMessageRequest(
    val message: String,
    val sender: User,
    val timestamp: String
)
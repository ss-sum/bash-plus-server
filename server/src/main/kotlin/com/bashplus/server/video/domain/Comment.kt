package com.bashplus.server.video.domain

import com.bashplus.server.users.domain.Users
import com.bashplus.server.video.dto.CommentRequestDTO
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import java.time.LocalDateTime
import java.time.ZoneId

@Entity
class Comment(
    @ManyToOne @JoinColumn(name = "uid") var user: Users,
    @ManyToOne @JoinColumn(name = "vid") var video: Video,
    content: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var cid: Long? = null
    var content: String = content
    var createdAt: LocalDateTime = LocalDateTime.now(ZoneId.of("UTC"))

    @ColumnDefault("0")
    var likes: Long = 0

    open fun update(request: CommentRequestDTO) {
        this.content = request.content
    }

    open fun like() {
        this.likes++
    }

    open fun unlike() {
        if (this.likes > 0) {
            this.likes--
        }
    }
}
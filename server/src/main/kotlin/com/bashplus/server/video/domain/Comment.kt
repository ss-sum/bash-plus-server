package com.bashplus.server.video.domain

import com.bashplus.server.users.domain.Users
import com.bashplus.server.video.dto.CommentRequestDTO
import jakarta.persistence.*
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
    var like: Long = 0

    open fun update(request: CommentRequestDTO) {
        this.content = request.content
    }

    open fun like() {
        this.like++
    }

    open fun unlike() {
        if (this.like > 0) {
            this.like--
        }
    }
}
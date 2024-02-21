package com.bashplus.server.video.domain

import com.bashplus.server.users.domain.Users
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Comment(@ManyToOne @JoinColumn(name = "uid") var user: Users, @ManyToOne @JoinColumn(name = "vid") var video: Video, content: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var cid: Long? = null
    var content: String = content
    var createdAt: LocalDateTime = LocalDateTime.now()
}
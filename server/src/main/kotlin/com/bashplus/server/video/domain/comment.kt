package com.bashplus.server.video.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class comment(uid: String, vid: String, content: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var cid: String? = null
    var uid: String = uid
    var vid: String = vid
    var content: String = content
    var createdAt: LocalDateTime = LocalDateTime.now()
}
package com.bashplus.server.archive.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
class archive(uid: String, vid: String, time: LocalTime? = null, like: Boolean = false, last: Boolean = false) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var uidvid: String? = null
    var uid: String = uid
    var vid: String = vid
    var time: LocalTime? = time
    var likes: Boolean = like
    var last: Boolean = last
    var createdAt: LocalDateTime = LocalDateTime.now()
}
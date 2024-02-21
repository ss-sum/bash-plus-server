package com.bashplus.server.archive.domain

import com.bashplus.server.users.domain.Users
import com.bashplus.server.video.domain.Video
import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
class Archive(@ManyToOne
              @JoinColumn(name = "uid")
              var user: Users, @ManyToOne @JoinColumn(name = "vid") var video: Video, time: LocalTime? = null, like: Boolean = false, last: Boolean = false) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var uidvid: Long? = null
    var time: LocalTime? = time
    var likes: Boolean = like
    var last: Boolean = last
    var createdAt: LocalDateTime = LocalDateTime.now()
}
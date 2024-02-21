package com.bashplus.server.video.domain

import com.bashplus.server.host.domain.Conference
import jakarta.persistence.*

@Entity
class Video(@ManyToOne @JoinColumn(name = "coid") var conference: Conference, url: String, title: String, content: String? = null) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var vid: Long? = null
    var url: String = url
    var title: String = title
    var content: String? = content
}
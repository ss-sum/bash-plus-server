package com.bashplus.server.video.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class video(coid: String, url: String, title: String, content: String? = null) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var vid: String? = null
    var coid: String = coid
    var url: String = url
    var title: String = title
    var content: String? = content
}
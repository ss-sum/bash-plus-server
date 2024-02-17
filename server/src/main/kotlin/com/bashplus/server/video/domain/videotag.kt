package com.bashplus.server.video.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class videotag(vid: String, tid: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var vtid: String? = null
    var vid: String = vid
    var tid: String = tid
}
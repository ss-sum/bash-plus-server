package com.bashplus.server.video.domain

import com.bashplus.server.information.domain.Category
import jakarta.persistence.*

@Entity
class VideoTag(@ManyToOne @JoinColumn(name = "vid") var video: Video, @ManyToOne @JoinColumn(name = "tid") var category: Category) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var vtid: Long? = null
}
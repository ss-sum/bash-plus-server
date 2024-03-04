package com.bashplus.server.video.repository

import com.bashplus.server.video.domain.Video
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
open interface VideoRepository : JpaRepository<Video, String> {
    open fun findByVid(vid: Long): Optional<Video>
    open fun findAllByTitleIsLike(title: String): List<Video>
}
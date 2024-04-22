package com.bashplus.server.video.repository

import com.bashplus.server.video.domain.Video
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
open interface VideoRepository : JpaRepository<Video, String> {
    open fun findByVid(vid: Long): Optional<Video>
    open fun findAllByConferenceTitle(title: String, pageable: Pageable): Page<Video>
    open fun findAllByTitleIsLikeIgnoreCase(title: String, pageable: Pageable): Page<Video>

    @Query("SELECT v FROM Video v WHERE v.conference.coid = (SELECT ch.conference.coid FROM ConferenceHost ch JOIN Host h ON ch.host.hid = h.hid WHERE LOWER(h.company) like LOWER(:host))")
    open fun findAllByConferenceHost(host: String, pageable: Pageable): Page<Video>
}
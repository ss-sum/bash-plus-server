package com.bashplus.server.video.repository

import com.bashplus.server.video.domain.VideoTag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
open interface VideoTagRepository : JpaRepository<VideoTag, String> {

    @Query("SELECT c.category FROM VideoTag v JOIN Category c ON v.category.tid = c.tid WHERE v.video.vid = :vid")
    fun findTagByVid(vid: Long): ArrayList<String>
}
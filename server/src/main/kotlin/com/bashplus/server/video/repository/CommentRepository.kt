package com.bashplus.server.video.repository

import com.bashplus.server.video.domain.Comment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
open interface CommentRepository : JpaRepository<Comment, String> {
    open fun findAllByUserUid(uid: Long, pageable: Pageable): Page<Comment>
    open fun findAllByVideoVid(vide: Long): ArrayList<Comment>
}
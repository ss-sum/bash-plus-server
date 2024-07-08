package com.bashplus.server.video.repository

import com.bashplus.server.video.domain.Comment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
open interface CommentRepository : JpaRepository<Comment, String> {
    open fun findAllByUserUid(uid: Long, pageable: Pageable): Page<Comment>
    open fun findAllByVideoVid(vid: Long, pageable: Pageable): Page<Comment>
    open fun findByCid(cid: Long): Optional<Comment>
    open fun findAllByVideoVidOrderByCreatedAtDesc(vid: Long, pageable: Pageable): Page<Comment>
    open fun findAllByVideoVidOrderByCreatedAtAsc(vid: Long, pageable: Pageable): Page<Comment>
    open fun findAllByVideoVidOrderByLikesDesc(vid: Long, pageable: Pageable): Page<Comment>
    open fun findAllByVideoVidOrderByLikesAsc(vid: Long, pageable: Pageable): Page<Comment>
}
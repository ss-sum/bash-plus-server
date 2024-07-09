package com.bashplus.server.video.repository

import com.bashplus.server.video.domain.CommentLike
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CommentLikeRepository : JpaRepository<CommentLike, String> {
    open fun findByCommentCidAndUserUid(cid: Long, uid: Long): Optional<CommentLike>
}
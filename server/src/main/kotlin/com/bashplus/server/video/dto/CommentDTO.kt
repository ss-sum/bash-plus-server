package com.bashplus.server.video.dto

import com.bashplus.server.video.domain.Comment
import java.time.LocalDateTime

class CommentDTO private constructor(val vid: Long, val content: String, val createAt: LocalDateTime) {
    constructor(comment: Comment) :
            this(
                    vid = comment.video.vid ?: 0,
                    content = comment.content,
                    createAt = comment.createdAt
            )
}
package com.bashplus.server.video.dto

import com.bashplus.server.video.domain.Comment
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

class CommentDTO private constructor(val vid: Long, val content: String, @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC") val createdAt: LocalDateTime) {
    constructor(comment: Comment) :
            this(
                    vid = comment.video.vid ?: 0,
                    content = comment.content,
                    createdAt = comment.createdAt
            )
}
package com.bashplus.server.video.dto

import lombok.NoArgsConstructor

@NoArgsConstructor
class CommentRequestDTO {
    var uid: Long = 0
    var vid: Long = 0
    var cid: Long = 0
    var content: String = ""
}
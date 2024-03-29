package com.bashplus.server.information.dto

import com.bashplus.server.video.domain.Video

class VideoInformationDTO private constructor(val coid: Long, val url: String, val title: String, val content: String) {
    constructor(video: Video) :
            this(
                    coid = video.conference.coid?.toLong() ?: 0,
                    url = video.url,
                    title = video.title,
                    content = video.content.toString()
            )
}
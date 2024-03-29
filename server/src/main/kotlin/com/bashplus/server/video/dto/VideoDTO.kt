package com.bashplus.server.video.dto

import com.bashplus.server.video.domain.Video

class VideoDTO private constructor(val coid: Long, val url: String, val title: String, val content: String, val category: ArrayList<String>) {
    constructor(video: Video, category: ArrayList<String>) :
            this(
                    coid = video.conference.coid?.toLong() ?: 0,
                    url = video.url,
                    title = video.title,
                    content = video.content.toString(),
                    category = category
            )
}
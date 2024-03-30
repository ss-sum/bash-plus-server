package com.bashplus.server.archive.dto

import com.bashplus.server.archive.domain.Archive

class ArchiveVideoDTO private constructor(val vid: Long, val title: String, val content: String, val url: String, val conference: String) {
    constructor(archive: Archive) :
            this(
                    vid = archive.video.vid ?: 0,
                    title = archive.video.title,
                    content = archive.video.content ?: "",
                    url = archive.video.url,
                    conference = archive.video.conference.title
            )
}
package com.bashplus.server.archive.dto

import com.bashplus.server.archive.domain.Archive
import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalTime

@Schema(description = "기록 날짜", pattern = "HH:mm:ss", type = "String")
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "UTC")
class ArchiveVideoWatchRecordDTO private constructor(val vid: Long, val title: String, val content: String, val url: String, val conference: String, @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "UTC") val time: LocalTime) {
    constructor(archive: Archive) :
            this(
                    vid = archive.video.vid ?: 0,
                    title = archive.video.title,
                    content = archive.video.content ?: "",
                    url = archive.video.url,
                    conference = archive.video.conference.title,

                    time = archive.time ?: LocalTime.now()
            )
}
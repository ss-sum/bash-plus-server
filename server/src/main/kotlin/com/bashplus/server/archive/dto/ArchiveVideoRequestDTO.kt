package com.bashplus.server.archive.dto

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalTime

@Schema(description = "영상 시간", pattern = "HH:mm:ss", type = "String")
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "UTC")
class ArchiveVideoRequestDTO {
    var vid: Long = 0
    var uid: Long = 0

    @DateTimeFormat(pattern = "HH:mm:ss")
    var time: LocalTime = LocalTime.now()
}
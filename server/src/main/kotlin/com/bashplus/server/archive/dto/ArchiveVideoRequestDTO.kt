package com.bashplus.server.archive.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalTime

class ArchiveVideoRequestDTO {
    var vid: Long = 0
    var uid: Long = 0

    @DateTimeFormat(pattern = "HH:mm:ss")
    var time: LocalTime = LocalTime.now()
}
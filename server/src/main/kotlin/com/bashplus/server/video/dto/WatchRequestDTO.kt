package com.bashplus.server.video.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalTime

class WatchRequestDTO {
    var uid: Long = 0
    var vid: Long = 0

    @DateTimeFormat(pattern = "HH:mm:ss")
    var time: LocalTime = LocalTime.now()
}
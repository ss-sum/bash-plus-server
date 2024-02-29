package com.bashplus.server.video.dto

import java.time.LocalTime

class WatchRequestDTO {
    var uid: Long = 0
        private set
    var vid: Long = 0
        private set
    var time: LocalTime = LocalTime.now()
        private set
}
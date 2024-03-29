package com.bashplus.server.archive.dto

import java.time.LocalTime

class ArchiveVideoRequestDTO {
    var vid: Long = 0
        private set
    var uid: Long = 0
        private set
    var time: LocalTime = LocalTime.now()
        private set
}
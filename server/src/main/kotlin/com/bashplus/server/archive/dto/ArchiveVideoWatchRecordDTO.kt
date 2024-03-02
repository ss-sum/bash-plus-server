package com.bashplus.server.archive.dto

import com.bashplus.server.archive.domain.Archive
import java.time.LocalTime

class ArchiveVideoWatchRecordDTO private constructor(val vid: Long, val time: LocalTime) {
    constructor(archive: Archive) :
            this(
                    vid = archive.video.vid ?: 0,
                    time = archive.time ?: LocalTime.now()
            )
}
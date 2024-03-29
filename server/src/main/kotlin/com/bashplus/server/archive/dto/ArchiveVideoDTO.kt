package com.bashplus.server.archive.dto

import com.bashplus.server.archive.domain.Archive

class ArchiveVideoDTO private constructor(val vid: Long) {
    constructor(archive: Archive) :
            this(
                    vid = archive.video.vid ?: 0
            )
}
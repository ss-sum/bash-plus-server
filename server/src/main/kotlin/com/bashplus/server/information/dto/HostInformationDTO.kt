package com.bashplus.server.information.dto

import com.bashplus.server.host.domain.Host

class HostInformationDTO private constructor(val hid: Long) {
    constructor(host: Host) :
            this(
                    hid = host.hid ?: 0
            )
}
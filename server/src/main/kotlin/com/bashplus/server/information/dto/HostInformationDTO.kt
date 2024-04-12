package com.bashplus.server.information.dto

import com.bashplus.server.host.domain.Host

class HostInformationDTO private constructor(val hid: Long, val company: String, val channel: String, val content: String) {
    constructor(host: Host) :
            this(
                    hid = host.hid ?: 0,
                    company = host.company,
                    channel = host.channel,
                    content = host.content ?: ""
            )
}
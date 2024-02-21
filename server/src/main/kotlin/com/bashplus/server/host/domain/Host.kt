package com.bashplus.server.host.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Host(company: String, channel: String, content: String? = null) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var hid: Long? = null
    var company: String = company
    var channel: String = channel
    var content: String? = content
}
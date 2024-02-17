package com.bashplus.server.host.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class host(company: String, channel: String, content: String? = null) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var hid: String? = null
    var company: String = company
    var channel: String = channel
    var content: String? = content
}
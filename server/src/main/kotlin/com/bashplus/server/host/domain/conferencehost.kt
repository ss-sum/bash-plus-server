package com.bashplus.server.host.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class conferencehost(coid: String, hid: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var chid: String? = null
    var coid: String? = coid
    var hid: String? = hid
}
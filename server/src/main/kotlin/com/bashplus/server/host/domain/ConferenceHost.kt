package com.bashplus.server.host.domain

import jakarta.persistence.*

@Entity
class ConferenceHost(@ManyToOne @JoinColumn(name = "coid") var conference: Conference, @ManyToOne @JoinColumn(name = "hid") var host: Host) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var chid: Long? = null
}
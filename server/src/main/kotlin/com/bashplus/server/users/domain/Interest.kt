package com.bashplus.server.users.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Interest(uid: String, tid: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var utid: String? = null
    var uid: String = uid
    var tid: String = tid
}
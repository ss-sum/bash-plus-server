package com.bashplus.server.users.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Users(id: String, type: String, name: String, access: String? = null, refresh: String? = null, email: String? = null) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var uid: Long? = null
    var id: String = id
    var type: String = type
    var name: String = name
    var access: String? = access
    var refresh: String? = refresh
    var email: String? = email
}
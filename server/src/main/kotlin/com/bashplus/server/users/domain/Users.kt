package com.bashplus.server.users.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Users(type: String, name: String, access: String? = null, refresh: String? = null, email: String? = null) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null
    var type: String = type
    var name: String = name
    var access: String? = access
    var refresh: String? = refresh
    var email: String? = email
}
package com.bashplus.server.information.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class category(category: String, level: Int) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var tid: String? = null
    var category: String = category
    var level: Int = level
}
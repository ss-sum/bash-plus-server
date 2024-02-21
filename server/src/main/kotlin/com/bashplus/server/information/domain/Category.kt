package com.bashplus.server.information.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Category(category: String, level: Int) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var tid: Long? = null
    var category: String = category
    var level: Int = level
}
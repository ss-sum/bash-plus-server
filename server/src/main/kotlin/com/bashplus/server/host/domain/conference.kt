package com.bashplus.server.host.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDate

@Entity
class conference(title: String, content: String? = null, startAtTime: LocalDate, endAtTime: LocalDate) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var coid: String? = null
    var title: String = title
    var content: String? = content
    var startAtTime: LocalDate = startAtTime
    var endAtTime: LocalDate = endAtTime
}
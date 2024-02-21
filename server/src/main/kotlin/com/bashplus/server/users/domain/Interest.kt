package com.bashplus.server.users.domain

import com.bashplus.server.information.domain.Category
import jakarta.persistence.*

@Entity
class Interest(@ManyToOne @JoinColumn(name = "uid") var users: Users, @ManyToOne @JoinColumn(name = "tid") var category: Category) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var utid: Long? = null
}
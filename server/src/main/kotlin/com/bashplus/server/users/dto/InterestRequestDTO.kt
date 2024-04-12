package com.bashplus.server.users.dto

class InterestRequestDTO(category: String, level: Int) {
    var category: String = category ?: ""
    var level: Int = level ?: 0
}
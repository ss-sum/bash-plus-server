package com.bashplus.server.users.dto

class InterestRequestDTO(category: String, level: Int) {
    var category: String = category ?: ""
        private set
    var level: Int = level ?: 0
        private set
}
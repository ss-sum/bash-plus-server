package com.bashplus.server.users.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

class Auth2UserDTO(id: String, name: String, email: String) : OAuth2User {
    val apiId: String = id
    val userName: String = name
    val email: String = email

    override fun getName(): String {
        return apiId
    }

    override fun getAttributes(): MutableMap<String, Any> {
        return mutableMapOf("apiId" to apiId, "userName" to name, "email" to email)
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authorities.toMutableSet()
    }


}
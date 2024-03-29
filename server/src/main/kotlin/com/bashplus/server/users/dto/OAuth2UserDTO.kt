package com.bashplus.server.users.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User

class OAuth2UserDTO private constructor(val id: String, val userName: String, val email: String, val platform: String, val token: String) : OAuth2User {

    constructor(attributes: Map<String, Any>, userRequest: OAuth2UserRequest) :
            this(
                    id = attributes[ID_ATTRIBUTE]?.toString() ?: "",
                    userName = attributes[NAME_ATTRIBUTE]?.toString() ?: "",
                    email = attributes[EMAIL_ATTRIBUTE]?.toString() ?: "",
                    platform = userRequest.clientRegistration.registrationId ?: "",
                    token = userRequest.accessToken.tokenValue.toString() ?: ""
            )


    companion object {

        private const val ID_ATTRIBUTE: String = "sub";
        private const val EMAIL_ATTRIBUTE: String = "email";
        private const val NAME_ATTRIBUTE: String = "name";
    }

    override fun getName(): String {
        return userName
    }

    override fun getAttributes(): MutableMap<String, Any> {
        return mutableMapOf("id" to id, "userName" to userName, "email" to email)
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authority = SimpleGrantedAuthority("ROLE_USER")
        return mutableSetOf(authority)
    }


}
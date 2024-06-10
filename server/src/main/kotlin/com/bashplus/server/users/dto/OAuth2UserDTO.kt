package com.bashplus.server.users.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User

class OAuth2UserDTO private constructor(
    var uid: Long,
    val id: String,
    val userName: String,
    val email: String,
    val platform: String,
    val accessToken: String,
    val refreshToken: String?
) : OAuth2User {

    constructor(attributes: Map<String, Any>, userRequest: OAuth2UserRequest) :
            this(
                uid = 0,
                id = attributes[ID_ATTRIBUTE]?.toString() ?: attributes[ID_ATTRIBUTE_GITHUB]?.toString() ?: "",
                userName = attributes[NAME_ATTRIBUTE]?.toString() ?: "",
                email = attributes[EMAIL_ATTRIBUTE]?.toString() ?: "",
                platform = userRequest.clientRegistration.registrationId ?: "",
                accessToken = userRequest.accessToken.tokenValue.toString() ?: "",
                refreshToken = userRequest.additionalParameters["refresh_token"]?.toString() ?: null
            )


    companion object {

        private const val ID_ATTRIBUTE: String = "sub";
        private const val ID_ATTRIBUTE_GITHUB: String = "id";
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
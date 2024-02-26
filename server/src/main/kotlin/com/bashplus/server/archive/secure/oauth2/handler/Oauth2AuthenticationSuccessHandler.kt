package com.bashplus.server.archive.secure.oauth2.handler

import com.bashplus.server.archive.secure.jwt.TokenProvider
import com.bashplus.server.users.dto.Auth2UserDTO
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class Oauth2AuthenticationSuccessHandler @Autowired constructor(private val tokenProvider: TokenProvider) : SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        //val user: Auth2UserDTO? = authentication?.principal as? Auth2UserDTO
        val user = (authentication?.principal as DefaultOidcUser).attributes as Map<String, Any>
        val authUser = Auth2UserDTO(user.get("sub").toString(), user.get("name").toString(), user.get("email").toString())
        val accessToken = tokenProvider.createToken(authentication)
        response?.addHeader("Authorization", accessToken)
        val requestDispatcher = request?.getRequestDispatcher("/auth/authorization/" + (authentication as OAuth2AuthenticationToken).authorizedClientRegistrationId.toString())
        requestDispatcher?.forward(request, response)
    }


}
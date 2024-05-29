package com.bashplus.server.common.secure.oauth2.handler

import com.bashplus.server.common.exception.ApiException
import com.bashplus.server.common.exception.ExceptionEnum
import com.bashplus.server.common.secure.jwt.TokenProvider
import com.bashplus.server.users.dto.OAuth2UserDTO
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.security.web.savedrequest.SavedRequest
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class Oauth2AuthenticationSuccessHandler @Autowired constructor(private val tokenProvider: TokenProvider) :
    SimpleUrlAuthenticationSuccessHandler() {

    private var requestCache = HttpSessionRequestCache()
    private var redirectStrategy = DefaultRedirectStrategy()
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val user: OAuth2UserDTO? = authentication?.principal as? OAuth2UserDTO
        try {
            val accessToken = tokenProvider.createToken(authentication!!)
            val savedRequest: SavedRequest? = requestCache.getRequest(request, response)
            requestCache.removeRequest(request, response)
            var uri: String? = savedRequest?.redirectUrl ?: "/auth/authorization/${user?.platform!!}"
            val cookie = Cookie("Authorization", accessToken)
            cookie.secure = true
            cookie.isHttpOnly = true
            cookie.path = "/"
            response?.addCookie(cookie)
            redirectStrategy.sendRedirect(request, response, uri);
        } catch (e: Exception) {
            throw ApiException(ExceptionEnum.TOKEN_ERROR)
        }
    }


}
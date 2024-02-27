package com.bashplus.server.archive.secure.oauth2.handler

import com.bashplus.server.archive.secure.jwt.TokenProvider
import com.bashplus.server.common.exception.ApiException
import com.bashplus.server.common.exception.ExceptionEnum
import com.bashplus.server.users.dto.OAuth2UserDTO
import com.bashplus.server.users.repository.UsersRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class Oauth2AuthenticationSuccessHandler @Autowired constructor(private val tokenProvider: TokenProvider, private val usersRepository: UsersRepository) : SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        val user: OAuth2UserDTO? = authentication?.principal as? OAuth2UserDTO
        try {
            val accessToken = tokenProvider.createToken(authentication!!)
            response?.addHeader("Authorization", accessToken)
            usersRepository.updateToken(user?.id!!, user?.platform!!, accessToken, user.token)
            val requestDispatcher = request?.getRequestDispatcher("/auth/authorization/${user.platform}")
            requestDispatcher?.forward(request, response)
        } catch (e: Exception) {
            throw ApiException(ExceptionEnum.TOKEN_ERROR)
        }
    }


}
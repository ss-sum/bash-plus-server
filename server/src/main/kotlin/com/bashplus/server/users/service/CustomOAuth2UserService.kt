package com.bashplus.server.users.service

import com.bashplus.server.common.exception.ApiException
import com.bashplus.server.common.exception.ExceptionEnum
import com.bashplus.server.users.domain.Users
import com.bashplus.server.users.dto.OAuth2UserDTO
import com.bashplus.server.users.repository.UsersRepository
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.util.*

@RequiredArgsConstructor
@Service
class CustomOAuth2UserService : DefaultOAuth2UserService() {


    @Autowired
    private lateinit var usersRepository: UsersRepository

    override open fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val attributes: Map<String, Any>? = super.loadUser(userRequest).getAttributes()
        return isExistedUserOrJoin(attributes, userRequest)
    }

    private fun isExistedUserOrJoin(attributes: Map<String, Any>?, userRequest: OAuth2UserRequest?): OAuth2UserDTO {
        if (attributes.isNullOrEmpty() || userRequest == null) {
            throw ApiException(ExceptionEnum.BAD_REQUEST)
        }
        var customOAuth2User = OAuth2UserDTO(attributes!!, userRequest!!)
        var user = joinOrUpdate(customOAuth2User)
        customOAuth2User.uid = user.uid!!
        return customOAuth2User
    }

    private fun joinOrUpdate(authUser: OAuth2UserDTO): Users {
        var user: Optional<Users> = usersRepository.findByIdAndType(authUser.id, authUser.platform)
        if (!user.isPresent) {
            return usersRepository.save(
                Users(
                    authUser.id,
                    authUser.platform,
                    authUser.name,
                    authUser.accessToken,
                    authUser.refreshToken,
                    authUser.email,
                    authUser.image
                )
            )
        } else {
            usersRepository.updateToken(
                user.get().id, user.get().type, authUser.accessToken, authUser.refreshToken
                    ?: ""
            )
            return user.get()
        }
    }

}
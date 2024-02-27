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
        val customOAuth2User = OAuth2UserDTO(attributes!!, userRequest!!)
        val user: Optional<Users> = usersRepository.findByIdAndType(customOAuth2User.id, customOAuth2User.platform)
        if (!user.isPresent) {
            join(customOAuth2User)
        }
        return customOAuth2User
    }

    private fun join(authUser: OAuth2UserDTO): Unit {
        usersRepository.save(Users(authUser.id, authUser.platform, authUser.name, null, null, authUser.email))
    }

}
package com.bashplus.server.users.service

import com.bashplus.server.users.domain.Users
import com.bashplus.server.users.dto.Auth2UserDTO
import com.bashplus.server.users.repository.UsersRepository
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@RequiredArgsConstructor
@Service
class OAuth2UserService() : DefaultOAuth2UserService() {

    private val ID_ATTRIBUTE: String = "id";
    private val EMAIL_ATTRIBUTE: String = "email";

    @Autowired
    private lateinit var usersRepository: UsersRepository

    @Transactional
    override open fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val attributes: Map<String, Any>? = super.loadUser(userRequest).getAttributes()
        val apiId: String = (attributes?.get(ID_ATTRIBUTE)).toString()
        if (isExistedUser(apiId)) {
            return super.loadUser(userRequest)
        } else {
            throw UsernameNotFoundException(apiId)
        }
    }

    private fun isExistedUser(id: String): Boolean {
        val user: Optional<Users> = usersRepository.findById(id)
        if (user.isEmpty) {
            return false;
        } else {
            return true;
        }
    }

    private fun join(authUser: Auth2UserDTO): Unit {
        //var user: Users = Users(authUser.id,,,,authUser.email)
    }

}
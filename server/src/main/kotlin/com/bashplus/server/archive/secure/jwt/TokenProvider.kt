package com.bashplus.server.archive.secure.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.bashplus.server.users.dto.Auth2UserDTO
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Collectors

@Component
class TokenProvider : InitializingBean {
    private val AUTHORITIES_KEY: String = "auth";

    @Value("\${jwt.secret}")
    private lateinit var secret: String;

    @Value("\${jwt.token-validation-time}")
    private var tokenValidationTime: Long = 0

    override fun afterPropertiesSet() {
    }


    open fun createToken(authentication: Authentication): String {
        val authorities: String = authentication.authorities
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","))
        val expired: Date = Date(Date().time + this.tokenValidationTime)
        return "Bearer " + JWT.create()
                .withSubject(authentication.name)
                .withClaim(AUTHORITIES_KEY, authorities)
                .withExpiresAt(expired)
                .sign(Algorithm.HMAC512(secret))
    }

    open fun getAuthentication(token: String): Authentication {
        val claims = JWT.require(Algorithm.HMAC512(secret))
                .build()
                .verify(token)
                .claims
        val authorities = (claims[AUTHORITIES_KEY] as String).split(",")
                .map { SimpleGrantedAuthority(it) }
                .toList()
        val user = Auth2UserDTO(claims["sub"]?.asString() ?: "", "", "") //TODO

        return UsernamePasswordAuthenticationToken(user, token, authorities)

    }

    open fun validateToken(token: String): Boolean {
        val verifier: JWTVerifier = JWT.require(Algorithm.HMAC512(secret)).build()

        try {
            val decodedJWT: DecodedJWT = verifier.verify(token)
            return true
        } catch (e: SecurityException) {
            // 잘못된 JWT 서명
        } catch (e: IllegalArgumentException) {
            // 잘못된 JWT 토큰
        }
        return false

    }
}
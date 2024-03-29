package com.bashplus.server.setting

import com.bashplus.server.users.domain.Users
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.test.context.support.WithSecurityContextFactory

class CustomMockUserSecurityContextFactory : WithSecurityContextFactory<WithCustomMockUser> {
    private val securityContextHolderStrategy = SecurityContextHolder
            .getContextHolderStrategy()


    override fun createSecurityContext(annotation: WithCustomMockUser): SecurityContext {
        val email: String = annotation.id
        var role: String = "USER"
        var type: String = annotation.type
        var name: String = annotation.name
        var password: String = annotation.password
        val grantedAuthorities: MutableList<GrantedAuthority> = ArrayList()
        for (authority in annotation.authorities) {
            grantedAuthorities.add(SimpleGrantedAuthority(authority))
        }

        var user: Users = Users(email, type, name)
        val principal = User(name, password, true, true, true, true, grantedAuthorities)
        var authentication: Authentication = UsernamePasswordAuthenticationToken(principal,
                principal.password, principal.authorities)
        val context: SecurityContext = this.securityContextHolderStrategy.createEmptyContext()
        context.authentication = authentication
        return context
    }
}
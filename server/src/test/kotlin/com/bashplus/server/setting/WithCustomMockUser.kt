package com.bashplus.server.setting

import org.springframework.security.test.context.support.WithSecurityContext

@Retention(AnnotationRetention.RUNTIME)
@WithSecurityContext(factory = CustomMockUserSecurityContextFactory::class)
annotation class WithCustomMockUser(val id: String = "user", val type: String = "USER", val name: String = "user", val authorities: Array<String> = ["ROLE_USER"], val password: String = "PASSWORD") {

}

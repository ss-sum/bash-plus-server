package com.bashplus.server.common.secure

import com.bashplus.server.common.CustomExceptionHandlerFilter
import com.bashplus.server.common.secure.jwt.JwtAccessDeniedHandler
import com.bashplus.server.common.secure.jwt.JwtAuthenticationEntryPoint
import com.bashplus.server.common.secure.jwt.JwtFilter
import com.bashplus.server.common.secure.jwt.TokenProvider
import com.bashplus.server.common.secure.oauth2.handler.Oauth2AuthenticationFailureHandler
import com.bashplus.server.common.secure.oauth2.handler.Oauth2AuthenticationSuccessHandler
import com.bashplus.server.users.service.CustomOAuth2UserService
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
class SecurityConfig @Autowired constructor(private var tokenProvider: TokenProvider, private val userService: CustomOAuth2UserService) {

    private val authenticationSuccessHandler: Oauth2AuthenticationSuccessHandler = Oauth2AuthenticationSuccessHandler(tokenProvider)
    private val authenticationFailureHandler: Oauth2AuthenticationFailureHandler = Oauth2AuthenticationFailureHandler()
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler = JwtAccessDeniedHandler()
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint = JwtAuthenticationEntryPoint()

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web ->
            web.ignoring()
                    .requestMatchers(AntPathRequestMatcher("/auth/authorization/**"))
        }
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder();
    }


    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
                .csrf { csrf: CsrfConfigurer<HttpSecurity> -> csrf.disable() }
                .headers { headerConfig: HeadersConfigurer<HttpSecurity?> ->
                    headerConfig.frameOptions({ frameOptionsConfig ->
                        frameOptionsConfig.disable()
                    })
                }
                .httpBasic { it.disable() }
                .formLogin { it.disable() }
                .sessionManagement { it -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
                .exceptionHandling {
                    it.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                            .accessDeniedHandler(jwtAccessDeniedHandler)
                }
                .authorizeHttpRequests { request ->
                    request
                            .requestMatchers(AntPathRequestMatcher("/")).permitAll()
                            .requestMatchers(AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher("/swagger-resources/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher("/error")).permitAll()
                            .requestMatchers(AntPathRequestMatcher("/oauth2/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher("/auth/login/social/platform/**")).permitAll()
                            .anyRequest().authenticated()
                }
                .oauth2Login {
                    it.userInfoEndpoint { endpoint ->
                        endpoint.userService(userService)
                    }
                            .authorizationEndpoint { endpoint -> endpoint.baseUri("/auth/login/social/platform") }
                            .successHandler(authenticationSuccessHandler)
                            .failureHandler(authenticationFailureHandler)

                }
                .addFilterBefore(JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter::class.java)
                .addFilterBefore(CustomExceptionHandlerFilter(), JwtFilter::class.java)
                .addFilterBefore(CustomExceptionHandlerFilter(), OAuth2LoginAuthenticationFilter::class.java)

        return http.build()
    }


}
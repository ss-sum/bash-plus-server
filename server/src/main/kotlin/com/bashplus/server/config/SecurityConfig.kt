package com.bashplus.server.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web -> web.ignoring() }
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
                .formLogin { formLogin: FormLoginConfigurer<HttpSecurity?> -> formLogin.disable() }
                .authorizeHttpRequests { request ->
                    request
                            .requestMatchers(AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher("/swagger-resources/**")).permitAll()
                            .requestMatchers(AntPathRequestMatcher("/error")).permitAll()
                            .requestMatchers(AntPathRequestMatcher("/auth/login/social/**")).permitAll()
                            .anyRequest().authenticated()
                }
                .oauth2Login { oauth2 ->
                    oauth2.defaultSuccessUrl("/")
                            .permitAll()
                }

        return http.build()
    }

}
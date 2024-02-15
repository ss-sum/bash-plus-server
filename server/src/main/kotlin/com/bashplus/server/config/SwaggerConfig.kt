package com.bashplus.server.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@Configuration
@EnableWebMvc
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
            .components(Components())
            .info(apiInfo())
    private fun apiInfo() = Info()
            .title("bash-plus")
            .description("bash-plus project의 API 명세서")
            .version("1.0.0")
}
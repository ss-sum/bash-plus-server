package com.bashplus.server.users.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
internal class AuthControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc


    @Test
    fun socialLoginInitTest() {
        mvc.perform(get("/auth/login/social/platform/google")
                .with(oauth2Login()))
                .andExpect(status().is3xxRedirection)
    }


    @Test
    @WithMockUser
    fun authorizationTest() {
        mvc.perform(post("/auth/logout"))
                .andExpect(status().isOk)
    }

}
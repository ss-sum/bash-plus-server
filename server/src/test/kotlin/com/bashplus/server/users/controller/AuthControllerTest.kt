package com.bashplus.server.users.controller

import com.bashplus.server.setting.WithCustomMockUser
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
internal class AuthControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    @WithCustomMockUser
    fun socialLoginInitTest() {
        mvc.perform(get("/auth/login/social/platform/google"))
                .andExpect(status().is3xxRedirection)
                .andDo(print())
    }

    @Test
    @WithCustomMockUser
    fun authorizationTest() {
        mvc.perform(post("/auth/logout"))
                .andExpect(status().isOk)
                .andDo(print())
    }

}
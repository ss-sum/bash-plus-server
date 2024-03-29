package com.bashplus.server.users.controller

import com.bashplus.server.setting.WithCustomMockUser
import org.junit.jupiter.api.DisplayName
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

//TODO - OAuth 관련 test mocking해서 하는 방법
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
internal class AuthControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @DisplayName("oauth 로그인 페이지로 요청을 보내면 해당 client의 인증 페이지로 redirect되는 것을 확인")
    @Test
    @WithCustomMockUser
    fun socialLoginInitTest() {
        mvc.perform(get("/auth/login/social/platform/google"))
                .andExpect(status().is3xxRedirection)
                .andDo(print())
    }

    @DisplayName("로그아웃 요청을 하면 성공적으로 응답이 오는 것을 확인")
    @Test
    @WithCustomMockUser
    fun authorizationTest() {
        mvc.perform(post("/auth/logout"))
                .andExpect(status().isOk)
                .andDo(print())
    }

}
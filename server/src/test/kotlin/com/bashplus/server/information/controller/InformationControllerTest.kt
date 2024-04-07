package com.bashplus.server.information.controller

import com.bashplus.server.host.domain.Conference
import com.bashplus.server.host.domain.ConferenceHost
import com.bashplus.server.host.domain.Host
import com.bashplus.server.host.repository.ConferenceHostRepository
import com.bashplus.server.host.repository.ConferenceRepository
import com.bashplus.server.host.repository.HostRepository
import com.bashplus.server.information.domain.Category
import com.bashplus.server.information.repository.CategoryRepository
import com.bashplus.server.setting.WithCustomMockUser
import org.junit.jupiter.api.*

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InformationControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    @Autowired
    private lateinit var hostRepository: HostRepository

    @Autowired
    private lateinit var conferenceRepository: ConferenceRepository

    @Autowired
    private lateinit var conferenceHostRepository: ConferenceHostRepository

    private lateinit var conference: Conference
    private lateinit var category: Category
    private lateinit var host: Host

    @BeforeAll
    fun setUp() {
        category = categoryRepository.save(Category("spring", 1))
        host = hostRepository.save(Host("company", "channel"))
        conference = conferenceRepository.save(Conference("test1", "content", LocalDate.now(), LocalDate.now()))
        conferenceHostRepository.save(ConferenceHost(conference, host))
    }

    @AfterAll
    fun tearDown() {
        conferenceHostRepository.deleteAll()
        conferenceRepository.deleteAll()
        hostRepository.deleteAll()
        categoryRepository.deleteAll()
    }

    @DisplayName("카테고리 리스트 요청에 대한 정상 응답 테스트")
    @Test
    @WithCustomMockUser
    fun categoryListTest() {
        mvc.perform(get("/information/category")
                .header("Authorization", "Bearer "))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.message[0].category").value("spring"))
                .andExpect(jsonPath("$.message[0].level").value(1))
                .andDo(print())
    }

    @DisplayName("컨퍼런스 주최 목록 요청에 대한 정상 응답 테스트")
    @Test
    @WithCustomMockUser
    fun conferenceHostListTest() {
        mvc.perform(get("/information/conference/host")
                .header("Authorization", "Bearer "))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.message[0].name").value("company"))
                .andExpect(jsonPath("$.message[0].channel").value("channel"))
                .andDo(print())
    }

}
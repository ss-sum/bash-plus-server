package com.bashplus.server.information.controller

import com.bashplus.server.host.domain.Conference
import com.bashplus.server.host.domain.ConferenceHost
import com.bashplus.server.host.domain.Host
import com.bashplus.server.host.repository.ConferenceHostRepository
import com.bashplus.server.host.repository.ConferenceRepository
import com.bashplus.server.host.repository.HostRepository
import com.bashplus.server.information.domain.Category
import com.bashplus.server.information.repository.CategoryRepository
import com.bashplus.server.video.domain.Video
import com.bashplus.server.video.repository.VideoRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
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
class SearchControllerTest {

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

    @Autowired
    private lateinit var videoRepository: VideoRepository
    private lateinit var conference: Conference
    private lateinit var category: Category
    private lateinit var host: Host
    private lateinit var video: Video

    @BeforeAll
    fun setUp() {
        category = categoryRepository.save(Category("spring", 1))
        host = hostRepository.save(Host("company", "channel"))
        conference = conferenceRepository.save(Conference("test1", "content", LocalDate.now(), LocalDate.now()))
        conferenceHostRepository.save(ConferenceHost(conference, host))
        video = videoRepository.save(Video(conference, "url", "test1", "content"))
    }

    @AfterAll
    fun tearDown() {
        conferenceHostRepository.deleteAll()
        videoRepository.deleteAll()
        conferenceRepository.deleteAll()
        hostRepository.deleteAll()
        categoryRepository.deleteAll()
    }

    @DisplayName("전체 영상 목록 조회 api가 정상적으로 응답하는지에 대한 테스트")
    @Test
    fun searchAllVideoTest() {
        mvc.perform(get("/search/video")
                .header("Authorization", "Bearer "))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.message[0].title").value("test1"))
                .andDo(print())
    }

    @DisplayName("컨퍼런스 주최 검색 api가 정상적으로 응답하는지에 대한 테스트")
    @ParameterizedTest
    @ValueSource(strings = ["company", "comp", "c", "Company", "ComPany"])
    fun searchConferenceHostTest(text: String) {
        mvc.perform(get("/search/conference/host/{$text}")
                .param("text", text)
                .header("Authorization", "Bearer "))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.message[0].name").value(host.company))
                .andExpect(jsonPath("$.message[0].channel").value(host.channel))
                .andDo(print())
    }

    @DisplayName("카테고리 검색 api가 정상적으로 응답하는지에 대한 테스트")
    @ParameterizedTest
    @ValueSource(strings = ["spring", "sp", "s", "Spring", "SprIng"])
    fun searchCategoryTest(text: String) {
        mvc.perform(get("/search/category/{$text}")
                .param("text", text)
                .header("Authorization", "Bearer "))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.message[0].category").value(category.category))
                .andExpect(jsonPath("$.message[0].level").value(category.level))
                .andDo(print())
    }

    @DisplayName("영상 검색 api가 정상적으로 응답하는지에 대한 테스트")
    @ParameterizedTest
    @ValueSource(strings = ["test1", "test", "t", "Test1", "TesT1"])
    fun searchVideoTest(text: String) {
        mvc.perform(get("/search/video/{$text}")
                .param("text", text)
                .header("Authorization", "Bearer "))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.message[0].title").value(video.title))
                .andExpect(jsonPath("$.message[0].content").value(video.content))
                .andDo(print())
    }

}
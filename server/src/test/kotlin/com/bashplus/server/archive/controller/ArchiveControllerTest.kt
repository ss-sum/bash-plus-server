package com.bashplus.server.archive.controller

import com.bashplus.server.archive.domain.Archive
import com.bashplus.server.archive.repository.ArchiveRepository
import com.bashplus.server.host.domain.Conference
import com.bashplus.server.host.repository.ConferenceRepository
import com.bashplus.server.setting.WithCustomMockUser
import com.bashplus.server.users.domain.Users
import com.bashplus.server.users.repository.UsersRepository
import com.bashplus.server.video.domain.Video
import com.bashplus.server.video.repository.VideoRepository
import com.fasterxml.jackson.databind.ObjectMapper
import junit.framework.TestCase
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ArchiveControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var usersRepository: UsersRepository

    @Autowired
    private lateinit var conferenceRepository: ConferenceRepository

    @Autowired
    private lateinit var videoRepository: VideoRepository

    @Autowired
    private lateinit var archiveRepository: ArchiveRepository
    private lateinit var conference: Conference
    private lateinit var video: Video
    private lateinit var user: Users

    @BeforeAll
    fun setUp() {
        conference = conferenceRepository.save(Conference("title", "content", LocalDate.now(), LocalDate.now()))
        video = videoRepository.save(Video(conference, "url", "title", "content"))
        user = usersRepository.save(Users("user", "google", "user"))
    }

    @AfterEach
    fun init() {
        archiveRepository.deleteAll()
    }

    @AfterAll
    fun tearDown() {
        archiveRepository.deleteAll()
        videoRepository.deleteAll()
        conferenceRepository.deleteAll()
        usersRepository.deleteAll()
    }

    @DisplayName("사용자 uid로 영상 시청 기록 목록 조회 테스트")
    @Test
    @WithCustomMockUser
    fun findByUserUidAndVideoVidSuccessTest() {
        archiveRepository.save(Archive(user, video))
        mvc.perform(MockMvcRequestBuilders.get("/archive/videos")
                .header("Authorization", "Bearer "))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.message[0].vid").value(video.vid))
                .andDo(MockMvcResultHandlers.print())
    }

    @DisplayName("사용자 uid로 영상 시청 기록 목록 조회 실패 테스트 - uid")
    @ParameterizedTest
    @ValueSource(strings = ["abc", "483901"])
    @WithCustomMockUser
    fun findByUserUidAndVideoVidFailureByUidTest(text: String) {
        mvc.perform(MockMvcRequestBuilders.get("/archive/videos")
                .header("Authorization", "Bearer ${text}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andDo(MockMvcResultHandlers.print())
    }

    @DisplayName("나즁에 볼 영상 등록 성공 테스트 - 기존 시청 기록이 있는 경우")
    @Test
    @WithCustomMockUser
    fun saveSuccessAlreadyArchiveTest() {
        archiveRepository.save(Archive(user, video))
        val request: MutableMap<String, Any> = HashMap()
        val objectMapper: ObjectMapper = ObjectMapper()
        request.put("vid", video.vid!!)
        mvc.perform(MockMvcRequestBuilders.post("/archive/last/video")
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", "Bearer ")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Ok"))
                .andDo(MockMvcResultHandlers.print())
    }

    @DisplayName("나즁에 볼 영상 등록 성공 테스트 - 기존 시청 기록이 없는 경우")
    @Test
    @WithCustomMockUser
    fun saveSuccessNoArchiveTest() {
        val request: MutableMap<String, Any> = HashMap()
        val objectMapper: ObjectMapper = ObjectMapper()
        request.put("vid", video.vid!!)
        mvc.perform(MockMvcRequestBuilders.post("/archive/last/video")
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", "Bearer ")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Ok"))
                .andDo(MockMvcResultHandlers.print())
    }

    @DisplayName("나즁에 볼 영상 등록 실패 테스트 - vid")
    @ParameterizedTest
    @ValueSource(strings = ["abc", "483901"])
    @WithCustomMockUser
    fun saveFailureByVidTest(text: String) {
        val request: MutableMap<String, Any> = HashMap()
        val objectMapper: ObjectMapper = ObjectMapper()
        request.put("vid", text)
        mvc.perform(MockMvcRequestBuilders.post("/archive/last/video")
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", "Bearer ")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andDo(MockMvcResultHandlers.print())
    }

    @DisplayName("나즁에 볼 영상 등록 실패 테스트 - uid")
    @ParameterizedTest
    @ValueSource(strings = ["abc", "483901"])
    @WithCustomMockUser
    fun saveFailureByUidTest(text: String) {
        val request: MutableMap<String, Any> = HashMap()
        val objectMapper: ObjectMapper = ObjectMapper()
        request.put("vid", video.vid!!)
        mvc.perform(MockMvcRequestBuilders.post("/archive/last/video")
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", "Bearer ${text}")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andDo(MockMvcResultHandlers.print())
    }

    @DisplayName("나중에 볼 영상 목록 조회 성공 테스트")
    @Test
    @WithCustomMockUser
    fun findByUserUidAndLastIsTrueSuccessTest() {
        archiveRepository.save(Archive(user, video, last = true))
        mvc.perform(MockMvcRequestBuilders.get("/archive/last/videos")
                .header("Authorization", "Bearer "))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.message[0].vid").value(video.vid))
                .andDo(MockMvcResultHandlers.print())
    }

    @DisplayName("나중에 볼 영상 목록 조회 실패 테스트 - uid")
    @ParameterizedTest
    @ValueSource(strings = ["abc", "483901"])
    @WithCustomMockUser
    fun findByUserUidAndLastIsTrueFailureByUidTest(text: String) {
        mvc.perform(MockMvcRequestBuilders.get("/archive/last/videos")
                .header("Authorization", "Bearer ${text}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andDo(MockMvcResultHandlers.print())
    }

    @DisplayName("좋아요한 영상 목록 조회 성공 테스트")
    @Test
    @WithCustomMockUser
    fun findByUserUidAndLikesIsTrueSuccessTest() {
        archiveRepository.save(Archive(user, video, like = true))
        mvc.perform(MockMvcRequestBuilders.get("/archive/like/videos")
                .header("Authorization", "Bearer "))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.message[0].vid").value(video.vid))
                .andDo(MockMvcResultHandlers.print())
    }

    @DisplayName("좋아요한 영상 목록 조회 실패 테스트 - uid")
    @ParameterizedTest
    @ValueSource(strings = ["abc", "483901"])
    @WithCustomMockUser
    fun findByUserUidAndLikesIsTrueFailureByUidTest(text: String) {
        mvc.perform(MockMvcRequestBuilders.get("/archive/like/videos")
                .header("Authorization", "Bearer ${text}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andDo(MockMvcResultHandlers.print())
    }

    @DisplayName("좋아요한 영상 등록 성공 테스트 - 시청기록 존재하는 경우")
    @Test
    @WithCustomMockUser
    fun saveLikeSuccessAlreadyArchiveTest() {
        archiveRepository.save(Archive(user, video, like = false))
        val request: MutableMap<String, Any> = HashMap()
        val objectMapper: ObjectMapper = ObjectMapper()
        request.put("vid", video.vid!!)
        mvc.perform(MockMvcRequestBuilders.post("/archive/like/video")
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", "Bearer ")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Ok"))
                .andDo(MockMvcResultHandlers.print())
        assert(archiveRepository.findByUserUidAndVideoVid(user.uid!!, video.vid!!).get().likes)
    }

    @DisplayName("좋아요한 영상 등록 성공 테스트 - 시청기록 존재하지않는 경우")
    @Test
    @WithCustomMockUser
    fun saveLikeSuccessNoArchiveTest() {
        val request: MutableMap<String, Any> = HashMap()
        val objectMapper: ObjectMapper = ObjectMapper()
        request.put("vid", video.vid!!)
        mvc.perform(MockMvcRequestBuilders.post("/archive/like/video")
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", "Bearer ")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Ok"))
                .andDo(MockMvcResultHandlers.print())
        assert(archiveRepository.findByUserUidAndVideoVid(user.uid!!, video.vid!!).get().likes)
    }

    @DisplayName("좋아요한 영상 등록 실패 테스트 - vid")
    @ParameterizedTest
    @ValueSource(strings = ["abc", "483901"])
    @WithCustomMockUser
    fun saveLikeFailureByVidTest(text: String) {
        val request: MutableMap<String, Any> = HashMap()
        val objectMapper: ObjectMapper = ObjectMapper()
        request.put("vid", text)
        mvc.perform(MockMvcRequestBuilders.post("/archive/like/video")
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", "Bearer ")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andDo(MockMvcResultHandlers.print())
    }

    @DisplayName("좋아요한 영상 등록 실패 테스트 - uid")
    @ParameterizedTest
    @ValueSource(strings = ["abc", "483901"])
    @WithCustomMockUser
    fun saveLikeFailureByUidAlreadyArchiveTest(text: String) {
        archiveRepository.save(Archive(user, video, like = false))
        val request: MutableMap<String, Any> = HashMap()
        val objectMapper: ObjectMapper = ObjectMapper()
        request.put("vid", video.vid!!)
        mvc.perform(MockMvcRequestBuilders.post("/archive/like/video")
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", "Bearer ${text}")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andDo(MockMvcResultHandlers.print())
        TestCase.assertFalse(archiveRepository.findByUserUidAndVideoVid(user.uid!!, video.vid!!).get().likes)
    }

    @DisplayName("좋아요한 영상 등록 실패 테스트 - uid")
    @ParameterizedTest
    @ValueSource(strings = ["abc", "483901"])
    @WithCustomMockUser
    fun saveLikeFailureByUidNoArchiveTest(text: String) {
        val request: MutableMap<String, Any> = HashMap()
        val objectMapper: ObjectMapper = ObjectMapper()
        request.put("vid", video.vid!!)
        mvc.perform(MockMvcRequestBuilders.post("/archive/like/video")
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", "Bearer ${text}")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andDo(MockMvcResultHandlers.print())
        assert(archiveRepository.findByUserUidAndVideoVid(user.uid!!, video.vid!!).isEmpty())
    }

}
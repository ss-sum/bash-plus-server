package com.bashplus.server.video.controller

import com.bashplus.server.archive.repository.ArchiveRepository
import com.bashplus.server.host.domain.Conference
import com.bashplus.server.host.repository.ConferenceRepository
import com.bashplus.server.setting.WithCustomMockUser
import com.bashplus.server.users.domain.Users
import com.bashplus.server.users.repository.UsersRepository
import com.bashplus.server.video.domain.Comment
import com.bashplus.server.video.domain.Video
import com.bashplus.server.video.repository.CommentRepository
import com.bashplus.server.video.repository.VideoRepository
import com.bashplus.server.video.repository.VideoTagRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate
import java.time.LocalTime

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class VideoControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var videoRepository: VideoRepository

    @Autowired
    private lateinit var videoTagRepository: VideoTagRepository

    @Autowired
    private lateinit var commentRepository: CommentRepository

    @Autowired
    private lateinit var conferenceRepository: ConferenceRepository

    @Autowired
    private lateinit var usersRepository: UsersRepository

    @Autowired
    private lateinit var archiveRepository: ArchiveRepository

    private lateinit var user: Users
    private lateinit var video: Video
    private lateinit var conference: Conference

    @BeforeAll
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        user = usersRepository.save(Users("user", "google", "user"))
        conference = conferenceRepository.save(Conference("test1", "content", LocalDate.now(), LocalDate.now()))
        video = videoRepository.save(Video(conference, "url", "test1", null))
        commentRepository.save(Comment(user, video, "test"))
    }

    @AfterAll
    fun tearDown() {
        archiveRepository.deleteAll()
        commentRepository.deleteAll()
        usersRepository.deleteAll()
        videoRepository.deleteAll()
        conferenceRepository.deleteAll()
    }

    @DisplayName("영상 정보를 가져오는 api 정상 응답테스트")
    @Test
    @WithCustomMockUser
    fun getVideoSuccessTest() {
        mvc.perform(get("/video/${video.vid}")
                .header("Authorization", "Bearer "))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.message.title").value("test1"))
                .andExpect(jsonPath("$.message.content").value("content"))
                .andDo(print())
    }

    @DisplayName("영상 정보를 가져오는 api 정상 오류 응답 테스트 - vid")
    @ParameterizedTest
    @ValueSource(strings = ["-1", "2", "lskf"])
    @WithCustomMockUser
    fun getVideoFailTest(vid: String) {
        mvc.perform(get("/video/$vid")
                .header("Authorization", "Bearer "))
                .andExpect(status().isBadRequest)
                .andDo(print())
    }

    @DisplayName("영상 댓글 정보를 가져오는 api 정상 응답테스트")
    @Test
    @WithCustomMockUser
    fun getVideoCommentSuccessTest() {
        mvc.perform(get("/video/${video.vid}/comments")
                .header("Authorization", "Bearer "))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.message[0].content").value("test"))
                .andDo(print())
    }

    @DisplayName("영상 댓글 정보를 가져오는 api 정상 오류 응답 테스트 - vid")
    @ParameterizedTest
    @ValueSource(strings = ["-1", "2", "lskf"])
    @WithCustomMockUser
    fun getVideoCommentFailTest(vid: String) {
        mvc.perform(get("/video/${vid}/comments")
                .header("Authorization", "Bearer "))
                .andExpect(status().isBadRequest)
                .andDo(print())
    }

    @DisplayName("영상 댓글 작성 api 정상 응답테스트")
    @Test
    @WithCustomMockUser
    fun writeCommentSuccessTest() {
        val objectMapper: ObjectMapper = ObjectMapper()
        val request: MutableMap<String, String> = HashMap()
        request["content"] = "test"
        mvc.perform(post("/video/${video.vid}/comment")
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", "Bearer ")
                .contentType("application/json"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.message[0].content").value("test"))
                .andDo(print())
    }

    @DisplayName("영상 댓글 작성 api 정상 오류 응답 테스트 - vid")
    @ParameterizedTest
    @ValueSource(strings = ["-1", "2", "lskf"])
    @WithCustomMockUser
    fun writeCommentFailTest(vid: String) {
        val objectMapper: ObjectMapper = ObjectMapper()
        val request: MutableMap<String, String> = HashMap()
        request["content"] = "test"
        mvc.perform(post("/video/${vid}/comment")
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", "Bearer ")
                .contentType("application/json"))
                .andExpect(status().isBadRequest)
                .andDo(print())
    }

    @DisplayName("영상 시청 기록 업데이트 api 정상 응답테스트")
    @Test
    @WithCustomMockUser
    fun updateWatchRecordSuccessTest() {
        val objectMapper: ObjectMapper = ObjectMapper()
        val request: MutableMap<String, String> = HashMap()
        request["time"] = "10:00:00"
        mvc.perform(post("/video/${video.vid}/watch")
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", "Bearer ")
                .contentType("application/json"))
                .andExpect(status().isOk)
                .andDo(print())
        val result = archiveRepository.findByUserUidAndVideoVid(user.uid!!, video.vid!!)
        assert(result.isPresent && result.get().time == LocalTime.of(0, 0, 10))
    }

    @DisplayName("영상 시청 기록 업데이트 api 정상 오류 응답 테스트 - vid")
    @ParameterizedTest
    @ValueSource(strings = ["-1", "2", "lskf"])
    @WithCustomMockUser
    fun updateWatchRecordFailTest(vid: String) {
        val objectMapper: ObjectMapper = ObjectMapper()
        val request: MutableMap<String, String> = HashMap()
        request["time"] = "10:00:00"
        mvc.perform(post("/video/${vid}/watch")
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", "Bearer ")
                .contentType("application/json"))
                .andExpect(status().isBadRequest)
                .andDo(print())
    }
}
package com.bashplus.server.users.controller

import com.bashplus.server.host.domain.Conference
import com.bashplus.server.host.repository.ConferenceRepository
import com.bashplus.server.information.domain.Category
import com.bashplus.server.information.repository.CategoryRepository
import com.bashplus.server.setting.WithCustomMockUser
import com.bashplus.server.users.domain.Interest
import com.bashplus.server.users.domain.Users
import com.bashplus.server.users.dto.InterestRequestDTO
import com.bashplus.server.users.repository.InterestRepository
import com.bashplus.server.users.repository.UsersRepository
import com.bashplus.server.video.domain.Comment
import com.bashplus.server.video.domain.Video
import com.bashplus.server.video.repository.CommentRepository
import com.bashplus.server.video.repository.VideoRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UserControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var usersRepository: UsersRepository

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    @Autowired
    private lateinit var interestRepository: InterestRepository

    @Autowired
    private lateinit var commentRepository: CommentRepository

    @Autowired
    private lateinit var videoRepository: VideoRepository

    @Autowired
    private lateinit var conferenceRepository: ConferenceRepository
    private lateinit var user: Users
    private lateinit var user2: Users

    @BeforeAll
    fun setUp() {
        user = usersRepository.save(Users("user", "google", "user"))
        user2 = usersRepository.save(Users("user2", "google", "user2"))
        val category: Category = categoryRepository.save(Category("spring", 1))
        interestRepository.save(Interest(user, category))
        val conference: Conference = conferenceRepository.save(Conference("test1", null, LocalDate.now(), LocalDate.now()))
        val video: Video = videoRepository.save(Video(conference, "url", "test1", null))
        commentRepository.save(Comment(user, video, "test"))
    }

    @AfterAll
    fun tearDown() {
        commentRepository.deleteAll()
        videoRepository.deleteAll()
        conferenceRepository.deleteAll()
        interestRepository.deleteAll()
        usersRepository.deleteAll()
        categoryRepository.deleteAll()
    }

    @DisplayName("유저의 관심 카테고리를 설정하는 api가 정상적으로 응답하는지에 대한 테스트")
    @Test
    @WithCustomMockUser
    fun setInterestingCategorySuccessTest() {
        val objectMapper: ObjectMapper = ObjectMapper()
        val request: MutableMap<String, ArrayList<InterestRequestDTO>> = HashMap()
        request["param"] = mutableListOf((InterestRequestDTO("spring", 1))) as ArrayList<InterestRequestDTO>
        mvc.perform(post("/user/${user.uid}/interesting")
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", "Bearer ")
                .contentType("application/json"))
                .andExpect(status().isOk)
                .andDo(print())
    }

    @DisplayName("유저의 관심 카테고리를 설정하는 api가 정상적으로 오류를 응답하는지에 대한 테스트 - 카테고리 관련 오류")
    @ParameterizedTest
    @ValueSource(strings = ["spring:2", "springu:1"])
    @WithCustomMockUser
    fun setInterestingCategoryFailureByCategoryTest(text: String) {
        val objectMapper: ObjectMapper = ObjectMapper()
        val request: MutableMap<String, ArrayList<InterestRequestDTO>> = HashMap()
        val inputs = text.split(":")
        request["param"] = mutableListOf((InterestRequestDTO(inputs.get(0), Integer.parseInt(inputs.get(1))))) as ArrayList<InterestRequestDTO>
        mvc.perform(post("/user/${user.uid}/interesting")
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", "Bearer ")
                .contentType("application/json"))
                .andExpect(status().isBadRequest)
                .andDo(print())
    }

    @DisplayName("유저의 관심 카테고리를 설정하는 api가 정상적으로 오류를 응답하는지에 대한 테스트 - 유저 관련 오류")
    @ParameterizedTest
    @ValueSource(strings = ["3", "none"])
    @WithCustomMockUser
    fun setInterestingCategoryFailureByUserTest(text: String) {
        val objectMapper: ObjectMapper = ObjectMapper()
        val request: MutableMap<String, ArrayList<InterestRequestDTO>> = HashMap()
        request["param"] = mutableListOf((InterestRequestDTO("spring", 1))) as ArrayList<InterestRequestDTO>
        mvc.perform(post("/user/${text}/interesting")
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", "Bearer ")
                .contentType("application/json"))
                .andExpect(status().isBadRequest)
                .andDo(print())
    }

    @DisplayName("유저의 댓글을 가져오는 api가 정상적으로 응답하는지에 대한 테스트")
    @Test
    @WithCustomMockUser
    fun getCommentsTest() {
        mvc.perform(get("/user/${user.uid}/comments")
                .header("Authorization", "Bearer "))
                .andExpect(status().isOk)
                .andDo(print())
        mvc.perform(get("/user/${user2.uid}/comments")
                .header("Authorization", "Bearer "))
                .andExpect(status().isOk)
                .andDo(print())
    }

    @DisplayName("유저의 댓글을 가져오는 api가 정상적으로 응답하는지에 대한 테스트- 유저 관련 오류")
    @ParameterizedTest
    @ValueSource(strings = ["3", "none"])
    @WithCustomMockUser
    fun getCommentsFailureByUserTest(text: String) {
        mvc.perform(get("/user/${text}/comments")
                .header("Authorization", "Bearer "))
                .andExpect(status().isBadRequest)
                .andDo(print())
    }


}
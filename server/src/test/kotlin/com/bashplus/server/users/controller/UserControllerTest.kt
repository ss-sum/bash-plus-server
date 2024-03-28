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
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
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

    @BeforeAll
    fun setUp() {
        user = usersRepository.save(Users("user", "google", "user"))
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

    @Test
    @WithCustomMockUser
    fun setInterestingCategoryTest() {
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

    @Test
    @WithCustomMockUser
    fun getCommentsTest() {
        mvc.perform(get("/user/${user.uid}/comments")
                .header("Authorization", "Bearer "))
                .andExpect(status().isOk)
                .andDo(print())
    }

}
package com.bashplus.server.users.service

import com.bashplus.server.host.domain.Conference
import com.bashplus.server.host.repository.ConferenceRepository
import com.bashplus.server.information.domain.Category
import com.bashplus.server.information.repository.CategoryRepository
import com.bashplus.server.users.domain.Interest
import com.bashplus.server.users.domain.Users
import com.bashplus.server.users.dto.InterestRequestDTO
import com.bashplus.server.users.repository.InterestRepository
import com.bashplus.server.users.repository.UsersRepository
import com.bashplus.server.video.domain.Comment
import com.bashplus.server.video.domain.Video
import com.bashplus.server.video.repository.CommentRepository
import com.bashplus.server.video.repository.VideoRepository
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UserServiceTest {
    @Autowired
    private lateinit var userService: UserService

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
        MockitoAnnotations.openMocks(this)
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
    fun setInterestingCategory() {
        val user = usersRepository.findByUid(user.uid!!)
        if (user.isPresent) {
            val request = arrayListOf(InterestRequestDTO("spring", 1))
            val category = categoryRepository.findByCategoryAndLevel("spring", 1)
            userService.setInterestingCategory(user.get().uid!!, request)
            val interest = interestRepository.findAllByUsersUid(user.get().uid!!)
            assertEquals(category.get().category, interest.get(0).category.category)
            assertEquals(category.get().level, interest.get(0).category.level)
        }
    }

    @Test
    fun getComments() {
        val comments = userService.getComments(user.uid!!)
        assertEquals(1, comments.size)
        assertEquals("test", comments[0].content)
    }
}
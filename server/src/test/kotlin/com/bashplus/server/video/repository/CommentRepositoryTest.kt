package com.bashplus.server.video.repository

import com.bashplus.server.host.domain.Conference
import com.bashplus.server.host.repository.ConferenceRepository
import com.bashplus.server.users.domain.Users
import com.bashplus.server.users.repository.UsersRepository
import com.bashplus.server.video.domain.Comment
import com.bashplus.server.video.domain.Video
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CommentRepositoryTest {
    @Autowired
    private lateinit var commentRepository: CommentRepository

    @Autowired
    private lateinit var videoRepository: VideoRepository

    @Autowired
    private lateinit var usersRepository: UsersRepository

    @Autowired
    private lateinit var conferenceRepository: ConferenceRepository

    private lateinit var user: Users
    private lateinit var conference: Conference
    private lateinit var video: Video

    @BeforeAll
    fun setUp() {
        user = usersRepository.save(Users("user", "google", "user"))
        conference = conferenceRepository.save(Conference("title", "description", LocalDate.now(), LocalDate.now()))
        video = videoRepository.save(Video(conference, "description", "url", "thumbnail"))
        commentRepository.save(Comment(user, video, "comment"))
    }

    @DisplayName("존재하는 댓글 정보(uid)값에 대한 정상 응답 테스트")
    @Test
    fun findByUidSuccessTest() {
        var result = commentRepository.findAllByUserUid(user.uid!!)
        assert(result.size == 1)
        assert(result.get(0).content == "comment")
    }

    @DisplayName("존재하지 않는 댓글 정보(uid)값에 대한 반환값 테스트")
    @ParameterizedTest
    @ValueSource(longs = [2])
    fun findByUidFailureTest(uid: Long) {
        var result = commentRepository.findAllByUserUid(uid)
        assert(result.isEmpty())
    }

    @DisplayName("존재하는 댓글 정보(vid)값에 대한 정상 응답 테스트")
    @Test
    fun findByVidSuccessTest() {
        var result = commentRepository.findAllByVideoVid(video.vid!!)
        assert(result.size == 1)
        assert(result.get(0).content == "comment")
    }

    @DisplayName("존재하지 않는 댓글 정보(vid)값에 대한 반환값 테스트")
    @ParameterizedTest
    @ValueSource(longs = [2])
    fun findByVidFailureTest(vid: Long) {
        var result = commentRepository.findAllByVideoVid(vid)
        assert(result.isEmpty())
    }
}
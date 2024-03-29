package com.bashplus.server.users.repository

import com.bashplus.server.users.domain.Users
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsersRepositoryTest {
    @Autowired
    private lateinit var usersRepository: UsersRepository

    private lateinit var user: Users

    @BeforeAll
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        user = usersRepository.save(Users("user", "google", "user"))
    }

    @DisplayName("존재하는 회원 정보(id, type)값에 대한 정상 응답 테스트")
    @Test
    fun findByIdAndTypeSuccessTest() {
        var result = usersRepository.findByIdAndType("user", "google")
        assert(result.isPresent)
    }

    @DisplayName("존재하지 않는 회원 정보(id, type)값에 대한 반환값 테스트")
    @ParameterizedTest
    @ValueSource(strings = ["user2:google", "user:kakao"])
    fun findByIdAndTypeFailureTest(text: String) {
        val inputs = text.split(":")
        var result = usersRepository.findByIdAndType(inputs.get(0), inputs.get(1))
        assert(result.isEmpty)
    }

    @DisplayName("존재하는 회원 정보(uid)값에 대한 반환값 테스트")
    @Test
    fun findByUidSuccessTest() {
        var result = usersRepository.findByUid(user.uid!!)
        assert(result.isPresent)
    }

    @DisplayName("존재하지 않는 회원 정보(uid)값에 대한 반환값 테스트")
    @ParameterizedTest
    @ValueSource(longs = [2])
    fun findByFailureTest(input: Long) {
        var result = usersRepository.findByUid(input)
        assert(result.isEmpty)
    }

    @DisplayName("존재하는 회원 정보값에 대한 token 최초 생성 테스트")
    @Test
    fun updateTokenFirstSuccessTest() {
        usersRepository.updateToken(user.name, user.type, "token", "token")
        var result = usersRepository.findByUid(user.uid!!)
        assert(result.get().access.equals("token") && result.get().refresh.equals("token"))
    }

    @DisplayName("존재하는 회원 정보값에 대한 token 업데이트 테스트")
    @Test
    fun updateTokenDuplicateSuccessTest() {
        val tokens: List<String> = listOf("token1", "token2", "token3")
        for (token in tokens) {
            usersRepository.updateToken(user.name, user.type, token, token)
        }

        var result = usersRepository.findByUid(user.uid!!)
        assert(result.get().access.equals(tokens.last()) && result.get().refresh.equals(tokens.last()))
    }
}
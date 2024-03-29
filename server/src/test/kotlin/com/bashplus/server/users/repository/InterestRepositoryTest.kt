package com.bashplus.server.users.repository

import com.bashplus.server.information.domain.Category
import com.bashplus.server.information.repository.CategoryRepository
import com.bashplus.server.users.domain.Interest
import com.bashplus.server.users.domain.Users
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InterestRepositoryTest {
    @Autowired
    private lateinit var interestRepository: InterestRepository

    @Autowired
    private lateinit var categoryRepository: CategoryRepository


    @Autowired
    private lateinit var usersRepository: UsersRepository
    private lateinit var user: Users

    @BeforeAll
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        user = usersRepository.save(Users("user", "google", "user"))
        val category: Category = categoryRepository.save(Category("spring", 1))
        interestRepository.save(Interest(user, category))

    }

    @Test
    fun findAllByUsersUidTest() {
        val interests = interestRepository.findAllByUsersUid(user.uid!!)
        assert(interests.isNotEmpty())
        assert(interests.get(0).category.category.equals("spring") && interests.get(0).category.level == 1)
        assert(interests.get(0).users.uid!!.equals(user.uid!!))
    }
}
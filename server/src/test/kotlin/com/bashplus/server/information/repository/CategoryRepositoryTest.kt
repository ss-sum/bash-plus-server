package com.bashplus.server.information.repository

import com.bashplus.server.information.domain.Category
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

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryRepositoryTest {
    @Autowired
    private lateinit var categoryRepository: CategoryRepository
    private lateinit var category: Category

    @BeforeAll
    fun setUp() {
        category = categoryRepository.save(Category("spring", 1))
    }

    @DisplayName("존재하는 카테고리 이름, 레벨에 대한 반환값 테스트")
    @Test
    fun findByCategoryAndLevelSuccessTest() {
        val result = categoryRepository.findByCategoryAndLevel("spring", 1)
        assert(result.isPresent)
    }

    @DisplayName("존재하지 않는 카테고리 이름, 레벨에 대한 반환값 테스트")
    @ParameterizedTest
    @ValueSource(strings = ["spring:2", "spring2:1"])
    fun findByCategoryAndLevelFailureTest(inputs: String) {
        val input = inputs.split(":")
        val result = categoryRepository.findByCategoryAndLevel(input.get(0), Integer.parseInt(input.get(1)))
        assert(result.isEmpty)
    }

    @DisplayName("존재하는 카테고리 이름에 대한 반환값 테스트")
    @ParameterizedTest
    @ValueSource(strings = ["spring", "spr", "Spr", "SpRing", "s"])
    fun findByCategorySuccessTest(text: String) {
        val result = categoryRepository.findAllByCategoryIsLike(text)
        assert(result.size == 1)
        assert(result.get(0).equals(category))
    }

    @DisplayName("존재하지 않는 카테고리 이름에 대한 반환값 테스트")
    @ParameterizedTest
    @ValueSource(strings = ["spring2", "spr1", "Spru", "SpRin5", "s2"])
    fun findByCategoryFailureTest(text: String) {
        val result = categoryRepository.findAllByCategoryIsLike(text)
        assert(result.isEmpty())
    }
}
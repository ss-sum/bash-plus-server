package com.bashplus.server.information.repository

import com.bashplus.server.information.domain.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
open interface CategoryRepository : JpaRepository<Category, String> {
    fun findByCategoryAndLevel(category: String, level: Int): Optional<Category>
    open fun findAllByCategoryIsLike(category: String): List<Category>
}
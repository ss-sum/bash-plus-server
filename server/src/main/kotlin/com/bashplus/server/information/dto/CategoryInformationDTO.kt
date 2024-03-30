package com.bashplus.server.information.dto

import com.bashplus.server.information.domain.Category

class CategoryInformationDTO private constructor(val tid: Long, val category: String, val level: Int) {
    constructor(category: Category) :
            this(
                    tid = category.tid ?: 0,
                    category = category.category,
                    level = category.level
            )
}
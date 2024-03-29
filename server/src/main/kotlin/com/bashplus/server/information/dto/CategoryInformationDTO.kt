package com.bashplus.server.information.dto

import com.bashplus.server.information.domain.Category

class CategoryInformationDTO private constructor(val tid: Long) {
    constructor(category: Category) :
            this(
                    tid = category.tid ?: 0
            )
}
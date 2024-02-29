package com.bashplus.server.users.service

import com.bashplus.server.information.repository.CategoryRepository
import com.bashplus.server.users.domain.Interest
import com.bashplus.server.users.dto.InterestRequestDTO
import com.bashplus.server.users.repository.InterestRepository
import com.bashplus.server.users.repository.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired
    private lateinit var usersRepository: UsersRepository

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    @Autowired
    private lateinit var interestRepository: InterestRepository

    fun setInterestingCategory(userId: Int, request: ArrayList<InterestRequestDTO>) {
        val user = usersRepository.findByUid(userId)
        if (user.isPresent) {
            for (interest in request) {
                val category = categoryRepository.findByCategoryAndLevel(interest.category, interest.level)
                if (category.isPresent) {
                    interestRepository.save(Interest(user.get(), category.get()))
                }
            }
        }
    }
}
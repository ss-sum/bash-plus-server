package com.bashplus.server.users.service

import com.bashplus.server.information.repository.CategoryRepository
import com.bashplus.server.users.domain.Interest
import com.bashplus.server.users.dto.InterestRequestDTO
import com.bashplus.server.users.repository.InterestRepository
import com.bashplus.server.users.repository.UsersRepository
import com.bashplus.server.video.dto.CommentDTO
import com.bashplus.server.video.repository.CommentRepository
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

    @Autowired
    private lateinit var commentRepository: CommentRepository

    fun setInterestingCategory(userId: Long, request: ArrayList<InterestRequestDTO>) {
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

    fun getComments(userId: Long): List<CommentDTO> {
        return commentRepository.findAllByUserUid(userId).map { comment -> CommentDTO(comment) }
    }
}
package com.bashplus.server.video.domain

import com.bashplus.server.users.domain.Users
import jakarta.persistence.*

@Entity
class CommentLike(
    @ManyToOne @JoinColumn(name = "cid") var comment: Comment,
    @ManyToOne @JoinColumn(name = "uid") var user: Users
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var cuid: Long? = null
}
package app.vercel.junyeong.freeboard.domain.repository

import app.vercel.junyeong.freeboard.domain.entity.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long> {
    fun findAll(specification: Specification<Post>, pageable: Pageable): Page<Post>
}

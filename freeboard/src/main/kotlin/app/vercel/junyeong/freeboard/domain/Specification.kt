package app.vercel.junyeong.freeboard.domain

import app.vercel.junyeong.freeboard.domain.entity.Post
import org.springframework.data.jpa.domain.Specification

object Specification {
    fun ofPostWithTitle(title: String): Specification<Post> {
        return Specification { root, _, criteriaBuilder ->
            criteriaBuilder.like(root.get("title"), "%$title%")
        }
    }
}

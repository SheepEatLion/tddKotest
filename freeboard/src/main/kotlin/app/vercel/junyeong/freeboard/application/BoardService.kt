package app.vercel.junyeong.freeboard.application

import app.vercel.junyeong.freeboard.domain.entity.Post
import app.vercel.junyeong.freeboard.domain.repository.PostRepository
import app.vercel.junyeong.freeboard.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class BoardService(
    private val postRepository: PostRepository,
) {
    fun getPost(id: Long): Post {
        return postRepository.findById(id).orElseThrow { throw NotFoundException() }
    }
}

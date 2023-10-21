package app.vercel.junyeong.freeboard.application

import app.vercel.junyeong.freeboard.domain.Specification
import app.vercel.junyeong.freeboard.domain.entity.Post
import app.vercel.junyeong.freeboard.domain.repository.PostRepository
import app.vercel.junyeong.freeboard.exception.NotFoundException
import app.vercel.junyeong.freeboard.presentation.data.SearchPostsRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class BoardService(
    private val postRepository: PostRepository,
) {
    fun getPost(id: Long): Post {
        return postRepository.findById(id).orElseThrow { throw NotFoundException() }
    }

    fun getPosts(searchPostsRequest: SearchPostsRequest): Page<Post> {
        val spec = Specification.ofPostWithTitle(title = searchPostsRequest.title)
        val pageable = PageRequest.of(searchPostsRequest.pageNumber, searchPostsRequest.pageSize)

        return postRepository.findAll(spec, pageable)
    }
}

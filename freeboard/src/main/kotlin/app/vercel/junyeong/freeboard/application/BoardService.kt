package app.vercel.junyeong.freeboard.application

import app.vercel.junyeong.freeboard.domain.Specification
import app.vercel.junyeong.freeboard.domain.entity.Post
import app.vercel.junyeong.freeboard.domain.repository.PostRepository
import app.vercel.junyeong.freeboard.exception.BadRequestException
import app.vercel.junyeong.freeboard.exception.NotFoundException
import app.vercel.junyeong.freeboard.presentation.data.CreatePostRequest
import app.vercel.junyeong.freeboard.presentation.data.SearchPostsRequest
import app.vercel.junyeong.freeboard.presentation.data.UpdatePostRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
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
            .also {
                if (it.totalElements == 0L) throw NotFoundException()
            }
    }

    @Transactional
    fun create(createPostRequest: CreatePostRequest): Post {
        return postRepository.save(Post(createPostRequest))
    }

    @Transactional
    fun update(updatePostRequest: UpdatePostRequest): Post {
        val post = postRepository.findByIdOrNull(updatePostRequest.id) ?: throw NotFoundException()

        if (isSameTitleAndContents(post, updatePostRequest.title, updatePostRequest.contents)) {
            throw BadRequestException()
        }
        post.updateTitleAndContents(title = updatePostRequest.title, contents = updatePostRequest.contents)

        return postRepository.save(post)
    }

    private fun isSameTitleAndContents(post: Post, title: String, contents: String): Boolean {
        return post.title == title && post.contents == contents
    }
}

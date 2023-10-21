package app.vercel.junyeong.freeboard.application

import app.vercel.junyeong.freeboard.domain.entity.Post
import app.vercel.junyeong.freeboard.domain.repository.PostRepository
import app.vercel.junyeong.freeboard.exception.NotFoundException
import app.vercel.junyeong.freeboard.presentation.data.CreatePostRequest
import app.vercel.junyeong.freeboard.presentation.data.SearchPostsRequest
import app.vercel.junyeong.freeboard.presentation.data.UpdatePostRequest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.web.client.HttpClientErrorException.BadRequest
import java.time.LocalDateTime.now

class BoardServiceTest(
    private val postRepository: PostRepository
) : BehaviorSpec() {
    private val boardService = BoardService(postRepository = postRepository)

    init {
        given("유저가 홈화면에 진입했을 때") {
            val post = Post(title = "test", contents = "normal-contents")

            `when`("글이 존재하지 않는다면") {
                then("404 예외가 발생한다.")
                val exception = shouldThrow<NotFoundException> {
                    boardService.getPosts(searchPostsRequest = SearchPostsRequest())
                }
                exception.shouldBe(NotFoundException())
            }

            postRepository.save(post)
            `when`("글이 하나라도 존재한다면") {
                val result = boardService.getPosts(searchPostsRequest = SearchPostsRequest()).totalElements

                then("글 목록이 조회된다.")
                result shouldNotBe 0
            }
        }

        given("유저가 글 등록 버튼을 눌렀을 때") {
            `when`("입력 조건을 모두 채웠다면") {
                val createPostRequest = CreatePostRequest("it's me", "junyeong.")
                then("글이 생성된다.")
                val post = boardService.create(createPostRequest = createPostRequest)

                post.contents.shouldBe("junyeong.")
            }
        }

        given("유저가 글 수정 버튼을 눌렀을 때") {
            val post = postRepository.save(
                Post(
                    title = "test",
                    contents = "normal-contents",
                    )
            )
            val updatePostRequest = UpdatePostRequest(post.id, post.title, "updated contents")

            `when`("내용 필드가 바뀌었다면") {
                boardService.update(updatePostRequest = updatePostRequest)

                then("글이 수정된다.")
                boardService.getPost(post.id).contents.shouldBe("updated contents")
            }

            `when`("내용 필드가 바뀌지 않았다면") {
                boardService.update(updatePostRequest = updatePostRequest)

                then("글이 수정되지 않는다.")
                boardService.getPost(post.id).updatedAt.shouldNotBe(now())
            }
        }
    }
}

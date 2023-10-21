package app.vercel.junyeong.freeboard.application

import app.vercel.junyeong.freeboard.domain.entity.Post
import app.vercel.junyeong.freeboard.domain.repository.PostRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.web.client.HttpClientErrorException.BadRequest
import org.springframework.web.client.HttpClientErrorException.NotFound
import java.time.LocalDateTime.now

class BoardServiceTest(
    private val postRepository: PostRepository
) : BehaviorSpec() {
    private val boardService = BoardService(repository = postRepository)

    init {
        given("유저가 홈화면에 진입했을 때") {
            `when`("글이 존재하지 않는다면") {
                then("404 예외가 발생한다.")
                val exception = shouldThrow<NotFound> {
                    boardService.getPosts()
                }
                exception.statusCode.shouldBe(404)
            }

            postRepository.save()
            `when`("글이 하나라도 존재한다면") {
                val result = boardService.getPosts().size

                then("글 목록이 조회된다.")
                result shouldNotBe 0
            }
        }

        given("유저가 글 등록 버튼을 눌렀을 때") {
            `when`("입력 조건을 모두 채웠다면") {
                val createPostRequest = CreatePostRequest()
                then("글이 생성된다.")
                val post = boardService.create(request = createPostRequest)

                post.shouldNotBeEmpty()
            }
            `when`("입력 조건이 하나라도 비었다면") {
                val createPostRequest = CreatePostRequest()
                then("400 예외를 발생한다.")
                shouldThrowExactly<BadRequest> {
                    boardService.create(request = createPostRequest)
                }
            }
        }

        given("유저가 글 수정 버튼을 눌렀을 때") {
            val post = postRepository.save()
            val updatePostRequest = UpdatePostRequest()

            `when`("내용 필드가 바뀌었다면") {
                boardService.update(request = updatePostRequest)

                then("글이 수정된다.")
                boardService.getPost() shouldBe post
            }

            `when`("내용 필드가 바뀌지 않았다면") {
                boardService.update(request = updatePostRequest)

                then("글이 수정되지 않는다.")
                boardService.getPost().updatedAt shoudNotBe now()
            }
        }
    }
}

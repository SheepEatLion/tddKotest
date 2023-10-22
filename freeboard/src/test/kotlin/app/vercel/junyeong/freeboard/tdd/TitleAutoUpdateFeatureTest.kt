package app.vercel.junyeong.freeboard.tdd

import app.vercel.junyeong.freeboard.FreeboardApplication
import app.vercel.junyeong.freeboard.application.BoardService
import app.vercel.junyeong.freeboard.domain.entity.Post
import app.vercel.junyeong.freeboard.domain.repository.PostRepository
import app.vercel.junyeong.freeboard.presentation.data.UpdatePostRequest
import io.kotest.core.spec.style.ExpectSpec
import io.kotest.matchers.shouldBe
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [FreeboardApplication::class])
class TitleAutoUpdateFeatureTest(
    private val postRepository: PostRepository
) : ExpectSpec() {
    private val boardService = BoardService(postRepository = postRepository)

    init {
        context("글이 수정될 때") {
            val post = postRepository.save(
                Post(
                    title = "test",
                    contents = "normal-contents",
                )
            )
            val updatePostRequest = UpdatePostRequest(post.id, post.title, "updated contents")

            boardService.update(updatePostRequest = updatePostRequest)
            expect("제목 앞에 re: 글자가 자동 생성 된다.") {
                val result = boardService.getPost(post.id).title

                result.take(3) shouldBe "re:"
            }
        }
    }
}

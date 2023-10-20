package app.vercel.junyeong.freeboard.tdd

import app.vercel.junyeong.freeboard.application.BoardService
import app.vercel.junyeong.freeboard.domain.repository.BoardRepository
import io.kotest.core.spec.style.ExpectSpec

class TitleAutoUpdateFeatureTest(
    private val boardRepository: BoardRepository
) : ExpectSpec() {
    private val boardService = BoardService(repository = boardRepository)

    init {
        context("글이 수정될 때") {
            boardService.update()
            expect("제목 앞에 re: 글자가 자동 생성 된다.") {
                val result = boardService.getPost().title

                result.take(3) shouldBe "re:"
            }
        }
    }
}

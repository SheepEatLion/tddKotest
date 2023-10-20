package app.vercel.junyeong.freeboard.application

import app.vercel.junyeong.freeboard.domain.repository.BoardRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class BoardServiceTest(
    private val boardRepository: BoardRepository
) : BehaviorSpec() {
    private val boardService = BoardService(repository = boardRepository)

    init {
        given("유저가 홈화면에 진입했을 때") {
            `when`("글이 하나라도 존재한다면") {
                then("글 목록이 조회된다.")
            }
            `when`("글이 존재하지 않는다면") {
                then("404 예외가 발생한다.")
            }
        }

        given("유저가 글 등록 버튼을 눌렀을 때") {
            `when`("입력 조건을 모두 채웠다면") {
                then("글이 생성된다.")
            }
            `when`("입력 조건이 하나라도 비었다면") {
                then("400 예외를 발생한다.")
            }
        }

        given("유저가 글 수정 버튼을 눌렀을 때") {
            `when`("내용 필드가 바뀌었다면") {
                then("글이 수정된다.")
            }
            `when`("내용 필드가 바뀌지 않았다면") {
                then("글이 수정되지 않는다.")
            }
        }
    }
}

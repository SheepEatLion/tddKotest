package app.vercel.junyeong.freeboard

import app.vercel.junyeong.freeboard.domain.entity.Post

object Mock {
    fun post() = Post(
        title = "test",
        contents = "normal-contents",
    )
}

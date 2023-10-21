package app.vercel.junyeong.freeboard.presentation.data

class SearchPostsRequest(
    val title: String = "",

    val pageNumber: Int = 1,

    val pageSize: Int = 10,
)

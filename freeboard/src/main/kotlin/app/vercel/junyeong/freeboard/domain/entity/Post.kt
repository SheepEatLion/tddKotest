package app.vercel.junyeong.freeboard.domain.entity

import app.vercel.junyeong.freeboard.presentation.data.CreatePostRequest
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.AUTO
import jakarta.persistence.Id
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import java.time.LocalDateTime.now

@Entity
class Post(
    @Id
    @GeneratedValue(strategy = AUTO)
    val id: Long = 0,

    @Column(length = 15)
    var title: String,

    var contents: String,

    @CreatedDate
    val createdAt: LocalDateTime = now(),

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null,
) {
    constructor(createPostRequest: CreatePostRequest): this(
        title = createPostRequest.title,
        contents = createPostRequest.contents,
    )

    fun updateTitleAndContents(title: String, contents: String) {
        this.title = "re:$title"
        this.contents = contents
    }
}

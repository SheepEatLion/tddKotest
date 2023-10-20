package app.vercel.junyeong.freeboard.domain.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.AUTO
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class Post(
    @Id
    @GeneratedValue(strategy = AUTO)
    val id: Long,

    val title: String,

    val contents: String,

    val createdAt: LocalDateTime,

    var updatedAt: LocalDateTime,
)

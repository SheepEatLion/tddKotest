package app.vercel.junyeong.freeboard.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.AUTO
import jakarta.persistence.Id
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
class Post(
    @Id
    @GeneratedValue(strategy = AUTO)
    val id: Long,

    @Column(length = 15)
    val title: String,

    val contents: String,

    @CreatedDate
    val createdAt: LocalDateTime,

    @LastModifiedDate
    var updatedAt: LocalDateTime,
)

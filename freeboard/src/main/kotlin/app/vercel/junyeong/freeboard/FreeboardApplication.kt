package app.vercel.junyeong.freeboard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FreeboardApplication

fun main(args: Array<String>) {
	runApplication<FreeboardApplication>(*args)
}

package restapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UsersApiApplication

fun main(args: Array<String>) {
    runApplication<UsersApiApplication>(*args)
}

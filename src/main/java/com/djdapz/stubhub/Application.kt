package com.djdapz.stubhub

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.TimeZone
import javax.annotation.PostConstruct


@SpringBootApplication
@EnableScheduling
class Application {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java)
        }

        @PostConstruct
        fun started() {
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
        }
    }
}
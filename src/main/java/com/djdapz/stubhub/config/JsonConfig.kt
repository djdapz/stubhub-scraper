package com.djdapz.stubhub.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JsonConfig {
    companion object {
        @JvmStatic
        @Bean
        fun objectMapper(): ObjectMapper {
            val objectMapper = ObjectMapper()
            objectMapper.registerModule(JavaTimeModule())
            objectMapper.registerModule(KotlinModule())
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            return objectMapper
        }

        fun <T> asString(item: T): String = objectMapper().writeValueAsString(item)

        fun <T> asObject(item: String, klass: Class<T>): T = objectMapper().readValue(item, klass)
    }
}

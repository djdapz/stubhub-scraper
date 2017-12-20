package com.djdapz.stubhub.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.RestTemplate

import java.util.ArrayList

//HttpRequest
//ClientHttpRequestExecution

@Configuration
class SecurityConfigImpl(@param:Value("\${stubhub.api.token}") private val token: String) : SecurityConfig {

    @Bean
    override fun getToken(): String {
        return token
    }

    @Bean
    fun restTemplate(): RestTemplate {
        val restTemplate = RestTemplate()

        val interceptors = ArrayList<ClientHttpRequestInterceptor>()
        interceptors.add(StubhubRequestInterceptor(token))

        restTemplate.interceptors = interceptors
        return restTemplate
    }
}

class StubhubRequestInterceptor(private val token: String) : ClientHttpRequestInterceptor{
    override fun intercept(request: HttpRequest, body: ByteArray, execution: ClientHttpRequestExecution): ClientHttpResponse {
        request.headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        return execution.execute(request, body)
    }
}



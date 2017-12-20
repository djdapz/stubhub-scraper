package com.djdapz.stubhub.util

import com.djdapz.stubhub.domain.StubhubListingResponse
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate

import org.mockito.Matchers.eq
import org.mockito.Mockito.verify
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE

object TestingUtil {

    fun verifyJsonGetList(
            restTemplate: RestTemplate,
            url: String,
            parameterizedTypeReference: ParameterizedTypeReference<*>
    ) {

        val headers = LinkedMultiValueMap<String, String>()
        headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE)


        verify(restTemplate).exchange(
                eq(url),
                eq(HttpMethod.GET),
                eq(HttpEntity<Any>(headers)),
                eq(parameterizedTypeReference)
        )
    }

    fun verifyJsonGetObject(
            restTemplate: RestTemplate,
            url: String,
            stubhubListingResponseClass: Class<StubhubListingResponse>
    ) {
        val headers = LinkedMultiValueMap<String, String>()
        headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE)

        verify(restTemplate).exchange(
                eq(url),
                eq(HttpMethod.GET),
                eq(HttpEntity<Any>(headers)),
                eq(stubhubListingResponseClass)
        )
    }
}

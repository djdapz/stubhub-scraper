package com.djdapz.stubhub.client

import com.djdapz.stubhub.config.PARAM_EVENT_ID
import com.djdapz.stubhub.config.PARAM_PAGE_START
import com.djdapz.stubhub.config.PARAM_ROWS
import com.djdapz.stubhub.config.UrlConfig
import com.djdapz.stubhub.domain.StubhubListingResponse
import com.djdapz.stubhub.util.*
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

class StubhubClientImplTest {

    private val pageSize = 10
    private val eventId = randomInt()

    private val firstPage = randomStubhubListingResponse(
            start = 0,
            totalListings = 21,
            stubhubListing = randomList(pageSize, { randomStubhubListing() }),
            eventId = eventId
    )

    private val secondPage = randomStubhubListingResponse(
            start = 10,
            totalListings = 21,
            stubhubListing = randomList(pageSize, { randomStubhubListing() }),
            eventId = eventId
    )

    private val thirdPage = randomStubhubListingResponse(
            start = 20,
            totalListings = 21,
            stubhubListing = randomList(1, { randomStubhubListing() }),
            eventId = eventId
    )


    private val restTemplate = mock<RestTemplate> {
        on { exchange(any<String>(), any(), any(), any<Class<*>>()) }
                .thenReturn(ResponseEntity(firstPage, HttpStatus.OK))
                .thenReturn(ResponseEntity(secondPage, HttpStatus.OK))
                .thenReturn(ResponseEntity(thirdPage, HttpStatus.OK))
    }

    val url = randomUrl()
    private val urlConfig = mock<UrlConfig> {
        on { stubhubListingUrl } doReturn url
        on { pageSize } doReturn pageSize
    }

    val subject = StubhubClientImpl(restTemplate = restTemplate, urlConfig = urlConfig)

    @Test
    internal fun `should call restTemplate to get stubhubListings`() {
        val allEvents = subject.getAllEvents(eventId)
        assertThat(allEvents).containsAll(firstPage.stubhubListing)
        assertThat(allEvents).containsAll(secondPage.stubhubListing)
        assertThat(allEvents).containsAll(thirdPage.stubhubListing)
    }

    @Test
    internal fun `should call restTemplate for each page`() {
        subject.getAllEvents(eventId)

        TestingUtil.verifyJsonGetObject(
                restTemplate,
                getUri(0),
                StubhubListingResponse::class.java
        )

        TestingUtil.verifyJsonGetObject(
                restTemplate,
                getUri(1),
                StubhubListingResponse::class.java
        )

        TestingUtil.verifyJsonGetObject(
                restTemplate,
                getUri(2),
                StubhubListingResponse::class.java
        )

    }

    fun getUri(pageNumber: Int) = UriComponentsBuilder
            .fromHttpUrl(urlConfig.stubhubListingUrl.toExternalForm())
            .queryParam(PARAM_EVENT_ID, eventId)
            .queryParam(PARAM_ROWS, pageSize)
            .queryParam(PARAM_PAGE_START, pageSize * pageNumber)
            .build().toUriString()
}
package com.djdapz.stubhub.service

import com.djdapz.stubhub.config.UrlConfig
import com.djdapz.stubhub.domain.StubhubListingResponse
import com.djdapz.stubhub.domain.ProcessedListing
import com.djdapz.stubhub.repository.ListingRepository
import com.djdapz.stubhub.util.*
import com.djdapz.stubhub.util.TestingUtil.verifyJsonGetObject
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.util.Arrays.asList

class StubhubListingServiceTest {
    private val eventId = randomInt()
    private val stubhubUrl = randomUrl()
    private val asOfDate = randomLocalDateTime()
    private val firstStubhubListing = randomStubhubListing()
    private val secondStubhubListing = randomStubhubListing()
    private val listingResponse = randomListingResponse(
            stubhubListing = asList(firstStubhubListing, secondStubhubListing),
            totalListings = 2
    )
    private val response = ResponseEntity(listingResponse, HttpStatus.OK)
    private val firstProcessedListing = ProcessedListing(this.firstStubhubListing, asOfDate, listingResponse.eventId)
    private val secondProcessedListing = ProcessedListing(this.secondStubhubListing, asOfDate, listingResponse.eventId)



    var restTemplate = mock<RestTemplate> {
        on { exchange(any<String>(), any(), any(), any<Class<*>>()) } doReturn response
    }

    var urlConfig = mock<UrlConfig>{
        on {stubhubListingUrl } doReturn stubhubUrl
    }

    var timeService = mock<TimeService>{
        on { now() } doReturn asOfDate
    }

    var listingRepository = mock<ListingRepository>{}


    private val subject = ListingService(restTemplate, urlConfig, listingRepository, timeService)



    private val url: String
        get() = UriComponentsBuilder
                .fromHttpUrl(stubhubUrl.toExternalForm())
                .queryParam("eventid", eventId)
                .toUriString()

    @Test
    fun shouldCallRestTemplate() {
        subject.getListingFor(eventId)
        verifyJsonGetObject(
                restTemplate,
                url,
                StubhubListingResponse::class.java
        )
    }


    @Test
    fun shouldCallUrlConfig() {
        subject.getListingFor(eventId)
        verify(urlConfig).stubhubListingUrl
    }

    @Test
    fun shouldReturnListOfListings() {
        val actual = subject.getListingFor(eventId)
        assertThat(actual).containsExactlyInAnyOrder(firstProcessedListing, secondProcessedListing)
    }

    @Test
    fun shouldPersistListingsInDB() {
        subject.getListingFor(eventId)

        verify(listingRepository).saveListing(firstProcessedListing)
        verify(listingRepository).saveListing(secondProcessedListing)
    }
}
package com.djdapz.stubhub.service

import com.djdapz.stubhub.client.StubhubClientImpl
import com.djdapz.stubhub.config.UrlConfig
import com.djdapz.stubhub.domain.ProcessedListing
import com.djdapz.stubhub.domain.StubhubListingResponse
import com.djdapz.stubhub.repository.AnalysisRepository
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

class StubhubListingServiceImplTest {
    private val eventId = randomInt()
    private val stubhubUrl = randomUrl()
    private val asOfDate = randomLocalDateTime()

    private val firstStubhubListing = randomStubhubListing()
    private val secondStubhubListing = randomStubhubListing()

    private val listingResponse = randomStubhubListingResponse(
            stubhubListing = asList(firstStubhubListing, secondStubhubListing),
            totalListings = 2
    )
    private val response = ResponseEntity(listingResponse, HttpStatus.OK)
    private val firstProcessedListing = ProcessedListing(this.firstStubhubListing, asOfDate, listingResponse.eventId)
    private val secondProcessedListing = ProcessedListing(this.secondStubhubListing, asOfDate, listingResponse.eventId)

    private val analyzedSample = randomAnalyzedSample()

    private val restTemplate = mock<RestTemplate> {
        on { exchange(any<String>(), any(), any(), any<Class<*>>()) } doReturn response
    }

    private val urlConfig = mock<UrlConfig> {
        on { stubhubListingUrl } doReturn stubhubUrl
    }

    private val timeService = mock<TimeService> {
        on { now() } doReturn asOfDate
    }

    private val stubhubClient = mock<StubhubClientImpl> {
        on { getAllEvents(any()) } doReturn asList(firstStubhubListing, secondStubhubListing)
    }

    private val analysisService = mock<AnalysisService> {
        on { analyzeListings(any()) } doReturn analyzedSample
    }

    private val listingRepository = mock<ListingRepository> {}
    private val analysisRepository = mock<AnalysisRepository> {}


    private val subject = ListingServiceImpl(
            restTemplate =restTemplate,
            urlConfig =urlConfig,
            listingRepository =listingRepository,
            timeService =timeService,
            analysisService =analysisService,
            analysisRepository =analysisRepository,
            stubhubClient =stubhubClient
    )

    private val url: String
        get() = UriComponentsBuilder
                .fromHttpUrl(stubhubUrl.toExternalForm())
                .queryParam("eventid", eventId)
                .toUriString()

    @Test
    fun shouldCallRestTemplate() {
        subject.getListings(eventId)
        verifyJsonGetObject(
                restTemplate,
                url,
                StubhubListingResponse::class.java
        )
    }

    @Test
    fun shouldCallUrlConfig() {
        subject.getListings(eventId)
        verify(urlConfig).stubhubListingUrl
    }

    @Test
    fun shouldReturnListOfListings() {
        val actual = subject.getListings(eventId)
        assertThat(actual).containsExactlyInAnyOrder(firstProcessedListing, secondProcessedListing)
    }

    @Test
    fun shouldPersistListingsInDB() {
        subject.getListings(eventId)

        verify(listingRepository).saveListing(firstProcessedListing)
        verify(listingRepository).saveListing(secondProcessedListing)
    }

    @Test
    internal fun `should get all from stubhub when analyzing an event`() {
        subject.analyzeListings(eventId)

        verify(stubhubClient).getAllEvents(eventId)
    }

    @Test
    fun `should analyze results`() {
        subject.analyzeListings(eventId)

        verify(analysisService).analyzeListings(asList(firstStubhubListing, secondStubhubListing))
    }

    @Test
    fun `should save analyzed results in repository`() {
        subject.analyzeListings(eventId)

        verify(analysisRepository).save(analyzedSample, eventId)
    }


    @Test
    fun `should save return analyzed sample`() {
        val actual = subject.analyzeListings(eventId)

        assertThat(actual).isEqualTo(analyzedSample)
    }
}
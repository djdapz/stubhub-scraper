package com.djdapz.stubhub.service

import com.djdapz.stubhub.client.StubhubClientImpl
import com.djdapz.stubhub.config.UrlConfig
import com.djdapz.stubhub.domain.ProcessedListing
import com.djdapz.stubhub.domain.StubhubListingResponse
import com.djdapz.stubhub.repository.ListingRepository
import com.djdapz.stubhub.util.*
import com.djdapz.stubhub.util.TestingUtil.verifyJsonGetObject
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.nield.kotlinstatistics.median
import org.nield.kotlinstatistics.standardDeviation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.math.BigDecimal
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


    var restTemplate = mock<RestTemplate> {
        on { exchange(any<String>(), any(), any(), any<Class<*>>()) } doReturn response
    }

    var urlConfig = mock<UrlConfig> {
        on { stubhubListingUrl } doReturn stubhubUrl
    }

    var timeService = mock<TimeService> {
        on { now() } doReturn asOfDate
    }

    var stubhubClient = mock<StubhubClientImpl> {
        on { getAllEvents(any()) } doReturn asList(firstStubhubListing, secondStubhubListing)
    }

    var listingRepository = mock<ListingRepository> {}


    private val subject = ListingServiceImpl(restTemplate, urlConfig, listingRepository, timeService, stubhubClient)

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
    internal fun `should get average of listing prices from stubhub when analyzing an event`() {
        val actual = subject.analyzeListings(eventId)

        val average = (firstProcessedListing.listingPrice.amount + secondProcessedListing.listingPrice.amount)
                .div(BigDecimal(2))

        assertThat(actual.average).isEqualTo(average)
    }

    @Test
    internal fun `should get maximum of listing prices from stubhub when analyzing an event`() {
        val actual = subject.analyzeListings(eventId)

        val max = asList(firstProcessedListing.listingPrice.amount, secondProcessedListing.listingPrice.amount)
                .max()

        assertThat(actual.maximum).isEqualTo(max)
    }

    @Test
    internal fun `should get minimum of listing prices from stubhub when analyzing an event`() {
        val actual = subject.analyzeListings(eventId)

        val min = asList(firstProcessedListing.listingPrice.amount, secondProcessedListing.listingPrice.amount)
                .min()

        assertThat(actual.minimum).isEqualTo(min)
    }

    @Test
    internal fun `should get standard deviation of listing prices from stubhub when analyzing an event`() {
        val actual = subject.analyzeListings(eventId)

        val standardDeviation = asList(firstProcessedListing.listingPrice.amount, secondProcessedListing.listingPrice.amount)
                .standardDeviation().toBigDecimal()

        assertThat(actual.standardDeviation).isEqualTo(standardDeviation)
    }

    @Test
    internal fun `should get number  of listing prices from stubhub when analyzing an event`() {
        val actual = subject.analyzeListings(eventId)

        val count = asList(firstProcessedListing.listingPrice.amount, secondProcessedListing.listingPrice.amount)
                .size

        assertThat(actual.count).isEqualTo(count)
    }


    @Test
    internal fun `should get median of listing prices from stubhub when analyzing an event`() {
        val actual = subject.analyzeListings(eventId)

        val median = asList(firstProcessedListing.listingPrice.amount, secondProcessedListing.listingPrice.amount)
                .median().toBigDecimal()

        assertThat(actual.median).isEqualTo(median)
    }
}
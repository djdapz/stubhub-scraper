package com.djdapz.stubhub.service

import com.djdapz.stubhub.client.StubhubClient
import com.djdapz.stubhub.config.UrlConfig
import com.djdapz.stubhub.domain.AnalyzedSample
import com.djdapz.stubhub.domain.ProcessedListing
import com.djdapz.stubhub.domain.StubhubListingResponse
import com.djdapz.stubhub.repository.ListingRepository
import com.djdapz.stubhub.util.RestUtil.getJsonObject
import org.nield.kotlinstatistics.average
import org.nield.kotlinstatistics.median
import org.nield.kotlinstatistics.standardDeviation
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.time.LocalDateTime

@Service
class ListingServiceImpl(
        private val restTemplate: RestTemplate,
        private val urlConfig: UrlConfig,
        private val listingRepository: ListingRepository,
        private val timeService: TimeService,
        val stubhubClient: StubhubClient
) : ListingService {

    override fun getListings(eventId: Int): List<ProcessedListing> {
        val now = timeService.now()
        println("Getting info for event: ${eventId}")
        val listingResponse = getJsonObject(restTemplate, getUrl(eventId), StubhubListingResponse::class.java)
        return listingResponse
                .stubhubListing
                .map { listing -> ProcessedListing(listing, now, listingResponse.eventId) }
                .onEach { listingRepository.saveListing(it) }
    }

    private fun getUrl(eventId: Int?): String {
        return UriComponentsBuilder
                .fromHttpUrl(urlConfig.stubhubListingUrl.toExternalForm())
                .queryParam("eventid", eventId)
                .toUriString()
    }

    override fun analyzeListings(eventId: Int): AnalyzedSample {
        val allEvents = stubhubClient.getAllEvents(eventId).map { it.listingPrice.amount }

        return AnalyzedSample(
                asOfDate = LocalDateTime.now(),
                count = allEvents.size,
                average = allEvents.average(),
                minimum = allEvents.min()!!,
                maximum = allEvents.max()!!,
                standardDeviation = allEvents.standardDeviation().toBigDecimal(),
                median = allEvents.median().toBigDecimal()
        )
    }
}

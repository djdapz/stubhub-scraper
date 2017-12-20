package com.djdapz.stubhub.service

import com.djdapz.stubhub.config.UrlConfig
import com.djdapz.stubhub.domain.StubhubListingResponse
import com.djdapz.stubhub.domain.ProcessedListing
import com.djdapz.stubhub.repository.ListingRepository
import com.djdapz.stubhub.util.RestUtil.getJsonObject
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class ListingService(
        private val restTemplate: RestTemplate,
        private val urlConfig: UrlConfig,
        private val listingRepository: ListingRepository,
        private val timeService: TimeService
) {

    fun getListingFor(eventId: Int?): List<ProcessedListing> {
        val now = timeService.now()
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
}

package com.djdapz.stubhub.client

import com.djdapz.stubhub.config.PARAM_EVENT_ID
import com.djdapz.stubhub.config.PARAM_PAGE_START
import com.djdapz.stubhub.config.PARAM_ROWS
import com.djdapz.stubhub.config.UrlConfig
import com.djdapz.stubhub.domain.StubhubListing
import com.djdapz.stubhub.domain.StubhubListingResponse
import com.djdapz.stubhub.util.RestUtil.getJsonObject
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder


@Component
class StubhubClientImpl(val restTemplate: RestTemplate, val urlConfig: UrlConfig) : StubhubClient {
    override fun getAllEvents(eventId: Int): List<StubhubListing> = getPagesStartingAt(0, eventId)

    private fun getPagesStartingAt(pageNumber: Int, eventId: Int): List<StubhubListing> =
            getJsonObject(restTemplate, getUri(pageNumber, eventId), StubhubListingResponse::class.java).let {
                it.stubhubListing + getExtraPages(pageNumber, it.totalListings, eventId)
            }

    private fun getExtraPages(pageNumber: Int, totalListings: Int, eventId: Int): List<StubhubListing> {
        return if (areMorePages(totalListings, pageNumber)) {
            getPagesStartingAt(pageNumber + 1, eventId)
        } else
            emptyList()
    }

    private fun areMorePages(totalListings: Int, pageNumber: Int) =
            totalListings >= (pageNumber * urlConfig.pageSize) + urlConfig.pageSize


    private fun getUri(pageNumber: Int, eventId: Int) = UriComponentsBuilder
            .fromHttpUrl(urlConfig.stubhubListingUrl.toExternalForm())
            .queryParam(PARAM_EVENT_ID, eventId)
            .queryParam(PARAM_ROWS, urlConfig.pageSize)
            .queryParam(PARAM_PAGE_START, urlConfig.pageSize * pageNumber)
            .build().toUriString()

}
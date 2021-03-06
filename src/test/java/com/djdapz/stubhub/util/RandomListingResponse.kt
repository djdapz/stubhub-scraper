package com.djdapz.stubhub.util

import com.djdapz.stubhub.domain.StubhubListing
import com.djdapz.stubhub.domain.StubhubListingResponse

fun randomStubhubListingResponse(
        eventId: Int = randomInt(),
        totalListings: Int = randomInt(),
        totalTickets: Int = randomInt(),
        minQuantity: Int = randomInt(),
        maxQuantity: Int = randomInt(),
        stubhubListing: List<StubhubListing> = randomList(::randomStubhubListing),
        listingAttributeCategorySummary: List<Int> = randomList({randomInt()}),
        start: Int = randomInt()
): StubhubListingResponse =
        StubhubListingResponse(
                eventId,
                totalListings,
                totalTickets,
                minQuantity,
                maxQuantity,
                stubhubListing,
                listingAttributeCategorySummary,
                start,
                stubhubListing.size
        )
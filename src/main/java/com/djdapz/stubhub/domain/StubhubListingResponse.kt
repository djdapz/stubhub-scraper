package com.djdapz.stubhub.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class StubhubListingResponse(
        val eventId: Int,
        val totalListings: Int,
        val totalTickets: Int,
        val minQuantity: Int,
        val maxQuantity: Int,
        @JsonProperty("listing")
        val stubhubListing: List<StubhubListing>,
        val listingAttributeCategorySummary: List<Int>,
        val start: Int,
        val rows: Int
)

package com.djdapz.stubhub.domain

data class StubhubListingResponse(
        val eventId: Int,
        val totalListings: Int,
        val totalTickets: Int,
        val minQuantity: Int,
        val maxQuantity: Int,
        val stubhubListing: List<StubhubListing>,
        val listingAttributeCategorySummary: List<Int>,
        val start: Int,
        val rows: Int
)

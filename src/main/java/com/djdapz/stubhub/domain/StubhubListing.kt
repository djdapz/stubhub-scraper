package com.djdapz.stubhub.domain

data class StubhubListing(
        val listingId: Int,
        val currentPrice: Price,
        val listingPrice: Price,
        val sectionId: Int,
        val row: String,
        val quantity: Int,
        val sellerSectionName: String,
        val sectionName: String,
        val seatNumbers: String?,
        val zoneId: Int,
        val zoneName: String,
        val isGA: Int,
        val dirtyTicketInd: Boolean,
        val splitOption: String,
        val ticketSplit: String,
        val score: Int
)

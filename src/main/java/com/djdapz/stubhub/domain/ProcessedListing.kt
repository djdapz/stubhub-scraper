package com.djdapz.stubhub.domain

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime


data class ProcessedListing(
        val asOfDate: LocalDateTime,
        val listingId: Int,
        val eventId: Int,
        val currentPrice: Price,
        val dirtyTicketInd: Boolean,
        val isGa: Int,
        val listingPrice: Price,
        val quantity: Int,
        val row: String,
        val score: Int,
        val seatNumbers: String?,
        val sectionId: Int,
        val sectionName: String,
        val sellerSectionName: String,
        val splitOption: String,
        val ticketSplit: String,
        val zoneId: Int,
        val zoneName: String
) {
    constructor(stubhubListing: StubhubListing, asOfDate: LocalDateTime, eventId: Int) : this(
            asOfDate = asOfDate,
            listingId = stubhubListing.listingId,
            eventId = eventId,
            currentPrice = stubhubListing.currentPrice,
            dirtyTicketInd = stubhubListing.dirtyTicketInd,
            isGa = stubhubListing.isGA,// == 1,
            listingPrice = stubhubListing.listingPrice,
            quantity = stubhubListing.quantity,
            row = stubhubListing.row,
            score = stubhubListing.score,
            seatNumbers = stubhubListing.seatNumbers,
            sectionId = stubhubListing.sectionId,
            sectionName = stubhubListing.sectionName,
            sellerSectionName = stubhubListing.sellerSectionName,
            splitOption = stubhubListing.splitOption,
            ticketSplit = stubhubListing.ticketSplit,
            zoneId = stubhubListing.zoneId,
            zoneName = stubhubListing.zoneName
    )

    @Suppress("unused")
    fun getIsGa() = isGa
}





package com.djdapz.stubhub.controller

import com.djdapz.stubhub.domain.ProcessedListing
import com.djdapz.stubhub.service.ListingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ListingController(private val listingService: ListingService) {

    @GetMapping("/listings")
    fun getListings(
            @RequestParam("eventId") eventId: Int
    ): List<ProcessedListing> {
        return listingService.getListingFor(eventId)
    }
}

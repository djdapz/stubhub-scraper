package com.djdapz.stubhub.controller;

import com.djdapz.stubhub.domain.ProcessedListing;
import com.djdapz.stubhub.service.ListingService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RestController
public class ListingController {

    ListingService listingService;

    @GetMapping("/listings")
    public List<ProcessedListing> getListings(
            @RequestParam("eventId") final Integer eventId
    ) {
        return listingService.getListingFor(eventId);
    }
}

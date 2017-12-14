package com.djdapz.stubhub.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Wither;

import java.util.List;

@Data
@AllArgsConstructor
@Getter
@Wither
public class ListingResponse {
    Integer eventId;
    Integer totalListings;
    Integer totalTickets;
    Integer minQuantity;
    Integer maxQuantity;
    List<Listing> listing;
    List<Integer> listingAttributeCategorySummary;
    Integer start;
    Integer rows;
}

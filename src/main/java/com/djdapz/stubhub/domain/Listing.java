package com.djdapz.stubhub.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Value
@AllArgsConstructor
@Getter
public class Listing {
    Integer listingId;
    Price currentPrice;
    Price listingPrice;
    Integer sectionId;
    String row;
    Integer quantity;
    String sellerSectionName;
    String sectionName;
    String seatNumbers;
    Integer zoneId;
    String zoneName;
    Integer isGA;
    Boolean dirtyTicketInd;
    String splitOption;
    String ticketSplit;
    Integer score;
}

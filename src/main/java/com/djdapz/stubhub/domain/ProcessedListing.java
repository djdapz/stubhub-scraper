package com.djdapz.stubhub.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
@Getter
@AllArgsConstructor
@Value
public class ProcessedListing {
    Integer eventId;
    Integer listingId;
    LocalDateTime asOfDate;
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


    public ProcessedListing(Listing listing, LocalDateTime time, Integer eventId) {
        this.eventId = eventId;
        this.listingId = listing.getListingId();
        this.currentPrice = listing.getCurrentPrice();
        this.listingPrice = listing.getListingPrice();
        this.sectionId = listing.getSectionId();
        this.row = listing.getRow();
        this.quantity = listing.getQuantity();
        this.sellerSectionName = listing.getSellerSectionName();
        this.sectionName = listing.getSectionName();
        this.seatNumbers = listing.getSeatNumbers();
        this.zoneId = listing.getZoneId();
        this.zoneName = listing.getZoneName();
        this.isGA = listing.getIsGA();
        this.dirtyTicketInd = listing.getDirtyTicketInd();
        this.splitOption = listing.getSplitOption();
        this.ticketSplit = listing.getTicketSplit();
        this.score = listing.getScore();
        this.asOfDate = time;
    }
}





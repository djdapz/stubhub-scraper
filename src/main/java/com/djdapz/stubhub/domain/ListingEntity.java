package com.djdapz.stubhub.domain;

import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
@Getter
public class ListingEntity {
    Integer eventId;
    Integer listingId;
    OffsetDateTime asOfDate;
    BigDecimal currentPriceAmount;
    String currentPriceCurrency;
    BigDecimal listingPriceAmount;
    String listingPriceCurrency;
    Integer sectionId;
    Integer quantity;
    Integer zoneId;
    Integer isGA;
    Integer score;
    String row;
    String sellerSectionName;
    String sectionName;
    String seatNumbers;
    String zoneName;
    String splitOption;
    String ticketSplit;
    Boolean dirtyTicketInd;

    public ListingEntity(ProcessedListing processedListing) {
        this.listingId = processedListing.getListingId();
        this.currentPriceCurrency = processedListing.getListingPrice().getCurrency();
        this.currentPriceAmount = processedListing.getCurrentPrice().getAmount();
        this.listingPriceCurrency = processedListing.getCurrentPrice().getCurrency();
        this.listingPriceAmount = processedListing.getListingPrice().getAmount();
        this.sectionId = processedListing.getSectionId();
        this.row = processedListing.getRow();
        this.quantity = processedListing.getQuantity();
        this.sellerSectionName = processedListing.getSellerSectionName();
        this.sectionName = processedListing.getSectionName();
        this.seatNumbers = processedListing.getSeatNumbers();
        this.zoneId = processedListing.getZoneId();
        this.zoneName = processedListing.getZoneName();
        this.isGA = processedListing.getIsGA();
        this.dirtyTicketInd = processedListing.getDirtyTicketInd();
        this.splitOption = processedListing.getSplitOption();
        this.ticketSplit = processedListing.getTicketSplit();
        this.score = processedListing.getScore();
        this.asOfDate = processedListing.getAsOfDate();
        this.eventId = processedListing.getEventId();
    }
}

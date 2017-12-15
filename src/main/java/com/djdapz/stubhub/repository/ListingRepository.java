package com.djdapz.stubhub.repository;

import com.djdapz.stubhub.domain.ProcessedListing;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Repository
public class ListingRepository {
    JdbcTemplate jdbcTemplate;

    public void saveListing(ProcessedListing listing) {
        jdbcTemplate.update(
                insertSql(),
                listing.getListingId(),
                listing.getEventId(),
                Timestamp.from(listing.getAsOfDate().toInstant(OffsetDateTime.now().getOffset())),
                listing.getCurrentPrice().getAmount(),
                listing.getCurrentPrice().getCurrency(),
                listing.getListingPrice().getAmount(),
                listing.getListingPrice().getCurrency(),
                listing.getSectionId(),
                listing.getQuantity(),
                listing.getZoneId(),
                listing.getIsGA(),
                listing.getScore(),
                listing.getRow(),
                listing.getSellerSectionName(),
                listing.getSectionName(),
                listing.getSeatNumbers(),
                listing.getZoneName(),
                listing.getSplitOption(),
                listing.getTicketSplit(),
                listing.getDirtyTicketInd());
    }

    private String insertSql() {
        return "INSERT INTO listing(" +
                " listing_id " +
                ", event_id " +
                ", as_of_date " +
                ", current_price_amount " +
                ", current_price_currency " +
                ", listing_price_amount " +
                ", listing_price_currency " +
                ", sectionId " +
                ", quantity " +
                ", zoneId " +
                ", isGA " +
                ", score " +
                ", row " +
                ", sellerSectionName " +
                ", sectionName " +
                ", seatNumbers " +
                ", zoneName " +
                ", splitOption " +
                ", ticketSplit " +
                ", dirtyTicketInd " +
                ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    }


}

package com.djdapz.stubhub.repository

import com.djdapz.stubhub.config.SqlConfig
import com.djdapz.stubhub.domain.ProcessedListing
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.Timestamp
import java.time.OffsetDateTime

@Repository
class ListingRepository(val jdbcTemplate: JdbcTemplate, val sqlConfig: SqlConfig) {

    fun saveListing(listing: ProcessedListing) {
        jdbcTemplate.update(
                insertSql(),
                listing.listingId,
                listing.eventId,
                Timestamp.from(listing.asOfDate.toInstant(OffsetDateTime.now().offset)),
                listing.currentPrice.amount,
                listing.currentPrice.currency,
                listing.listingPrice.amount,
                listing.listingPrice.currency,
                listing.sectionId,
                listing.quantity,
                listing.zoneId,
                listing.isGa,
                listing.score,
                listing.row,
                listing.sellerSectionName,
                listing.sectionName,
                listing.seatNumbers,
                listing.zoneName,
                listing.splitOption,
                listing.ticketSplit,
                listing.dirtyTicketInd)
    }

    private fun insertSql(): String {
        return "INSERT INTO ${sqlConfig.schema}.stubhubListing(" +
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
                ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
    }
}

package com.djdapz.stubhub.repository

import com.djdapz.stubhub.config.SqlConfig
import com.djdapz.stubhub.util.randomInt
import com.djdapz.stubhub.util.randomProcessedListing
import com.djdapz.stubhub.util.randomString
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.Timestamp
import java.time.OffsetDateTime

class StubhubListingRepositoryTest {

    private val schema = randomString()

    private val jdbcTemplate = mock<JdbcTemplate> {}
    private val sqlConfig = mock<SqlConfig> {
        on { schema } doReturn schema
    }
    private val subject = ListingRepository(jdbcTemplate, sqlConfig)

    private val listing = randomProcessedListing()
    private val eventId = randomInt()

    @Test
    fun shouldCallJdbcTemplateUpdateWhenSaving() {

        subject.saveListing(listing)
        verify(jdbcTemplate).update(
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


    @Test
    fun shouldCallJdbcTemplateGetWhenQueryingAgainstAnalysis() {
        subject.getSamples(eventId)
        verify(jdbcTemplate).queryForList(analysisSql, eventId)
    }


    private fun insertSql(): String {
        return "INSERT INTO ${schema}.stubhubListing(" +
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

    private val analysisSql = "SELECT as_of_date, " +
            "   COUNT(*), " +
            "   AVG(current_price_amount), " +
            "   MIN(current_price_amount), " +
            "   MAX(current_price_amount), " +
            "   STDDEV(current_price_amount), " +
            "   MEDIAN(current_price_amount)" +
            "FROM ${sqlConfig.schema}.stubhublisting " +
//            "WHERE sectionname like '%Lower%' or sectionname like '%Floor%'" +
            "GROUP by as_of_date" +
            "WHERE eventId = ?;"
}
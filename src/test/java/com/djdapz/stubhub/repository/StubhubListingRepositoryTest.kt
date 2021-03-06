package com.djdapz.stubhub.repository

import com.djdapz.stubhub.config.SqlConfig
import com.djdapz.stubhub.repository.mapper.AnalyzedSampleRowMapper
import com.djdapz.stubhub.util.randomAnalyzedSample
import com.djdapz.stubhub.util.randomInt
import com.djdapz.stubhub.util.randomProcessedListing
import com.djdapz.stubhub.util.randomString
import com.nhaarman.mockito_kotlin.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.Matchers.anyString
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.Timestamp
import java.time.OffsetDateTime
import java.util.Arrays.asList

class StubhubListingRepositoryTest {

    private val firstSample = randomAnalyzedSample()
    private val secondSample = randomAnalyzedSample()
    private val samples = asList(firstSample, secondSample)

    private val listing = randomProcessedListing()
    private val eventId = randomInt()

    private val schema = randomString()

    private val analyzedSampleRowMapper = mock<AnalyzedSampleRowMapper> {}

    private val jdbcTemplate = mock<JdbcTemplate> {
        on { query(anyString(), eq(analyzedSampleRowMapper), any()) } doReturn samples
    }

    private val sqlConfig = mock<SqlConfig> {
        on { schema } doReturn schema
    }


    private val subject = ListingRepository(jdbcTemplate, sqlConfig, analyzedSampleRowMapper)

    @Test
    fun shouldCallJdbcTemplateUpdateWhenSaving() {
        subject.saveListing(listing)
        verify(jdbcTemplate).update(
                insertSql,
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
        verify(jdbcTemplate).query(analysisSql, analyzedSampleRowMapper, eventId)
    }


    @Test
    internal fun `should return list of analyzed samples for analysis`() {
        val samples = subject.getSamples(eventId)
        assertThat(samples).containsExactly(firstSample, secondSample)
    }


    private val insertSql =
            """INSERT INTO ${schema}.stubhubListing(
                 listing_id
                , event_id
                , as_of_date
                , current_price_amount
                , current_price_currency
                , listing_price_amount
                , listing_price_currency
                , sectionId
                , quantity
                , zoneId
                , isGA
                , score
                , row
                , sellerSectionName
                , sectionName
                , seatNumbers
                , zoneName
                , splitOption
                , ticketSplit
                , dirtyTicketInd
                ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"""

    val analysisSql = """SELECT as_of_date,
                            COUNT(*),
                            AVG(current_price_amount),
                            MIN(current_price_amount),
                            MAX(current_price_amount),
                            STDDEV(current_price_amount)
                        FROM ${sqlConfig.schema}.stubhubListing
                        WHERE sectionname like '%Lower%' or sectionname like '%Floor'
                        AND event_id = ?
                        GROUP by as_of_date;
                    """
}
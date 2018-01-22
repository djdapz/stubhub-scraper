package com.djdapz.stubhub.repository

import com.djdapz.stubhub.config.SqlConfig
import com.djdapz.stubhub.domain.AnalyzedSample
import com.djdapz.stubhub.domain.ProcessedListing
import com.djdapz.stubhub.repository.mapper.AnalyzedSampleRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.Timestamp
import java.time.OffsetDateTime

@Repository
class ListingRepository(private val jdbcTemplate: JdbcTemplate, sqlConfig: SqlConfig, private val analyzedSampleRowMapper: AnalyzedSampleRowMapper) {

    fun saveListing(listing: ProcessedListing) =
        jdbcTemplate.update(
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


    fun getSamples(eventId: Int): List<AnalyzedSample> = jdbcTemplate.query(analysisSql, analyzedSampleRowMapper, eventId)

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


    val insertSql = """INSERT INTO ${sqlConfig.schema}.stubhubListing(
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
}

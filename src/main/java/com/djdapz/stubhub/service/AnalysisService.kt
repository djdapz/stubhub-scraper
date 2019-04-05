package com.djdapz.stubhub.service

import com.djdapz.stubhub.domain.AnalyzedSample
import com.djdapz.stubhub.domain.ProcessedListing
import com.djdapz.stubhub.domain.StubhubListing
import com.djdapz.stubhub.repository.ListingRepository
import org.nield.kotlinstatistics.average
import org.nield.kotlinstatistics.median
import org.nield.kotlinstatistics.standardDeviation
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class AnalysisService(val repository: ListingRepository) {
    fun getAnalyzedSamples(eventId: Int): List<AnalyzedSample> = repository.getSamples(eventId)

    fun analyzeListings(events: List<StubhubListing>): AnalyzedSample {
        val amounts = events.map { it.listingPrice.amount }
        return AnalyzedSample(
                asOfDate = LocalDateTime.now(),
                count = amounts.size,
                average = amounts.average(),
                minimum = amounts.min()!!,
                maximum = amounts.max()!!,
                standardDeviation = amounts.standardDeviation().toBigDecimal(),
                median = amounts.median().toBigDecimal()
        )
    }
}

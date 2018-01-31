package com.djdapz.stubhub.service

import com.djdapz.stubhub.domain.AnalyzedSample
import com.djdapz.stubhub.domain.ProcessedListing

interface ListingService {
    fun getListings(eventId: Int): List<ProcessedListing>

    fun analyzeListings(eventId: Int): AnalyzedSample
}
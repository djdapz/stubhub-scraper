package com.djdapz.stubhub.service

import com.djdapz.stubhub.domain.AnalyzedSample
import com.djdapz.stubhub.repository.ListingRepository
import org.springframework.stereotype.Service

@Service
class AnalysisService(val repository: ListingRepository) {
    fun getAnalyzedSamples(eventId: Int): List<AnalyzedSample> = repository.getSamples(eventId)
}

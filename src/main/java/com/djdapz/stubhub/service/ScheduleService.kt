package com.djdapz.stubhub.service

import com.djdapz.stubhub.domain.AnalysisType
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ScheduleService(
        private val listingService: ListingService,
        private val eventService: EventService
) {

    @Scheduled(cron = "0 0 0 * * *")
    fun getListingsForHardcodedList() = eventService
            .getEvents()
            .forEach {
                when (it.analysisType) {
                    AnalysisType.AGGREGATE -> listingService.analyzeListings(it.eventId)
                    AnalysisType.ALL -> listingService.getListings(it.eventId)
                }
            }
}
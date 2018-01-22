package com.djdapz.stubhub.service

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
            .map { it.eventId }
            .forEach { listingService.getListingFor(it) }
}
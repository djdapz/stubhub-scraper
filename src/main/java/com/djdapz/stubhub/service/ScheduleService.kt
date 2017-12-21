package com.djdapz.stubhub.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

const val BON_IVER_MILWAUKEE_2017 = 103277091

@Service
class ScheduleService(
        private val listingService: ListingService
) {
    @Scheduled(cron="0 0 12,6 * * *")
    fun getListingsForHardcodedList() {
        listingService.getListingFor(BON_IVER_MILWAUKEE_2017)
    }
}

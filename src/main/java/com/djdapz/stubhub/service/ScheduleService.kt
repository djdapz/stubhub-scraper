package com.djdapz.stubhub.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.Arrays.asList

const val BON_IVER_MILWAUKEE_2017 = 103277091
const val COACHELLA_WEEKEND_1 = 103006757
const val COACHELLA_WEEKEND_2 = 103006758

@Service
class ScheduleService(
        private val listingService: ListingService
) {
    val shows = asList(BON_IVER_MILWAUKEE_2017, COACHELLA_WEEKEND_1, COACHELLA_WEEKEND_2)

    @Scheduled(cron = "0 0 0 * * *")
    fun getListingsForHardcodedList() = shows.forEach { listingService.getListingFor(it) }
}

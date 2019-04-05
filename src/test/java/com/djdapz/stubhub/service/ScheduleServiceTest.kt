package com.djdapz.stubhub.service

import com.djdapz.stubhub.domain.AnalysisType
import com.djdapz.stubhub.domain.AnalysisType.*
import com.djdapz.stubhub.util.randomStubhubEvent
import com.nhaarman.mockito_kotlin.*

import org.junit.Test
import java.util.Arrays.asList

class ScheduleServiceTest {
    private val listingService = mock<ListingService> {}
    private val eventService = mock<EventService> {}

    private val subject = ScheduleService(listingService, eventService)

    @Test
    internal fun `should save all listings when type is ALL`() {
        val firstEvent = randomStubhubEvent(analysisType = ALL)
        val secondEvent = randomStubhubEvent(analysisType = ALL)

        eventService.stub {
            on { getEvents() } doReturn asList(firstEvent, secondEvent)
        }

        subject.getListingsForHardcodedList()

        verify(listingService).getListings(firstEvent.eventId)
        verify(listingService).getListings(secondEvent.eventId)
        verify(listingService, never()).analyzeListings(any())
    }

    @Test
    internal fun `should save analysis of listings when type is AGGREGATE`() {
        val firstEvent = randomStubhubEvent(analysisType = AGGREGATE)
        val secondEvent = randomStubhubEvent(analysisType = AGGREGATE)

        eventService.stub {
            on { getEvents() } doReturn asList(firstEvent, secondEvent)
        }

        subject.getListingsForHardcodedList()

        verify(listingService).analyzeListings(firstEvent.eventId)
        verify(listingService).analyzeListings(secondEvent.eventId)
        verify(listingService, never()).getListings(any())
    }
}
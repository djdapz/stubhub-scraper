package com.djdapz.stubhub.service

import com.djdapz.stubhub.util.randomStubhubEvent
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify

import org.junit.Test
import java.util.Arrays.asList

class ScheduleServiceTest {

    private val firstEvent = randomStubhubEvent()
    private val secondEvent = randomStubhubEvent()

    private val listingService = mock<ListingService> {}
    private val eventService = mock<EventService> {
        on { getEvents() } doReturn asList(firstEvent, secondEvent)
    }

    private val subject = ScheduleService(listingService, eventService)

    @Test
    internal fun `should call get events based on database values`() {
        subject.getListingsForHardcodedList()
        verify(listingService).getListings(firstEvent.eventId)
        verify(listingService).getListings(secondEvent.eventId)
    }
}
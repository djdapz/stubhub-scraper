package com.djdapz.stubhub.service

import com.djdapz.stubhub.repository.EventRepository

import com.djdapz.stubhub.util.randomList
import com.djdapz.stubhub.util.randomStubhubEvent
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class EventServiceTest {

    private val expectedEvents = randomList { randomStubhubEvent() }

    private val eventRepository = mock<EventRepository> {
        on { getEvents() } doReturn expectedEvents
    }

    val subject = EventService(eventRepository)


    @Test
    internal fun `should query event repository for list of events`() {
        subject.getEvents()
        verify(eventRepository).getEvents()
    }

    @Test
    internal fun `should retun list of events`() {
        val actualEvents = subject.getEvents()
        assertThat(actualEvents).containsExactlyElementsOf(expectedEvents)
    }
}
package com.djdapz.stubhub.service

import com.djdapz.stubhub.domain.StubhubEvent
import com.djdapz.stubhub.repository.EventRepository
import org.springframework.stereotype.Service

@Service
class EventService(val eventRepository: EventRepository) {
    fun getEvents(): List<StubhubEvent> = eventRepository.getEvents()
}

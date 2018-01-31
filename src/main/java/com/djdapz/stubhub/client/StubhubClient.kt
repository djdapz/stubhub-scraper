package com.djdapz.stubhub.client

import com.djdapz.stubhub.domain.StubhubListing

interface StubhubClient {
    fun getAllEvents(eventId: Int): List<StubhubListing>
}
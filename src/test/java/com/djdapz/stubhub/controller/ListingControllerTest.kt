package com.djdapz.stubhub.controller

import com.djdapz.stubhub.config.JsonConfig.Companion.asObject
import com.djdapz.stubhub.domain.ProcessedListing
import com.djdapz.stubhub.service.ListingService
import com.djdapz.stubhub.util.randomInt
import com.djdapz.stubhub.util.randomProcessedListing
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.Arrays.asList

class ListingControllerTest {
    private val eventId: Int = randomInt()
    private val expectedListings = asList(randomProcessedListing(), randomProcessedListing())

    private val listingService = mock<ListingService> {
        on { getListingFor(any()) } doReturn expectedListings
    }
    private val subject = ListingController(listingService)
    private val mockMvc = MockMvcBuilders.standaloneSetup(subject).build()

    @Test
    @Throws(Exception::class)
    fun shouldDelegateToListingService() {
        mockMvc.perform(
                get("/listings")
                        .param("eventId", eventId.toString())
        )

        verify(listingService).getListingFor(eventId)
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnListOfListings() {
        val listingsJson = mockMvc.perform(
                get("/listings")
                        .param("eventId", this.eventId.toString()))
                .andReturn()
                .response
                .contentAsString

        val actual = asObject(listingsJson, Array<ProcessedListing>::class.java)

        assertThat(actual).containsExactlyElementsOf(expectedListings)
    }
}
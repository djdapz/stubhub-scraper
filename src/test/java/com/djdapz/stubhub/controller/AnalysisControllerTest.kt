package com.djdapz.stubhub.controller

import com.djdapz.stubhub.config.JsonConfig
import com.djdapz.stubhub.domain.AnalyzedSample
import com.djdapz.stubhub.service.AnalysisService
import com.djdapz.stubhub.util.randomAnalyzedSample
import com.djdapz.stubhub.util.randomInt
import com.djdapz.stubhub.util.randomList
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class AnalysisControllerTest {
    private val eventId: Int = randomInt()
    private val expectedAnalyzedRows = randomList(::randomAnalyzedSample)

    private val service = mock<AnalysisService> {
        on { getAnalyzedSamples(any()) } doReturn expectedAnalyzedRows
    }
    private val subject = AnalysisController(service)
    private val mockMvc = MockMvcBuilders.standaloneSetup(subject).build()

    @Test
    @Throws(Exception::class)
    fun `should call analysis service`() {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/analysis")
                        .param("eventId", eventId.toString())
        )

        verify(service).getAnalyzedSamples(eventId)
    }

    @Test
    @Throws(Exception::class)
    fun `should return list of rows with stats`() {
        val listingsJson = mockMvc.perform(
                MockMvcRequestBuilders.get("/analysis")
                        .param("eventId", this.eventId.toString()))
                .andReturn()
                .response
                .contentAsString

        val actual = JsonConfig.asObject(listingsJson, Array<AnalyzedSample>::class.java)

        assertThat(actual).containsExactlyElementsOf(expectedAnalyzedRows)
    }
}
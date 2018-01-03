package com.djdapz.stubhub.service

import com.djdapz.stubhub.repository.ListingRepository
import com.djdapz.stubhub.util.randomAnalyzedSample
import com.djdapz.stubhub.util.randomInt
import com.djdapz.stubhub.util.randomList
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class AnalysisServiceTest {

    private val expectedSamples = randomList(::randomAnalyzedSample)

    private val repository = mock<ListingRepository> {
        on { getSamples(any()) } doReturn expectedSamples
    }

    private val subject = AnalysisService(repository)
    private val eventId = randomInt()

    @Test
    internal fun `should call analysis repository`() {
        subject.getAnalyzedSamples(eventId)
        verify(repository).getSamples(eventId)
    }

    @Test
    internal fun `should return list of samples that the repo found`() {
        val samples = subject.getAnalyzedSamples(eventId)
        assertThat(samples).containsExactlyElementsOf(expectedSamples)
    }
}

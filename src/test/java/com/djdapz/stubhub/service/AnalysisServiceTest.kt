package com.djdapz.stubhub.service

import com.djdapz.stubhub.repository.ListingRepository
import com.djdapz.stubhub.util.*
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.nield.kotlinstatistics.median
import org.nield.kotlinstatistics.standardDeviation
import java.math.BigDecimal
import java.util.*
import java.util.Arrays.asList

class AnalysisServiceTest {

    private val firstStubhubListings = randomStubhubListing()
    private val secondStubhubListing =  randomStubhubListing()

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


    @Test
    internal fun `should get average of listing prices from stubhub when analyzing an event`() {
        val actual = subject.analyzeListings(asList(firstStubhubListings, secondStubhubListing))

        val average = (firstStubhubListings.listingPrice.amount + secondStubhubListing.listingPrice.amount)
                .div(BigDecimal(2))

        assertThat(actual.average).isEqualTo(average)
    }

    @Test
    internal fun `should get maximum of listing prices from stubhub when analyzing an event`() {
        val actual = subject.analyzeListings(asList(firstStubhubListings, secondStubhubListing))

        val max = Arrays.asList(firstStubhubListings.listingPrice.amount, secondStubhubListing.listingPrice.amount)
                .max()

        assertThat(actual.maximum).isEqualTo(max)
    }

    @Test
    internal fun `should get minimum of listing prices from stubhub when analyzing an event`() {
        val actual = subject.analyzeListings(asList(firstStubhubListings, secondStubhubListing))

        val min = Arrays.asList(firstStubhubListings.listingPrice.amount, secondStubhubListing.listingPrice.amount)
                .min()

        assertThat(actual.minimum).isEqualTo(min)
    }

    @Test
    internal fun `should get standard deviation of listing prices from stubhub when analyzing an event`() {
        val actual = subject.analyzeListings(asList(firstStubhubListings, secondStubhubListing))

        val standardDeviation = Arrays.asList(firstStubhubListings.listingPrice.amount, secondStubhubListing.listingPrice.amount)
                .standardDeviation().toBigDecimal()

        assertThat(actual.standardDeviation).isEqualTo(standardDeviation)
    }

    @Test
    internal fun `should get number  of listing prices from stubhub when analyzing an event`() {
        val actual = subject.analyzeListings(asList(firstStubhubListings, secondStubhubListing))

        val count = Arrays.asList(firstStubhubListings.listingPrice.amount, secondStubhubListing.listingPrice.amount)
                .size

        assertThat(actual.count).isEqualTo(count)
    }


    @Test
    internal fun `should get median of listing prices from stubhub when analyzing an event`() {
        val actual = subject.analyzeListings(asList(firstStubhubListings, secondStubhubListing))

        val median = Arrays.asList(firstStubhubListings.listingPrice.amount, secondStubhubListing.listingPrice.amount)
                .median().toBigDecimal()

        assertThat(actual.median).isEqualTo(median)
    }
}

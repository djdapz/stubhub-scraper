package com.djdapz.stubhub.domain;

import static com.djdapz.stubhub.util.RandomKt.randomProcessedListing;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ProcessedListingTest {

    @Test
    public void should(){
        ProcessedListing processedListing = randomProcessedListing();
        processedListing.isGa();
    }
}
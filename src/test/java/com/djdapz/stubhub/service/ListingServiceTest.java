package com.djdapz.stubhub.service;

import com.djdapz.stubhub.config.UrlConfig;
import com.djdapz.stubhub.domain.Listing;
import com.djdapz.stubhub.domain.ListingResponse;
import com.djdapz.stubhub.domain.ProcessedListing;
import com.djdapz.stubhub.repository.ListingRepository;
import lombok.experimental.FieldDefaults;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URL;
import java.time.OffsetDateTime;
import java.util.List;

import static com.djdapz.stubhub.util.Random.*;
import static com.djdapz.stubhub.util.TestingUtil.verifyJsonGetObject;
import static java.util.Arrays.asList;
import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ListingServiceTest {
    RestTemplate restTemplate = mock(RestTemplate.class);
    UrlConfig urlConfig = mock(UrlConfig.class);
    ListingRepository listingRepository = mock(ListingRepository.class);
    TimeService timeService = mock(TimeService.class);
    ListingService subject = new ListingService(restTemplate, urlConfig, listingRepository, timeService);

    Integer eventId = randomInt();
    URL stubhubUrl = randomUrl();
    OffsetDateTime asOfDate = randomOffsetDateTime();
    Listing firstListing = randomListing();
    Listing secondListing = randomListing();

    ListingResponse listingResponse = randomListingResponse()
            .withListing(asList(firstListing, secondListing))
            .withTotalListings(2);
    ResponseEntity<ListingResponse> response = new ResponseEntity<>(listingResponse, HttpStatus.OK);


    ProcessedListing firstProcessedListing = new ProcessedListing(this.firstListing, asOfDate, listingResponse.getEventId());
    ProcessedListing secondProcessedListing = new ProcessedListing(this.secondListing, asOfDate, listingResponse.getEventId());

    @Before
    public void setUp() {
        when(urlConfig.getStubhubListingUrl()).thenReturn(stubhubUrl);
        when(timeService.now()).thenReturn(asOfDate);
        //noinspection unchecked
        when(restTemplate.exchange(anyString(), any(), any(), any(Class.class)))
                .thenReturn(response);

    }

    @Test
    public void shouldCallRestTemplate() {
        subject.getListingFor(eventId);
        verifyJsonGetObject(
                restTemplate,
                getUrl(),
                ListingResponse.class
        );
    }


    @Test
    public void shouldCallUrlConfig() {
        subject.getListingFor(eventId);
        verify(urlConfig).getStubhubListingUrl();
    }

    @Test
    public void shouldReturnListOfListings() {
        List<ProcessedListing> actual = subject.getListingFor(eventId);
        assertThat(actual).containsExactlyInAnyOrder(firstProcessedListing, secondProcessedListing);
    }

    @Test
    public void shouldPersistListingsInDB() {
        subject.getListingFor(eventId);

        verify(listingRepository).saveListing(firstProcessedListing);
        verify(listingRepository).saveListing(secondProcessedListing);
    }


    private String getUrl() {
        return UriComponentsBuilder
                .fromHttpUrl(stubhubUrl.toExternalForm())
                .queryParam("eventid", eventId)
                .toUriString();
    }
}
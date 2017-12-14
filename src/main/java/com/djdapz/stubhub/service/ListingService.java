package com.djdapz.stubhub.service;

import com.djdapz.stubhub.config.UrlConfig;
import com.djdapz.stubhub.domain.ListingResponse;
import com.djdapz.stubhub.domain.ProcessedListing;
import com.djdapz.stubhub.repository.ListingRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.djdapz.stubhub.util.RestUtil.getJsonObject;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ListingService {
    RestTemplate restTemplate;
    UrlConfig urlConfig;
    ListingRepository listingRepository;
    TimeService timeService;

    public List<ProcessedListing> getListingFor(final Integer eventId) {
        final OffsetDateTime now = timeService.now();
        ListingResponse listingResponse = getJsonObject(restTemplate, getUrl(eventId), ListingResponse.class);
        return listingResponse
                .getListing()
                .stream()
                .map(listing -> new ProcessedListing(listing, now, listingResponse.getEventId()))
                .peek(listingRepository::saveListing)
                .collect(Collectors.toList());
    }

    private String getUrl(Integer eventId) {
        return UriComponentsBuilder
                .fromHttpUrl(urlConfig.getStubhubListingUrl().toExternalForm())
                .queryParam("eventid", eventId)
                .toUriString();
    }

}

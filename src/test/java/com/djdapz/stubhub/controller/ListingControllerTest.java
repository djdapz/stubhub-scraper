package com.djdapz.stubhub.controller;

import com.djdapz.stubhub.domain.ProcessedListing;
import com.djdapz.stubhub.service.ListingService;
import lombok.experimental.FieldDefaults;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static com.djdapz.stubhub.config.JsonConfig.asObject;
import static com.djdapz.stubhub.util.Random.randomInt;
import static com.djdapz.stubhub.util.Random.randomProcessedListing;
import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ListingControllerTest {

    ListingService listingService = mock(ListingService.class);
    ListingController subject = new ListingController(listingService);

    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(subject).build();
    Integer eventId = randomInt();

    List<ProcessedListing> expectedListings = Arrays.asList(randomProcessedListing(), randomProcessedListing());

    @Before
    public void setUp() {
        when(listingService.getListingFor(any())).thenReturn(expectedListings);
    }

    @Test
    public void shouldReturn200() throws Exception {
        mockMvc.perform(
                get("/listings")
                        .param("eventId", eventId.toString())
        ).andExpect(status().isOk());
    }


    @Test
    public void shouldDelegateToListingSservice() throws Exception {
        mockMvc.perform(
                get("/listings")
                        .param("eventId", eventId.toString())
        );

        verify(listingService).getListingFor(eventId);
    }

    @Test
    public void shoulReturnListOfListinfs() throws Exception {
        String eventId = mockMvc.perform(
                get("/listings")
                        .param("eventId", this.eventId.toString()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProcessedListing[] actual = asObject(eventId, ProcessedListing[].class);

        assertThat(actual).containsExactlyElementsOf(expectedListings);
    }
}
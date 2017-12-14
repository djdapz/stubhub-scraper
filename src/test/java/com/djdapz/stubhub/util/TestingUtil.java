package com.djdapz.stubhub.util;

import com.djdapz.stubhub.domain.ListingResponse;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static lombok.AccessLevel.PRIVATE;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TestingUtil {

    public static void verifyJsonGetList(
            final RestTemplate restTemplate,
            final String url,
            final ParameterizedTypeReference parameterizedTypeReference
    ) {

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);

        //noinspection unchecked
        verify(restTemplate).exchange(
                eq(url),
                eq(HttpMethod.GET),
                eq(new HttpEntity<>(headers)),
                eq(parameterizedTypeReference)
        );
    }

    public static void verifyJsonGetObject(
            final RestTemplate restTemplate,
            final String url,
            final Class<ListingResponse> listingResponseClass
    ) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);

        //noinspection unchecked
        verify(restTemplate).exchange(
                eq(url),
                eq(HttpMethod.GET),
                eq(new HttpEntity<>(headers)),
                eq(listingResponseClass)
        );
    }
}

package com.djdapz.stubhub.util;

import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RestUtil {
//
//    public static ResponseEntity getJsonList(
//            final RestTemplate restTemplate,
//            final String url,
//            final ParameterizedTypeReference parameterizedTypeReference
//    ){
//
//        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//        headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
//
//        //noinspection unchecked
//        return restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                new HttpEntity<>(headers),
//                parameterizedTypeReference
//        );
//    }

    public static <T> T getJsonObject(
            final RestTemplate restTemplate,
            final String url,
            final Class<T> klass
    ) {

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                klass
        ).getBody();
    }
}

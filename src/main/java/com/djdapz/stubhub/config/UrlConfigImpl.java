package com.djdapz.stubhub.config;

import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.URL;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
@Configuration
public class UrlConfigImpl implements UrlConfig {

    private final String baseUrl;

    private final String listingsPath;

    public UrlConfigImpl(
            @Value("${stubhub.url}") String baseUrl,
            @Value("${stubhub.path.listing}") String listingsPath
    ) {
        this.baseUrl = baseUrl;
        this.listingsPath = listingsPath;
    }

    @Override
    @SneakyThrows
    public URL getStubhubListingUrl() {
        return new URL(baseUrl + listingsPath);
    }
}

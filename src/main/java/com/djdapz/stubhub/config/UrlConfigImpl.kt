package com.djdapz.stubhub.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.net.URL

@Configuration
open class UrlConfigImpl(
        @Value("\${stubhub.url}") private val baseUrl: String,
        @Value("\${stubhub.path.stubhubListing}") private val listingsPath: String
) : UrlConfig {
    override val stubhubListingUrl: URL
        get() = URL(baseUrl + listingsPath)
}

package com.djdapz.stubhub.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.net.URL

@Configuration
class UrlConfigImpl(
        @Value("\${stubhub.url}") private val baseUrl: String,
        @Value("\${stubhub.listing.path}") private val listingsPath: String,
        @Value("\${stubhub.listing.page-size}") private val listingsPageSize: Int

) : UrlConfig {
    override val pageSize: Int
        get() = listingsPageSize

    override val stubhubListingUrl: URL
        get() = URL(baseUrl + listingsPath)
}

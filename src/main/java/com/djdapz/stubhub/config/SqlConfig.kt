package com.djdapz.stubhub.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class SqlConfig(@Value("\${djdapz.stubhub.schema}") val schema: String)
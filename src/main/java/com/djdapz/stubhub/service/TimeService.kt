package com.djdapz.stubhub.service


import org.springframework.stereotype.Service

import java.time.LocalDateTime
import java.time.OffsetDateTime

@Service
open class TimeService {
    fun now(): LocalDateTime = OffsetDateTime.now().toLocalDateTime()
}

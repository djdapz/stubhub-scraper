package com.djdapz.stubhub.domain

import java.math.BigDecimal
import java.time.LocalDateTime

data class AnalyzedSample(
        val asOfDate: LocalDateTime,
        val count: Int,
        val average: BigDecimal,
        val minimum: BigDecimal,
        val maximum: BigDecimal,
        val median: BigDecimal,
        val standardDeviation: BigDecimal
)


package com.djdapz.stubhub.domain

import java.math.BigDecimal

data class Price (
    val amount: BigDecimal,
    val currency: String
)

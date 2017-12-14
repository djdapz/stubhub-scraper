package com.djdapz.stubhub.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.math.BigDecimal;

@Value
@AllArgsConstructor
@Getter
public class Price {
    BigDecimal amount;
    String currency;
}

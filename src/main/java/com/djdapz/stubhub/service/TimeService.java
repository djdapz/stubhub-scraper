package com.djdapz.stubhub.service;


import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Service

public class TimeService {
    LocalDateTime now() {
        return OffsetDateTime.now().toLocalDateTime();
    }
}

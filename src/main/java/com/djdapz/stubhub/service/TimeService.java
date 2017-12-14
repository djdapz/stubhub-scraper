package com.djdapz.stubhub.service;


import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service

public class TimeService {
    OffsetDateTime now() {
        return OffsetDateTime.now();
    }
}

package com.djdapz.stubhub.util

import com.djdapz.stubhub.domain.AnalysisType
import com.djdapz.stubhub.domain.StubhubEvent
import java.time.LocalDate

fun randomStubhubEvent(
        eventId: Int = randomInt(),
        name: String = randomString(),
        date: LocalDate = randomLocalDate(),
        analysisType: AnalysisType = randomAnalysisType()
) = StubhubEvent(
        eventId = eventId,
        name = name,
        date = date,
        analysisType = analysisType
)
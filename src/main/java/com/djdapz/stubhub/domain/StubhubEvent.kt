package com.djdapz.stubhub.domain

import java.time.LocalDate

class StubhubEvent(val eventId: Int, val name: String, val date: LocalDate, val analysisType: AnalysisType)
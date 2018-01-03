package com.djdapz.stubhub.controller

import com.djdapz.stubhub.service.AnalysisService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AnalysisController(val service: AnalysisService) {

    @GetMapping("/analysis")
    fun getAnalyzedSamples(@RequestParam("eventId") eventId: Int) = service.getAnalyzedSamples(eventId)
}
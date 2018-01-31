package com.djdapz.stubhub.repository.mapper

import com.djdapz.stubhub.domain.AnalysisType
import com.djdapz.stubhub.domain.StubhubEvent
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
class StubhubEventRowMapper : RowMapper<StubhubEvent> {
    override fun mapRow(rs: ResultSet?, rowNum: Int): StubhubEvent = StubhubEvent(
            eventId = rs!!.getInt("event_id"),
            name = rs.getString("event_name"),
            date = rs.getDate("event_date").toLocalDate(),
            analysisType = AnalysisType.valueOf(rs.getString("analysis_type"))
    )
}
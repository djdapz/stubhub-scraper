package com.djdapz.stubhub.repository.mapper

import com.djdapz.stubhub.domain.AnalyzedSample
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
class AnalyzedSampleRowMapper : RowMapper<AnalyzedSample> {
    override fun mapRow(rs: ResultSet?, rowNum: Int): AnalyzedSample = AnalyzedSample(
            asOfDate = rs!!.getTimestamp("as_of_date").toLocalDateTime(),
            count = rs.getLong("count").toInt(),
            average = rs.getDouble("avg").toBigDecimal(),
            maximum = rs.getDouble("max").toBigDecimal(),
            minimum = rs.getDouble("min").toBigDecimal(),
            standardDeviation = rs.getDouble("stddev").toBigDecimal()
    )
}
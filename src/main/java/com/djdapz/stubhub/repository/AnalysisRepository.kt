package com.djdapz.stubhub.repository

import com.djdapz.stubhub.config.SqlConfig
import com.djdapz.stubhub.domain.AnalyzedSample
import org.intellij.lang.annotations.Language
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.OffsetDateTime
import java.time.ZoneOffset.UTC


@Component
class AnalysisRepository(val jdbcTemplate: JdbcTemplate, final val sqlConfig: SqlConfig) {
    fun save(analyzedSample: AnalyzedSample, eventId: Int) {
        jdbcTemplate.update(INSERT_SQL,
                eventId,
                Timestamp.from(analyzedSample.asOfDate.toInstant(OffsetDateTime.now().offset)),
                analyzedSample.count,
                analyzedSample.average,
                analyzedSample.minimum,
                analyzedSample.maximum,
                analyzedSample.standardDeviation
        )
    }

    @Language("PostgreSQL")
    val INSERT_SQL = """INSERT INTO ${sqlConfig.schema}.analyzed_samples
        (
            event_id,
            as_of_date,
            count,
            average,
            minimum,
            maximum,
            standard_deviation
        )
        VALUES (?,?,?,?,?,?,?)
        """
}


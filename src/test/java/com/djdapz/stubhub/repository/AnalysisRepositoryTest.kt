package com.djdapz.stubhub.repository

import com.djdapz.stubhub.config.SqlConfig
import com.djdapz.stubhub.domain.AnalyzedSample
import com.djdapz.stubhub.util.randomAnalyzedSample
import com.djdapz.stubhub.util.randomInt
import com.djdapz.stubhub.util.randomString
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.intellij.lang.annotations.Language
import org.junit.Test
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.Timestamp
import java.time.OffsetDateTime

class AnalysisRepositoryTest {

    private val schema = randomString()

    private val sqlConfig: SqlConfig = mock {
        on { schema } doReturn schema
    }
    private val jdbcTemplate: JdbcTemplate = mock {
    }

    val subject = AnalysisRepository(jdbcTemplate, sqlConfig)

    private val eventId: Int = randomInt()

    private val analyzedSample = randomAnalyzedSample()

    @Test
    internal fun `should call jdbc with insert sql`() {
        subject.save(analyzedSample, eventId)
        verify(jdbcTemplate).update(INSERT_SQL,
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
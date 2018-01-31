package com.djdapz.stubhub.repository

import com.djdapz.stubhub.config.SqlConfig
import com.djdapz.stubhub.domain.StubhubEvent
import com.djdapz.stubhub.repository.mapper.StubhubEventRowMapper
import com.djdapz.stubhub.util.randomString
import com.djdapz.stubhub.util.randomStubhubEvent
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.Matchers.anyString
import org.springframework.jdbc.core.JdbcTemplate
import java.util.Arrays.asList

class EventRepositoryTest {
    private val schema = randomString()

    private val firstEvent: StubhubEvent = randomStubhubEvent()
    private val secondEvent: StubhubEvent = randomStubhubEvent()

    private val stubhubEventRowMapper = mock<StubhubEventRowMapper> {}

    private val jdbcTemplate = mock<JdbcTemplate> {
        on { query(anyString(), eq(stubhubEventRowMapper)) } doReturn asList(firstEvent, secondEvent)
    }

    private val sqlConfig = mock<SqlConfig> {
        on { schema } doReturn schema
    }

    private val subject = EventRepository(jdbcTemplate, sqlConfig, stubhubEventRowMapper)

    @Test
    internal fun `should query events from database`() {
        subject.getEvents()
        verify(jdbcTemplate).query(analysisSql, stubhubEventRowMapper)
    }

    @Test
    internal fun `should return events from database`() {
        val events: List<StubhubEvent> = subject.getEvents()
        assertThat(events).containsExactly(firstEvent, secondEvent)
    }

    private val analysisSql = "SELECT *  FROM $schema.stubhub_event"

}


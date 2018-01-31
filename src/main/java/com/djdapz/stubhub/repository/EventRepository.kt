package com.djdapz.stubhub.repository

import com.djdapz.stubhub.config.SqlConfig
import com.djdapz.stubhub.domain.StubhubEvent
import com.djdapz.stubhub.repository.mapper.StubhubEventRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class EventRepository(
        private val jdbcTemplate: JdbcTemplate,
        sqlConfig: SqlConfig,
        private val stubhubEventRowMapper: StubhubEventRowMapper) {

    fun getEvents(): List<StubhubEvent> =
            jdbcTemplate.query(
                    analysisSql,
                    stubhubEventRowMapper
            )

    private val analysisSql = "SELECT *  FROM ${sqlConfig.schema}.stubhub_event"

}

package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;

import javax.sql.DataSource;
import java.time.LocalDateTime;

/**
 * Created by ArtemAA on 24.04.2017.
 */
@Repository
@Profile(Profiles.POSTGRES_DB)
public class PostgresJdbcMealRepositoryImpl extends JdbcMealRepositoryImpl<LocalDateTime> {

    @Autowired
    public PostgresJdbcMealRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(dataSource, jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    protected LocalDateTime toTimestamp(LocalDateTime dateTime) {
        return dateTime;
    }

}

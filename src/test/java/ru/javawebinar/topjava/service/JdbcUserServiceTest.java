package ru.javawebinar.topjava.service;

import org.junit.BeforeClass;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

/**
 * Created by ArtemAA on 24.04.2017.
 */
@ActiveProfiles(Profiles.JDBC)
public class JdbcUserServiceTest extends UserServiceTest {

    @BeforeClass
    public static void setLog() {
        LOG = LoggerFactory.getLogger(JdbcUserServiceTest.class);
    }

}

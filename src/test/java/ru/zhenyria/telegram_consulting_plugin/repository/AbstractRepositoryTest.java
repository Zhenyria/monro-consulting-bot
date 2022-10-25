package ru.zhenyria.telegram_consulting_plugin.repository;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql({"/schema.sql", "/init.sql"})
public abstract class AbstractRepositoryTest {
}

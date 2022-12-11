package ru.zhenyria.monro_consulting_bot.test_config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "ru.zhenyria.monro_consulting_bot.repository")
@EnableTransactionManagement
public class DatasourceConfiguration {
}

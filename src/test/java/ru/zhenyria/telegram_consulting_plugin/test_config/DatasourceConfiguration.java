package ru.zhenyria.telegram_consulting_plugin.test_config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "ru.zhenyria.telegram_consulting_plugin.repository")
@EnableTransactionManagement
public class DatasourceConfiguration {
}

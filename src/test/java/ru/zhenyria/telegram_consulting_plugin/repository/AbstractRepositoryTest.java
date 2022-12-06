package ru.zhenyria.telegram_consulting_plugin.repository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.zhenyria.telegram_consulting_plugin.TelegramConsultingPluginApplication;
import ru.zhenyria.telegram_consulting_plugin.test_config.DatasourceConfiguration;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TelegramConsultingPluginApplication.class, DatasourceConfiguration.class})
public abstract class AbstractRepositoryTest {
}

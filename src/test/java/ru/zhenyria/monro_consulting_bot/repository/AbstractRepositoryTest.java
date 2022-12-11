package ru.zhenyria.monro_consulting_bot.repository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.zhenyria.monro_consulting_bot.MonroConsultingBotApplication;
import ru.zhenyria.monro_consulting_bot.test_config.DatasourceConfiguration;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {MonroConsultingBotApplication.class, DatasourceConfiguration.class})
public abstract class AbstractRepositoryTest {
}

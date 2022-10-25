package ru.zhenyria.telegram_consulting_plugin.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zhenyria.telegram_consulting_plugin.model.TextTemplate;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.zhenyria.telegram_consulting_plugin.test_util.TextTemplateRepositoryTestUtil.getAllKeys;
import static ru.zhenyria.telegram_consulting_plugin.test_util.TextTemplateRepositoryTestUtil.getByKey;

/**
 * Test for {@link TextTemplateRepository}
 */
class TextTemplateRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private TextTemplateRepository textTemplateRepository;

    @Test
    void getAllKeysTest() {
        Set<String> actualKeys = textTemplateRepository.getAllKeys();
        Set<String> expectedKeys = getAllKeys();

        assertEquals(expectedKeys, actualKeys);
    }

    @Test
    void getByKeyTest() {
        String key = getAllKeys().stream().findFirst().orElse(null);
        TextTemplate expectedTextTemplate = getByKey(key);
        TextTemplate actualTextTemplate = textTemplateRepository.getByKey(key);

        assertEquals(expectedTextTemplate, actualTextTemplate);
    }
}

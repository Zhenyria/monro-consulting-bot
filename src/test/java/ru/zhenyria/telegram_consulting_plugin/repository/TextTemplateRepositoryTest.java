package ru.zhenyria.telegram_consulting_plugin.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zhenyria.telegram_consulting_plugin.model.TextTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.zhenyria.telegram_consulting_plugin.test_util.TextTemplateRepositoryTestUtil.DEFAULT_TEMPLATE_CODE;
import static ru.zhenyria.telegram_consulting_plugin.test_util.TextTemplateRepositoryTestUtil.getDefaultTemplate;

/**
 * Test for {@link TextTemplateRepository}
 */
class TextTemplateRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private TextTemplateRepository textTemplateRepository;

    @Test
    void getByKeyTest() {
        TextTemplate expectedTextTemplate = getDefaultTemplate();
        TextTemplate actualTextTemplate = textTemplateRepository.getByKey(DEFAULT_TEMPLATE_CODE);

        assertEquals(expectedTextTemplate, actualTextTemplate);
    }
}

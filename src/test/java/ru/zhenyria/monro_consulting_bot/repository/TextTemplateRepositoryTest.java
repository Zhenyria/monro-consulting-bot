package ru.zhenyria.monro_consulting_bot.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zhenyria.monro_consulting_bot.model.TextTemplate;

import static ru.zhenyria.monro_consulting_bot.test_util.TextTemplateRepositoryTestUtil.DEFAULT_TEMPLATE_CODE;
import static ru.zhenyria.monro_consulting_bot.test_util.TextTemplateRepositoryTestUtil.getDefaultTemplate;

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

        Assertions.assertNotNull(actualTextTemplate);
        Assertions.assertEquals(expectedTextTemplate.getKey(), actualTextTemplate.getKey());
        Assertions.assertEquals(expectedTextTemplate.getText(), actualTextTemplate.getText());
    }
}

package ru.zhenyria.monro_consulting_bot.test_util;

import lombok.experimental.UtilityClass;
import ru.zhenyria.monro_consulting_bot.model.TextTemplate;
import ru.zhenyria.monro_consulting_bot.repository.TextTemplateRepository;

/**
 * Utility for {@link TextTemplateRepository} test
 */
@UtilityClass
public class TextTemplateRepositoryTestUtil {
    public static final String DEFAULT_TEMPLATE_CODE = "GREETING";

    private static final TextTemplate DEFAULT_TEMPLATE = new TextTemplate(DEFAULT_TEMPLATE_CODE,
                                                                          "Привет, я умный бот!");

    public static TextTemplate getDefaultTemplate() {
        return new TextTemplate(DEFAULT_TEMPLATE.getKey(), DEFAULT_TEMPLATE.getText());
    }
}

package ru.zhenyria.telegram_consulting_plugin.test_util;

import lombok.experimental.UtilityClass;
import ru.zhenyria.telegram_consulting_plugin.model.TextTemplate;
import ru.zhenyria.telegram_consulting_plugin.repository.TextTemplateRepository;

/**
 * Utility for {@link TextTemplateRepository} test
 */
@UtilityClass
public class TextTemplateRepositoryTestUtil {
    public static final String DEFAULT_TEMPLATE_CODE = "GREETING";

    private static final TextTemplate DEFAULT_TEMPLATE = new TextTemplate(DEFAULT_TEMPLATE_CODE, "Hi, how are you?");

    public static TextTemplate getDefaultTemplate() {
        return new TextTemplate(DEFAULT_TEMPLATE.getKey(), DEFAULT_TEMPLATE.getText());
    }
}

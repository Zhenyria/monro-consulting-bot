package ru.zhenyria.telegram_consulting_plugin.test_util;

import lombok.experimental.UtilityClass;
import ru.zhenyria.telegram_consulting_plugin.model.TextTemplate;
import ru.zhenyria.telegram_consulting_plugin.repository.TextTemplateRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility for {@link TextTemplateRepository} test
 */
@UtilityClass
public class TextTemplateRepositoryTestUtil {
    private static final TextTemplate GREETING_TEMPLATE = new TextTemplate("GREETING", "Hi, how are you?");
    private static final TextTemplate PRODUCT_EXISTING_TEMPLATE =
            new TextTemplate("PRODUCT_EXISTING", "Product's count is %s");
    private static final TextTemplate FAREWELL_TEMPLATE = new TextTemplate("FAREWELL", "Good bye!");

    private static final List<TextTemplate> TEXT_TEMPLATES =
            List.of(GREETING_TEMPLATE, PRODUCT_EXISTING_TEMPLATE, FAREWELL_TEMPLATE);

    public static Set<String> getAllKeys() {
        return TEXT_TEMPLATES
                .stream()
                .map(TextTemplate::getKey)
                .collect(Collectors.toSet());
    }

    public static TextTemplate getByKey(String key) {
        return TEXT_TEMPLATES
                .stream()
                .filter(tt -> tt.getKey().equals(key))
                .findFirst()
                .orElse(null);
    }
}

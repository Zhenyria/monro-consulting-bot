package ru.zhenyria.telegram_consulting_plugin.test_util;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TextTemplateRepositoryTestUtil {
  TextTemplate GREETING_TEMPLATE = new TextTemplate(null, "GREETING", "Hi, how are you?");
  TextTemplate PRODUCT_EXISTING_TEMPLATE =
      new TextTemplate(null, "PRODUCT_EXISTING", "Product's count is %s");
  TextTemplate FAREWELL_TEMPLATE = new TextTemplate(null, "FAREWELL", "Good bye!");

  List<TextTemplate> TEXT_TEMPLATES =
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

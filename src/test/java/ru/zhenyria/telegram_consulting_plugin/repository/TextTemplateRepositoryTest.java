package ru.zhenyria.telegram_consulting_plugin.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.zhenyria.telegram_consulting_plugin.test_util.TextTemplateRepositoryTestUtil.getAllKeys;
import static ru.zhenyria.telegram_consulting_plugin.test_util.TextTemplateRepositoryTestUtil.getByKey;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ru.zhenyria.telegram_consulting_plugin.model.TextTemplate;

import java.util.Set;

/**
 * Test for {@link TextTemplateRepository}
 */
@DataJpaTest
@Sql({"/schema.sql", "/init.sql"})
class TextTemplateRepositoryTest {

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

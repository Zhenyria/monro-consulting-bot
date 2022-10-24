package ru.zhenyria.telegram_consulting_plugin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.zhenyria.telegram_consulting_plugin.model.TextTemplate;

import java.util.Set;

@Repository
public interface TextTemplateRepository extends JpaRepository<TextTemplate, Integer> {

  TextTemplate getByKey(String key);

  @Query("SELECT tt.key FROM TextTemplate tt")
  Set<String> getAllKeys();
}

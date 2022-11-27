package ru.zhenyria.telegram_consulting_plugin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zhenyria.telegram_consulting_plugin.model.TextTemplate;

@Repository
public interface TextTemplateRepository extends JpaRepository<TextTemplate, Integer> {
    TextTemplate getByKey(String key);
}

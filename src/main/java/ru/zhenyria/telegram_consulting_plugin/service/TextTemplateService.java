package ru.zhenyria.telegram_consulting_plugin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zhenyria.telegram_consulting_plugin.model.TextTemplate;
import ru.zhenyria.telegram_consulting_plugin.repository.TextTemplateRepository;
import ru.zhenyria.telegram_consulting_plugin.util.TextTemplateKey;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TextTemplateService {
    private final TextTemplateRepository textTemplateRepository;

    public String getByKey(TextTemplateKey key) {
        return Optional
                .ofNullable(textTemplateRepository.getByKey(key.getKey()))
                .map(TextTemplate::getText)
                .orElse(null);
    }
}

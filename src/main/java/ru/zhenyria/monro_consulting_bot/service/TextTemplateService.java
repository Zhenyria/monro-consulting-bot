package ru.zhenyria.monro_consulting_bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zhenyria.monro_consulting_bot.model.TextTemplate;
import ru.zhenyria.monro_consulting_bot.repository.TextTemplateRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TextTemplateService {
    private final TextTemplateRepository textTemplateRepository;

    public String getByKey(String key) {
        return Optional
                .ofNullable(key)
                .map(textTemplateRepository::getByKey)
                .map(TextTemplate::getText)
                .orElse(null);
    }
}

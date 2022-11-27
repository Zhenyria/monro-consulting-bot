package ru.zhenyria.telegram_consulting_plugin.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The key of text template
 */
@RequiredArgsConstructor
@Getter
public enum TextTemplateKey {
    GREETING("GREETING"),
    GREETING_FOR_RETURNED("GREETING_FOR_RETURNED");

    private final String key;
}

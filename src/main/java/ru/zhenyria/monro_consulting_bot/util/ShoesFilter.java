package ru.zhenyria.monro_consulting_bot.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * The type of shoes filtering
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public enum ShoesFilter {
    MODEL("По типу"),
    SEASON("По сезону");

    String localizedMessage;
}

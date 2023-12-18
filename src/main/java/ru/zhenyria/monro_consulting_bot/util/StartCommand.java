package ru.zhenyria.monro_consulting_bot.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * The command for telegram's bot
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public enum StartCommand {
    GET_CHAT_MEMBERS_TOTAL_COUNT("members_count", "Получить общее количество пользователей"),
    GET_RECOMMENDED_SHOES("recommended_shoes", "Получить рекомендуемую обувь"),
    GET_WISH_LIST("wish_list", "Получить список желаемого"),
    START_TRYING_ON_SHOES("size", "Установить свой размер");

    String command;
    String description;
}

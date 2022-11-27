package ru.zhenyria.telegram_consulting_plugin.util;

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
public enum Command {
    GET_CHAT_MEMBERS_TOTAL_COUNT("members_count", "Total amount of all chat members");

    String command;
    String description;
}

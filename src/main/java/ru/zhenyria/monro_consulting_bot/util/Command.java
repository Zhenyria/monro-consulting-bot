package ru.zhenyria.monro_consulting_bot.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

/**
 * The command for telegram's bot
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public enum Command {
    ADDRESS("address", "Shop address"),
    DELIVERY("delivery", "Available delivery ways"),
    FITTING("fitting", "How to try on shoes"),
    GET_CHAT_MEMBERS_TOTAL_COUNT("members_count", "Total amount of all chat members"),
    REQUISITES("requisites", "Company's requisites");

    private static final Set<Command> SPECIAL_COMMANDS = Set.of(GET_CHAT_MEMBERS_TOTAL_COUNT);

    String command;
    String description;

    /**
     * Checks whether the command is special or not. Usual command just provide text template but special commands can
     * be handles by special way
     *
     * @return true if the command is special
     */
    public boolean isSpecial() {
        return SPECIAL_COMMANDS.contains(this);
    }
}

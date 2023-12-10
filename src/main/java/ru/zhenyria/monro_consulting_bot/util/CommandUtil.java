package ru.zhenyria.monro_consulting_bot.util;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

@UtilityClass
public class CommandUtil {
    public static final String COMMAND_SYMBOL = "/";

    /**
     * Check that expected command is received
     *
     * @param update          the update
     * @param expectedCommand expected command
     * @return true if expected command was received else false
     */
    public static boolean checkCommandIsSuitable(Update update, StartCommand expectedCommand) {
        return Objects.equals(update.getMessage().getText(), COMMAND_SYMBOL + expectedCommand.getCommand());
    }
}

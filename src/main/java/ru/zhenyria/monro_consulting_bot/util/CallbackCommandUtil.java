package ru.zhenyria.monro_consulting_bot.util;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@UtilityClass
public class CallbackCommandUtil {
    public static final String CALLBACK_DATA_SPLITTER = " ";

    private static final Pattern CALLBACK_DATA_PATTERN = Pattern.compile("^/([a-z]+) (.*$)");
    private static final int CALLBACK_DATA_PATTERN_COMMAND_GROUP = 1;
    private static final int CALLBACK_DATA_PATTERN_DATA_GROUP = 2;

    /**
     * Check that expected command is received
     *
     * @param update                 the update
     * @param expectedInitialCommand expected initial command
     * @return true if the expected initial command was an initializer of the current command
     */
    public static boolean checkCommandIsSuitable(Update update, String expectedInitialCommand) {
        return Optional.of(update)
                       .map(Update::getCallbackQuery)
                       .map(CallbackQuery::getData)
                       .map(callbackData -> {
                           var matcher = CALLBACK_DATA_PATTERN.matcher(callbackData);
                           if (!matcher.matches()) {
                               return null;
                           }
                           return matcher.group(CALLBACK_DATA_PATTERN_COMMAND_GROUP);
                       })
                       .map(initialCommand -> Objects.equals(expectedInitialCommand, initialCommand))
                       .orElse(false);
    }

    /**
     * Extract parameters from callback data
     *
     * @param update the update
     * @return parameters as array of {@code String}
     */
    public static String[] parseCallbackData(Update update) {
        return Optional.of(update)
                       .map(Update::getCallbackQuery)
                       .map(CallbackQuery::getData)
                       .map(callbackData -> {
                           var matcher = CALLBACK_DATA_PATTERN.matcher(callbackData);
                           if (!matcher.matches()) {
                               return null;
                           }
                           return matcher.group(CALLBACK_DATA_PATTERN_DATA_GROUP).split(CALLBACK_DATA_SPLITTER);
                       })
                       .orElse(new String[0]);
    }
}

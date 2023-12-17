package ru.zhenyria.monro_consulting_bot.util;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@UtilityClass
public class KeyboardUtil {
    public static final int DEFAULT_KEYBOARD_BUTTONS_IN_LINE_COUNT = 4;

    public static <T> InlineKeyboardMarkup getInlineKeyboard(List<T> elements,
                                                             Function<T, String> textProvider,
                                                             Function<T, String> callbackDataProvider) {
        var buttons = getInlineKeyboardButtons(elements, textProvider, callbackDataProvider);
        return getInlineKeyboard(buttons);
    }

    public static InlineKeyboardMarkup getInlineKeyboard(List<InlineKeyboardButton> buttons) {
        List<List<InlineKeyboardButton>> linedButtons = new ArrayList<>();

        // add first row
        linedButtons.add(new ArrayList<>());
        for (int i = 0, columnIndex = 0, rowIndex = 0; i < buttons.size(); i++, columnIndex++) {
            if (columnIndex == DEFAULT_KEYBOARD_BUTTONS_IN_LINE_COUNT) {
                linedButtons.add(new ArrayList<>());
                rowIndex++;
                columnIndex = 0;
            }
            linedButtons.get(rowIndex).add(buttons.get(i));
        }

        return new InlineKeyboardMarkup(linedButtons);
    }

    private static <T> List<InlineKeyboardButton> getInlineKeyboardButtons(List<T> elements,
                                                                           Function<T, String> textProvider,
                                                                           Function<T, String> callbackDataProvider) {
        return elements.stream()
                       .map(element -> {
                           var button = new InlineKeyboardButton();
                           button.setText(textProvider.apply(element));
                           button.setCallbackData(callbackDataProvider.apply(element));
                           return button;
                       })
                       .collect(Collectors.toList());
    }
}

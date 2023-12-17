package ru.zhenyria.monro_consulting_bot.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public enum CallbackCommand {
    SET_FEET_GIRTH(StartCommand.START_TRYING_ON_SHOES.getCommand(), "girth"),
    CHOOSE_SEASON(StartCommand.GET_RECOMMENDED_SHOES.getCommand(), "season"),
    CHOOSE_SHOES_MODEL(StartCommand.GET_RECOMMENDED_SHOES.getCommand(), "shoes_model");

    String initialCommand;
    String command;
}

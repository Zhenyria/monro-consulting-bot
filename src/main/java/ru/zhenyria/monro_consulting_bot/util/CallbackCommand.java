package ru.zhenyria.monro_consulting_bot.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public enum CallbackCommand {
    ADD_TO_WISH(null, "add_to_wish"),
    CHOOSE_SEASON(StartCommand.GET_RECOMMENDED_SHOES.getCommand(), "season"),
    CHOOSE_SHOES_MODEL(StartCommand.GET_RECOMMENDED_SHOES.getCommand(), "shoes_model"),
    GET_FROM_WISH_LIST(StartCommand.GET_WISH_LIST.getCommand(), "wished_shoes"),
    REMOVE_FROM_WISH_LIST(GET_FROM_WISH_LIST.getCommand(), "remove_from_wishlist"),
    SET_FEET_GIRTH(StartCommand.START_TRYING_ON_SHOES.getCommand(), "girth");

    String initialCommand;
    String command;
}

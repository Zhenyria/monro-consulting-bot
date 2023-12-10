package ru.zhenyria.monro_consulting_bot.util;

import org.mapstruct.Mapper;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.Collection;
import java.util.List;

/**
 * The mapper for {@link StartCommand}
 */
@Mapper(componentModel = "spring")
public interface StartCommandMapper {

    /**
     * Maps {@link StartCommand} instance to {@link BotCommand} instance
     *
     * @param command {@link StartCommand} instance
     * @return {@link BotCommand} instance
     */
    BotCommand mapToBotCommand(StartCommand command);

    /**
     * Maps list of {@link StartCommand} instance to list of {@link BotCommand} instance
     *
     * @param commands the list of {@link StartCommand} instance
     * @return list of {@link BotCommand} instance
     */
    List<BotCommand> mapToBotCommand(Collection<? extends StartCommand> commands);
}

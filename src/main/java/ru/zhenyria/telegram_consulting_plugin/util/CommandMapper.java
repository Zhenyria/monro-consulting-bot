package ru.zhenyria.telegram_consulting_plugin.util;

import org.mapstruct.Mapper;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.Collection;
import java.util.List;

/**
 * The mapper for {@link Command}
 */
@Mapper(componentModel = "spring")
public interface CommandMapper {

    /**
     * Maps {@link Command} instance to {@link BotCommand} instance
     *
     * @param command {@link Command} instance
     * @return {@link BotCommand} instance
     */
    BotCommand mapToBotCommand(Command command);

    /**
     * Maps list of {@link Command} instance to list of {@link BotCommand} instance
     *
     * @param commands the list of {@link Command} instance
     * @return list of {@link BotCommand} instance
     */
    List<BotCommand> mapToBotCommand(Collection<? extends Command> commands);
}

package ru.zhenyria.monro_consulting_bot.service.update_processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.zhenyria.monro_consulting_bot.service.CustomerService;
import ru.zhenyria.monro_consulting_bot.service.TextTemplateService;
import ru.zhenyria.monro_consulting_bot.util.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static ru.zhenyria.monro_consulting_bot.util.Command.GET_CHAT_MEMBERS_TOTAL_COUNT;

/**
 * The service handles commands
 */
@Service
@RequiredArgsConstructor
public class CommandService implements UpdateProcessableService {
    private static final String COMMAND_SYMBOL = "/";

    private final CustomerService customerService;
    private final TextTemplateService textTemplateService;

    @Override
    public boolean isServiceSuitable(Update update) {
        return Optional.of(update)
                .map(Update::getMessage)
                .map(Message::isCommand)
                .orElse(false);
    }

    @Override
    public Map<Predicate<Update>, Function<Update, SendMessage>> getUpdateHandlers() {
        Map<Predicate<Update>, Function<Update, SendMessage>> updateHandlers = new HashMap<>();

        updateHandlers.put(
                update -> checkCommandIsSuitable(update, GET_CHAT_MEMBERS_TOTAL_COUNT),
                update -> createMessageForChat(
                        getChatId(update),
                        String.format("Chat members total count: %d", customerService.getChatMembersTotalCount())
                )
        );

        // populate a map with update handlers for default consultation commands
        for (Command command : Command.values()) {
            if (command.isSpecial()) {
                continue;
            }

            updateHandlers.put(
                    update -> checkCommandIsSuitable(update, command),
                    update -> createMessageForChat(getChatId(update), textTemplateService.getByKey(command.name()))
            );
        }

        return updateHandlers;
    }

    /**
     * Gets chat's id
     *
     * @param update the update
     * @return chat's id as long
     */
    private long getChatId(Update update) {
        return update.getMessage().getChatId();
    }

    /**
     * Check that expected command is received
     *
     * @param update          the update
     * @param expectedCommand expected command
     * @return true if expected command was received else false
     */
    private static boolean checkCommandIsSuitable(Update update, Command expectedCommand) {
        return Objects.equals(update.getMessage().getText(), COMMAND_SYMBOL + expectedCommand.getCommand());
    }
}

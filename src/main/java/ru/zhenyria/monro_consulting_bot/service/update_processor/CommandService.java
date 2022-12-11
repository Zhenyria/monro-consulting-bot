package ru.zhenyria.monro_consulting_bot.service.update_processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.zhenyria.monro_consulting_bot.service.ChatMemberService;
import ru.zhenyria.monro_consulting_bot.util.Command;

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

    private final ChatMemberService chatMemberService;

    @Override
    public boolean isServiceSuitable(Update update) {
        return Optional.of(update)
                .map(Update::getMessage)
                .map(Message::isCommand)
                .orElse(false);
    }

    @Override
    public Map<Predicate<Update>, Function<Update, SendMessage>> getUpdateHandlers() {
        return Map.of(
                update -> checkCommandIsSuitable(update, GET_CHAT_MEMBERS_TOTAL_COUNT),
                update -> createMessageForChat(
                        getChatId(update),
                        String.format("Chat members total count: %d", chatMemberService.getChatMembersTotalCount())
                )
        );
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

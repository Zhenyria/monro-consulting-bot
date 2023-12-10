package ru.zhenyria.monro_consulting_bot.service.update_processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.zhenyria.monro_consulting_bot.service.CustomerService;
import ru.zhenyria.monro_consulting_bot.service.ScaleService;
import ru.zhenyria.monro_consulting_bot.util.CommandUtil;
import ru.zhenyria.monro_consulting_bot.util.KeyboardUtil;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static ru.zhenyria.monro_consulting_bot.util.StartCommand.GET_CHAT_MEMBERS_TOTAL_COUNT;
import static ru.zhenyria.monro_consulting_bot.util.StartCommand.START_TRYING_ON_SHOES;

/**
 * The service handles commands which require simple text response
 */
@Service
@RequiredArgsConstructor
public class StartCommandService implements UpdateProcessableService {
    private final CustomerService customerService;
    private final ScaleService scaleService;

    private final Map<Predicate<Update>, Function<Update, SendMessage>> updateHandlers = new HashMap<>();

    @PostConstruct
    private void init() {
        this.updateHandlers.put(
                update -> CommandUtil.checkCommandIsSuitable(update, GET_CHAT_MEMBERS_TOTAL_COUNT),
                update -> createTextMessageForChat(getChatId(update),
                                                   String.format("Общее количество пользователей: %d",
                                                                 customerService.getChatMembersTotalCount()))
        );

        this.updateHandlers.put(update -> CommandUtil.checkCommandIsSuitable(update, START_TRYING_ON_SHOES),
                                this::getMessageForStartTryingOnShoes);
    }

    @Override
    public boolean isServiceSuitable(Update update) {
        return Optional.of(update)
                       .map(Update::getMessage)
                       .map(Message::isCommand)
                       .orElse(false);
    }

    @Override
    public Map<Predicate<Update>, Function<Update, SendMessage>> getUpdateHandlers() {
        return Collections.unmodifiableMap(this.updateHandlers);
    }

    /**
     * Retrieves message which initiates trying on shoes operation
     *
     * @param update the update
     * @return a message with keyboard
     */
    private SendMessage getMessageForStartTryingOnShoes(Update update) {
        var keyboard = KeyboardUtil.getInlineKeyboard(scaleService.getAllLengths(),
                                                      String::valueOf,
                                                      size -> "%s%s %s".formatted(CommandUtil.COMMAND_SYMBOL,
                                                                                  START_TRYING_ON_SHOES.getCommand(),
                                                                                  size));
        var message = createTextMessageForChat(getChatId(update),
                                               """
                                                       Укажите длину своей стопы в сантиметрах. Производите замер \
                                                       вечером, в конце рабочего дня. Меряйте обувь с носками, с \
                                                       которыми вы планируете носить обувь. Например, для зимней обуви \
                                                       замер лучше производить с толстыми шерстяными носками.
                                                       Замер выполняется по линии от пятки до конца большого пальца. \
                                                       Если вы не нашли в представленном списке подходящие параметры, \
                                                       то выберите самый близкий к вашему с небольшим запасом в \
                                                       сторону увеличения""");
        message.setReplyMarkup(keyboard);
        return message;
    }

    /**
     * Retrieves chat's id
     *
     * @param update the update
     * @return chat's id as long
     */
    private long getChatId(Update update) {
        return update.getMessage().getChatId();
    }
}

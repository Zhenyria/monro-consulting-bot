package ru.zhenyria.monro_consulting_bot.service.update_processor;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.zhenyria.monro_consulting_bot.service.CustomerService;
import ru.zhenyria.monro_consulting_bot.service.ScaleService;
import ru.zhenyria.monro_consulting_bot.util.CallbackCommandUtil;
import ru.zhenyria.monro_consulting_bot.util.CommandUtil;
import ru.zhenyria.monro_consulting_bot.util.KeyboardUtil;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static ru.zhenyria.monro_consulting_bot.util.CallbackCommand.SET_FEET_GIRTH;

/**
 * The service handles callbacks <i>(results of start commands running)</i>
 */
@Service
@RequiredArgsConstructor
public class CallbackService implements UpdateProcessableService {
    private final CustomerService customerService;
    private final ScaleService scaleService;

    private final Map<Predicate<Update>, Function<Update, SendMessage>> updateHandlers = new HashMap<>();

    @PostConstruct
    private void init() {
        this.updateHandlers.put(
                update -> CallbackCommandUtil.checkCommandIsSuitable(update, SET_FEET_GIRTH.getInitialCommand()),
                this::getMessageForContinueTryingOnShoes
        );

        this.updateHandlers.put(
                update -> CallbackCommandUtil.checkCommandIsSuitable(update, SET_FEET_GIRTH.getCommand()),
                this::getMessageForFinishTryingOnShoes
        );
    }

    @Override
    public boolean isServiceSuitable(Update update) {
        return Optional.of(update)
                       .map(Update::getCallbackQuery)
                       .map(CallbackQuery::getData)
                       .isPresent();
    }

    @Override
    public Map<Predicate<Update>, Function<Update, SendMessage>> getUpdateHandlers() {
        return Collections.unmodifiableMap(this.updateHandlers);
    }

    /**
     * Retrieves chat's id
     *
     * @param update the update
     * @return chat's id as long
     */
    private long getChatId(Update update) {
        return update.getCallbackQuery().getMessage().getChatId();
    }

    /**
     * Retrieves user's id
     *
     * @param update the update
     * @return user's id as long or {@code null} if there is no data
     */
    private Long getChatMemberId(Update update) {
        return Optional.of(update)
                       .map(Update::getCallbackQuery)
                       .map(CallbackQuery::getFrom)
                       .map(User::getId)
                       .orElse(null);
    }

    /**
     * Retrieves message which continue trying on shoes operation
     *
     * @param update the update
     * @return a message with keyboard
     */
    private SendMessage getMessageForContinueTryingOnShoes(Update update) {
        val pickedLength = CallbackCommandUtil.parseCallbackData(update)[0];
        var keyboard = KeyboardUtil.getInlineKeyboard(scaleService.getAllGirths(),
                                                      String::valueOf,
                                                      girth -> "%s%s %s%s%s".formatted(
                                                              CommandUtil.COMMAND_SYMBOL,
                                                              SET_FEET_GIRTH.getCommand(),
                                                              pickedLength,
                                                              CallbackCommandUtil.CALLBACK_DATA_SPLITTER,
                                                              String.valueOf(girth)
                                                      ));
        var message = createTextMessageForChat(getChatId(update),
                                               """
                                                       Укажите обхват своей стопы в сантиметрах. Производите замер \
                                                       вечером, в конце рабочего дня. Меряйте обувь с носками, с \
                                                       которыми вы планируете носить обувь. Например, для зимней обуви \
                                                       замер лучше производить с толстыми шерстяными носками.
                                                       Замер выполняется в части подъёма стопы. Если вы не нашли в \
                                                       представленном списке подходящие параметры, то выберите самый \
                                                       близкий к вашему с небольшим запасом в сторону увеличения""");
        message.setReplyMarkup(keyboard);
        return message;
    }

    /**
     * Updates customer's foot scale data and retrieves success message
     *
     * @param update the update
     * @return a message
     */
    private SendMessage getMessageForFinishTryingOnShoes(Update update) {
        val footLengthIndex = 0;
        val footGirthIndex = 1;

        var params = CallbackCommandUtil.parseCallbackData(update);
        val footLength = new BigDecimal(params[footLengthIndex]);
        val footGirth = new BigDecimal(params[footGirthIndex]);

        var suitableScale = scaleService.getMostSuitable(footLength, footGirth);
        var customer = customerService.get(getChatMemberId(update));
        customer.setScale(suitableScale);
        customerService.save(customer);
        return createTextMessageForChat(getChatId(update),
                                        """
                                                Вы указали длину стопы в %s сантиметров и обхват стопы в %s \
                                                сантиметров. Мы определили, что у вас %d-й размер с %d-й полнотой (\
                                                средние значения для данного размера составляют длину стопы в %s \
                                                сантиметров и обхват в %s сантиметров)\
                                                """.formatted(footLength,
                                                              footGirth,
                                                              suitableScale.getSize(),
                                                              suitableScale.getVolume(),
                                                              suitableScale.getFootLength(),
                                                              suitableScale.getFootGirth()));
    }
}

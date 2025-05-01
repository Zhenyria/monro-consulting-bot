package ru.zhenyria.monro_consulting_bot.service.update_processor;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.zhenyria.monro_consulting_bot.model.ConsultationRequest;
import ru.zhenyria.monro_consulting_bot.model.Shoes;
import ru.zhenyria.monro_consulting_bot.service.ConsultationRequestService;
import ru.zhenyria.monro_consulting_bot.service.CustomerService;
import ru.zhenyria.monro_consulting_bot.service.ScaleService;
import ru.zhenyria.monro_consulting_bot.service.ShoesService;
import ru.zhenyria.monro_consulting_bot.util.CommandUtil;
import ru.zhenyria.monro_consulting_bot.util.KeyboardUtil;
import ru.zhenyria.monro_consulting_bot.util.ShoesFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.zhenyria.monro_consulting_bot.util.StartCommand.GET_CHAT_MEMBERS_TOTAL_COUNT;
import static ru.zhenyria.monro_consulting_bot.util.StartCommand.GET_RECOMMENDED_SHOES;
import static ru.zhenyria.monro_consulting_bot.util.StartCommand.GET_WISH_LIST;
import static ru.zhenyria.monro_consulting_bot.util.StartCommand.SEND_CONSULTATION_REQUEST;
import static ru.zhenyria.monro_consulting_bot.util.StartCommand.START_TRYING_ON_SHOES;

/**
 * The service handles commands which require simple text response
 */
@Service
@RequiredArgsConstructor
public class StartCommandService implements UpdateProcessableService {
    private final ConsultationRequestService consultationRequestService;
    private final CustomerService customerService;
    private final ScaleService scaleService;
    private final ShoesService shoesService;

    private final Map<Predicate<Update>, Function<Update, SendMessage>> updateHandlers = new HashMap<>();

    @PostConstruct
    private void init() {
        updateHandlers.putAll(Map.of(
                update -> CommandUtil.checkCommandIsSuitable(update, GET_CHAT_MEMBERS_TOTAL_COUNT),
                update -> createTextMessageForChat(getChatId(update),
                                                   String.format("Общее количество пользователей: %d",
                                                                 customerService.getChatMembersTotalCount())),
                update -> CommandUtil.checkCommandIsSuitable(update, START_TRYING_ON_SHOES),
                this::getMessageForStartTryingOnShoes,
                update -> CommandUtil.checkCommandIsSuitable(update, GET_RECOMMENDED_SHOES),
                this::getMessageForStartReceivingRecommendedShoes,
                update -> CommandUtil.checkCommandIsSuitable(update, GET_WISH_LIST),
                this::getMessageWithWishList,
                update -> CommandUtil.checkCommandIsSuitable(update, SEND_CONSULTATION_REQUEST),
                this::sendConsultationRequest
        ));
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

    @Override
    public Long getChatMemberId(Update update) {
        return Optional.of(update)
                       .map(Update::getMessage)
                       .map(Message::getFrom)
                       .map(User::getId)
                       .orElse(null);
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
                                               Укажите длину своей стопы в сантиметрах. Производите замер вечером, в \
                                               конце рабочего дня. Меряйте обувь с носками, с которыми вы планируете \
                                               носить обувь. Например, для зимней обуви замер лучше производить с \
                                               толстыми шерстяными носками.
                                               Замер выполняется по линии от пятки до конца большого пальца. Если вы \
                                               не нашли в представленном списке подходящие параметры, то выберите \
                                               самый близкий к вашему с небольшим запасом в сторону увеличения""");
        message.setReplyMarkup(keyboard);
        return message;
    }

    /**
     * Retrieves message which contains filter variants of shoes
     *
     * @param update the update
     * @return a message with keyboard
     */
    private SendMessage getMessageForStartReceivingRecommendedShoes(Update update) {
        val customerId = getChatMemberId(update);
        var customer = customerService.get(customerId);
        var scale = customer.getScale();

        if (scale == null) {
            return createTextMessageForChat(getChatId(update),
                                            """
                                            Невозможно сформировать список рекомендаций, так как вы не установили свой \
                                            размер""");
        }

        var keyboard = KeyboardUtil.getInlineKeyboard(
                Arrays.asList(ShoesFilter.values()),
                ShoesFilter::getLocalizedMessage,
                shoesFilter -> "%s%s %s".formatted(CommandUtil.COMMAND_SYMBOL,
                                                   GET_RECOMMENDED_SHOES.getCommand(),
                                                   shoesFilter.name())
        );

        var message = createTextMessageForChat(getChatId(update),
                                               "Каким образом вы желаете отфильтровать список обуви?");

        message.setReplyMarkup(keyboard);
        return message;
    }

    private SendMessage getMessageWithWishList(Update update) {
        var wishedShoes = shoesService.getAllFromWishList(getChatMemberId(update));

        val chatId = getChatId(update);
        if (wishedShoes.isEmpty()) {
            return createTextMessageForChat(chatId, "Вы ещё ничего не добавили в ваш список желаемого.");
        }

        List<List<InlineKeyboardButton>> linedButtons = new ArrayList<>();
        KeyboardUtil.getInlineKeyboardButtons(wishedShoes,
                                              shoes -> "%s %s".formatted(shoes.getModel().getLocalizedName(),
                                                                         shoes.getVendorCode()),
                                              shoes -> "%s%s %s".formatted(CommandUtil.COMMAND_SYMBOL,
                                                                           GET_WISH_LIST.getCommand(),
                                                                           shoes.getId()))
                    .forEach(button -> linedButtons.add(List.of(button)));

        var keyboard = new InlineKeyboardMarkup(linedButtons);

        var message = createTextMessageForChat(chatId,
                                               """
                                               Ниже представлен список артикулов обуви, добавленной вами в список \
                                               желаемого. Нажмите на интересующий вас артикул, чтобы получить более \
                                               подробную информацию.""");
        message.setReplyMarkup(keyboard);
        return message;
    }

    private SendMessage sendConsultationRequest(Update update) {
        val chatMemberId = getChatMemberId(update);

        var customer = customerService.getWithWishedShoes(chatMemberId);

        val chatId = getChatId(update);
        val phoneNumber = customer.getPhoneNumber();
        if (phoneNumber == null) {
            var message = createTextMessageForChat(chatId,
                                                   "Для получения консультации обновите ваши контактные данные");

            var contactRequestButton = new KeyboardButton();
            contactRequestButton.setText("Нажмите, чтобы обновить ваши контактные данные \uD83D\uDCDE");
            contactRequestButton.setRequestContact(true);

            var keyboard = new ReplyKeyboardMarkup(List.of(new KeyboardRow(List.of(contactRequestButton))));

            message.setReplyMarkup(keyboard);
            return message;
        }

        var wishedShoes = customer.getWishedShoes();

        var shoesToBeAddedToConsultation = new ArrayList<Shoes>();
        for (var shoes : customer.getWishedShoes()) {
            shoesToBeAddedToConsultation.add(shoesService.getReference(shoes.getId()));
        }

        var consultationRequest = new ConsultationRequest(customer.getChatMemberId(), shoesToBeAddedToConsultation, null);
        consultationRequestService.save(consultationRequest);

        val message = wishedShoes.isEmpty()
                      ? """
                        Запрос на консультацию успешно отправлен!
                        
                        Пожалуйста, ожидайте обратного звонка ❤
                        """
                      : """
                        Запрос на консультацию успешно отправлен!
                        
                        Наш менеджер выяснит всё по поводу обуви, добавленной вами в список желаемого (%s), и \
                        позвонит вам в ближайшее время!
                        
                        Пожалуйста, ожидайте обратного звонка ❤
                        """.formatted(wishedShoes.stream()
                                                 .map(shoes -> "%s %s".formatted(shoes.getModel().getLocalizedName(),
                                                                                 shoes.getVendorCode()))
                                                 .collect(Collectors.joining(", ")));

        return createTextMessageForChat(chatId, message);
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

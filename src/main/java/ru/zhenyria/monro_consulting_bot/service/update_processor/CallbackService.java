package ru.zhenyria.monro_consulting_bot.service.update_processor;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.zhenyria.monro_consulting_bot.model.Season;
import ru.zhenyria.monro_consulting_bot.model.Shoes;
import ru.zhenyria.monro_consulting_bot.model.ShoesModel;
import ru.zhenyria.monro_consulting_bot.service.CustomerService;
import ru.zhenyria.monro_consulting_bot.service.ScaleService;
import ru.zhenyria.monro_consulting_bot.service.SeasonService;
import ru.zhenyria.monro_consulting_bot.service.ShoesModelService;
import ru.zhenyria.monro_consulting_bot.service.ShoesService;
import ru.zhenyria.monro_consulting_bot.util.CallbackCommandUtil;
import ru.zhenyria.monro_consulting_bot.util.CommandUtil;
import ru.zhenyria.monro_consulting_bot.util.KeyboardUtil;
import ru.zhenyria.monro_consulting_bot.util.ShoesFilter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static ru.zhenyria.monro_consulting_bot.util.CallbackCommand.ADD_TO_WISH;
import static ru.zhenyria.monro_consulting_bot.util.CallbackCommand.CHOOSE_SEASON;
import static ru.zhenyria.monro_consulting_bot.util.CallbackCommand.CHOOSE_SHOES_MODEL;
import static ru.zhenyria.monro_consulting_bot.util.CallbackCommand.GET_FROM_WISH_LIST;
import static ru.zhenyria.monro_consulting_bot.util.CallbackCommand.REMOVE_FROM_WISH_LIST;
import static ru.zhenyria.monro_consulting_bot.util.CallbackCommand.SET_FEET_GIRTH;

/**
 * The service handles callbacks <i>(results of start commands running)</i>
 */
@Service
@RequiredArgsConstructor
public class CallbackService implements UpdateProcessableService {
    private final CustomerService customerService;
    private final ScaleService scaleService;
    private final SeasonService seasonService;
    private final ShoesModelService shoesModelService;
    private final ShoesService shoesService;

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

        this.updateHandlers.put(
                update -> CallbackCommandUtil.checkCommandIsSuitable(update, CHOOSE_SEASON.getInitialCommand())
                          || CallbackCommandUtil.checkCommandIsSuitable(update, CHOOSE_SHOES_MODEL.getInitialCommand()),
                this::getMessageForContinueReceivingRecommendedShoes
        );

        this.updateHandlers.put(
                update -> CallbackCommandUtil.checkCommandIsSuitable(update, CHOOSE_SEASON.getCommand()),
                this::getMessageWithRandomShoesBySeason
        );

        this.updateHandlers.put(
                update -> CallbackCommandUtil.checkCommandIsSuitable(update, CHOOSE_SHOES_MODEL.getCommand()),
                this::getMessageWithRandomShoesByShoesModel
        );

        this.updateHandlers.put(
                update -> CallbackCommandUtil.checkCommandIsSuitable(update, ADD_TO_WISH.getCommand()),
                this::getMessageForAddingShoesToWishList
        );

        this.updateHandlers.put(
                update -> CallbackCommandUtil.checkCommandIsSuitable(update, GET_FROM_WISH_LIST.getInitialCommand()),
                this::getMessageWithShoesFromWishList
        );

        this.updateHandlers.put(
                update -> CallbackCommandUtil.checkCommandIsSuitable(update, REMOVE_FROM_WISH_LIST.getCommand()),
                this::getMessageForShoesRemovedFromWishList
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

    @Override
    public Long getChatMemberId(Update update) {
        return Optional.of(update)
                       .map(Update::getCallbackQuery)
                       .map(CallbackQuery::getFrom)
                       .map(User::getId)
                       .orElse(null);
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

    /**
     * Retrieves message which contains variants of shoes filtering
     *
     * @param update thew update
     * @return a message with keyboard
     */
    private SendMessage getMessageForContinueReceivingRecommendedShoes(Update update) {
        val shoesFilterParameterIndex = 0;
        var params = CallbackCommandUtil.parseCallbackData(update);

        val shoesFilter = ShoesFilter.valueOf(params[shoesFilterParameterIndex]);

        val chatId = getChatId(update);
        val customerId = getChatMemberId(update);
        return switch (shoesFilter) {
            case MODEL -> {
                var shoesModels = shoesModelService.getLocalizedNamesOfRelatedToShoes(customerId);

                var keyboard = KeyboardUtil.getInlineKeyboard(
                        shoesModels,
                        ShoesModel::getLocalizedName,
                        shoesModel -> "%s%s %s".formatted(CommandUtil.COMMAND_SYMBOL,
                                                          CHOOSE_SHOES_MODEL.getCommand(),
                                                          shoesModel.getName())
                );

                var message = createTextMessageForChat(chatId,
                                                       """
                                                               Выберите интересующую вас модель обуви (в списке только \
                                                               те модели, для которых в данный момент представлена \
                                                               обувь вашего размера).""");
                message.setReplyMarkup(keyboard);
                yield message;
            }
            case SEASON -> {
                var seasons = seasonService.getLocalizedNamesOfRelatedToShoesAvailableToCustomer(customerId);

                var keyboard = KeyboardUtil.getInlineKeyboard(seasons,
                                                              Season::getLocalizedName,
                                                              season -> "%s%s %s".formatted(CommandUtil.COMMAND_SYMBOL,
                                                                                            CHOOSE_SEASON.getCommand(),
                                                                                            season.getName()));

                var message = createTextMessageForChat(chatId,
                                                       """
                                                               Выберите интересующий вас сезон (в списке только те \
                                                               сезоны, для которых в данный момент представлена обувь \
                                                               вашего размера).""");
                message.setReplyMarkup(keyboard);
                yield message;
            }
        };
    }

    /**
     * Retrieves message which contains random shoes description
     *
     * @param update thew update
     * @return a message with keyboard
     */
    private SendMessage getMessageWithRandomShoesBySeason(Update update) {
        val seasonParameterIndex = 0;
        var params = CallbackCommandUtil.parseCallbackData(update);
        val seasonName = params[seasonParameterIndex];
        return getMessageWithRandomShoesBySeasonAndShoesModel(update, seasonName, null);
    }

    /**
     * Retrieves message which contains random shoes description
     *
     * @param update thew update
     * @return a message with keyboard
     */
    private SendMessage getMessageWithRandomShoesByShoesModel(Update update) {
        val shoesModelParameterIndex = 0;
        var params = CallbackCommandUtil.parseCallbackData(update);
        val shoesModel = params[shoesModelParameterIndex];
        return getMessageWithRandomShoesBySeasonAndShoesModel(update, null, shoesModel);
    }

    /**
     * Retrieves message which contains random shoes description
     *
     * @param update     the update
     * @param season     the season for shoes filter
     * @param shoesModel the model of shoes for shoes filter
     * @return a message with keyboard
     */
    private SendMessage getMessageWithRandomShoesBySeasonAndShoesModel(Update update,
                                                                       String season,
                                                                       String shoesModel) {
        var randomShoes = shoesService.getRandomByCustomerAndSeasonAndModel(getChatMemberId(update),
                                                                            season,
                                                                            shoesModel);

        if (randomShoes == null) {
            return createTextMessageForChat(getChatId(update), "Подходящей обуви по вашему размеру не найдено");
        }

        var message = createTextMessageForChat(getChatId(update), getShoesDescription(randomShoes));

        List<InlineKeyboardButton> buttons = new ArrayList<>();

        var addToWishButton = new InlineKeyboardButton();
        addToWishButton.setText("В желаемое \uD83D\uDC95");
        addToWishButton.setCallbackData("%s%s %d".formatted(CommandUtil.COMMAND_SYMBOL,
                                                            ADD_TO_WISH.getCommand(),
                                                            randomShoes.getId()));
        buttons.add(addToWishButton);

        var nextShoesButton = new InlineKeyboardButton();
        nextShoesButton.setText("Следующая модель");
        nextShoesButton.setCallbackData(update.getCallbackQuery().getData());
        buttons.add(nextShoesButton);

        var keyboard = KeyboardUtil.getInlineKeyboard(buttons);

        message.setReplyMarkup(keyboard);
        return message;
    }

    /**
     * Retrieves message about adding shoes to wish list
     *
     * @param update the update
     * @return a message
     */
    private SendMessage getMessageForAddingShoesToWishList(Update update) {
        val shoesVendorCodeParameterIndex = 0;
        val shoesId = Integer.parseInt(CallbackCommandUtil.parseCallbackData(update)[shoesVendorCodeParameterIndex]);

        val chatId = getChatId(update);

        var shoes = shoesService.getById(shoesId);
        if (shoes == null) {
            return createTextMessageForChat(chatId, "Ошибка. Модель не найдена.");
        }

        val isShoesAdded = customerService.addShoesToWishList(chatId, shoes);
        return createTextMessageForChat(chatId,
                                        isShoesAdded ? "Модель успешно добавлена в список желаемого!"
                                                     : "Модель уже добавлена в список желаемого.");
    }

    /**
     * Retrieves message with shoes from wish list
     *
     * @param update the update
     * @return a message with keyboard
     */
    private SendMessage getMessageWithShoesFromWishList(Update update) {
        val shoesVendorCodeParameterIndex = 0;
        val shoesId = Integer.parseInt(CallbackCommandUtil.parseCallbackData(update)[shoesVendorCodeParameterIndex]);

        var shoes = shoesService.getById(shoesId);

        val chatId = getChatId(update);
        if (shoes == null) {
            return createTextMessageForChat(chatId, "Ошибка. Модель не найдена");
        }

        var message = createTextMessageForChat(chatId, getShoesDescription(shoes));

        var buttons = new ArrayList<InlineKeyboardButton>();
        var removeFromWishListButton = new InlineKeyboardButton();
        removeFromWishListButton.setText("Удалить из списка желаемого");
        removeFromWishListButton.setCallbackData("%s%s %d".formatted(CommandUtil.COMMAND_SYMBOL,
                                                                     REMOVE_FROM_WISH_LIST.getCommand(),
                                                                     shoesId));
        buttons.add(removeFromWishListButton);

        message.setReplyMarkup(KeyboardUtil.getInlineKeyboard(buttons));
        return message;
    }

    /**
     * Removes shoes from customer's wish list and retrieves message
     *
     * @param update the update
     * @return a message
     */
    private SendMessage getMessageForShoesRemovedFromWishList(Update update) {
        val shoesVendorCodeParameterIndex = 0;
        val shoesId = Integer.parseInt(CallbackCommandUtil.parseCallbackData(update)[shoesVendorCodeParameterIndex]);
        val customerId = getChatMemberId(update);

        customerService.removeShoesFromWishList(customerId, shoesId);

        return createTextMessageForChat(getChatId(update), "Модель успешно удалена из списка желаемого");
    }

    /**
     * Creates standard shoes text description
     *
     * @param shoes the shoes instance
     * @return a description
     */
    private static String getShoesDescription(Shoes shoes) {
        return """
                %s %s
                Артикул: %s
                
                %s
                
                Изображение: %s
                Страница в магазине: %s""".formatted(shoes.getModel().getLocalizedName(),
                                                     shoes.getName(),
                                                     shoes.getVendorCode(),
                                                     shoes.getDescription(),
                                                     shoes.getImageUrl(),
                                                     shoes.getUrl());
    }
}

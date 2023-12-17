package ru.zhenyria.monro_consulting_bot.service.update_processor;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * The interface of services which can handle telegram's updates
 */
interface UpdateProcessableService {

    /**
     * Checks that service can handle update
     *
     * @param update the update
     * @return true if the service is suitable else false
     */
    boolean isServiceSuitable(Update update);

    /**
     * Gets handlers for update
     *
     * @return map which contains predicate functions as keys and handling functions as values. Predicate function
     * checks that handler from value can be applied to update
     */
    Map<Predicate<Update>, Function<Update, SendMessage>> getUpdateHandlers();

    /**
     * Retrieves user's id
     *
     * @param update the update
     * @return user's id as long or {@code null} if there is no data
     */
    Long getChatMemberId(Update update);

    /**
     * Create simple reply message
     *
     * @param chatId chat's id
     * @param text   the message
     * @return reply message
     */
    default SendMessage createTextMessageForChat(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        return message;
    }
}

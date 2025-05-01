package ru.zhenyria.monro_consulting_bot.service.update_processor;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.zhenyria.monro_consulting_bot.service.CustomerService;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * The service handles updates of contact data
 */
@Service
@RequiredArgsConstructor
public class ContactService implements UpdateProcessableService {
    private final CustomerService customerService;

    @Override
    public boolean isServiceSuitable(Update update) {
        return Optional.of(update)
                       .map(Update::getMessage)
                       .map(Message::hasContact)
                       .orElse(Boolean.FALSE);
    }

    @Override
    public Map<Predicate<Update>, Function<Update, SendMessage>> getUpdateHandlers() {

        /*
         * the service is represented by the only one method so service's predicate works as predicate of the operation
         * either
         */
        return Map.of(update -> true, this::updateCustomerContactData);
    }

    @Override
    public Long getChatMemberId(Update update) {
        return Optional.of(update)
                       .map(Update::getMessage)
                       .map(Message::getFrom)
                       .map(User::getId)
                       .orElse(null);
    }

    private SendMessage updateCustomerContactData(Update update) {
        final Function<Function<Contact, String>, String> contactExtractor =
                contactDataExtractor -> Optional.ofNullable(update.getMessage())
                                                .map(Message::getContact)
                                                .map(contactDataExtractor)
                                                .orElse(null);

        val phoneNumber = contactExtractor.apply(Contact::getPhoneNumber);

        val chatId = getChatId(update);
        if (phoneNumber == null) {
            return createTextMessageForChat(chatId, "В вашем аккаунте не указан номер телефона");
        }

        val firstName = contactExtractor.apply(Contact::getFirstName);
        val lastName = contactExtractor.apply(Contact::getLastName);

        var customer = customerService.get(getChatMemberId(update));
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setPhoneNumber(phoneNumber);
        customerService.save(customer);

        return createTextMessageForChat(chatId,
                                        """
                                        Ваши контактные данные успешно обновлены:
                                        Имя: %s
                                        Фамилия: %s
                                        Номер телефона: %s""".formatted(firstName, lastName, phoneNumber));
    }

    /**
     * Retrieves chat's id
     *
     * @param update the update
     * @return chat's id as long
     */
    private static Long getChatId(Update update) {
        return Optional.ofNullable(update)
                       .map(Update::getMessage)
                       .map(Message::getChatId)
                       .orElse(null);
    }
}

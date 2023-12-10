package ru.zhenyria.monro_consulting_bot.service.update_processor;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import ru.zhenyria.monro_consulting_bot.service.CustomerService;
import ru.zhenyria.monro_consulting_bot.service.TextTemplateService;
import ru.zhenyria.monro_consulting_bot.util.UpdateMapper;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.telegram.telegrambots.meta.api.objects.MemberStatus.KICKED;
import static org.telegram.telegrambots.meta.api.objects.MemberStatus.LEFT;
import static org.telegram.telegrambots.meta.api.objects.MemberStatus.MEMBER;

/**
 * The service handles updates of chat's members statuses
 */
@Service
@RequiredArgsConstructor
class ChatMemberStatusService implements UpdateProcessableService {
    private final CustomerService customerService;
    private final TextTemplateService textTemplateService;

    private final UpdateMapper updateMapper;

    private final Map<Predicate<Update>, Function<Update, SendMessage>> updateHandlers = new HashMap<>();

    @PostConstruct
    private void init() {

        // Handler for new chat member
        updateHandlers.put(
                update -> checkChatMemberStatusChanging(update, LEFT, Collections.singletonList(MEMBER)),
                update -> {
                    customerService.save(updateMapper.mapToChatMember(update));
                    return createTextMessageForChat(getChatId(update), textTemplateService.getByKey("GREETING"));
                });

        // Handler for returned chat member
        updateHandlers.put(
                update -> checkChatMemberStatusChanging(update, KICKED, Collections.singletonList(MEMBER)),
                update -> {
                    customerService.save(updateMapper.mapToChatMember(update));
                    return createTextMessageForChat(
                            getChatId(update),
                            textTemplateService.getByKey("GREETING_FOR_RETURNED")
                    );
                });

        // Handler for left chat member
        updateHandlers.put(
                update -> checkChatMemberStatusChanging(update, MEMBER, List.of(LEFT, KICKED)),
                update -> {
                    customerService.removeByChatMemberId(updateMapper.mapToChatMember(update).getChatMemberId());
                    return null;
                }
        );
    }

    @Override
    public boolean isServiceSuitable(Update update) {
        return !Objects.equals(getOldStatus(update), getNewStatus(update));
    }

    @Override
    public Map<Predicate<Update>, Function<Update, SendMessage>> getUpdateHandlers() {
        return Collections.unmodifiableMap(this.updateHandlers);
    }

    /**
     * Check that expected chat member's status changing happened
     *
     * @param update              the update
     * @param expectedOldStatus   expected old status
     * @param expectedNewStatuses expected new status
     * @return true if expected changing happened else false
     */
    private boolean checkChatMemberStatusChanging(Update update,
                                                  String expectedOldStatus,
                                                  Collection<? extends String> expectedNewStatuses) {
        if (!Objects.equals(getOldStatus(update), expectedOldStatus)) {
            return false;
        }
        val actualNewStatus = getNewStatus(update);
        boolean isNewStatusChangingSuitable = false;
        for (String expectedNewStatus : expectedNewStatuses) {
            if (isNewStatusChangingSuitable) {
                break;
            }
            isNewStatusChangingSuitable = Objects.equals(actualNewStatus, expectedNewStatus);
        }

        return isNewStatusChangingSuitable;
    }

    /**
     * Extracts chat member's old status
     *
     * @param update the update
     * @return old status or null
     */
    private String getOldStatus(Update update) {
        return getStatus(update, ChatMemberUpdated::getOldChatMember);
    }

    /**
     * Extracts chat member's new status
     *
     * @param update the update
     * @return new status or null
     */
    private String getNewStatus(Update update) {
        return getStatus(update, ChatMemberUpdated::getNewChatMember);
    }

    /**
     * Extracts chat member's status from the update
     *
     * @param update              the update
     * @param chatMemberExtractor the chat member extractor
     * @return status or null
     */
    private String getStatus(Update update, Function<ChatMemberUpdated, ChatMember> chatMemberExtractor) {
        return Optional.ofNullable(update.getMyChatMember())
                       .map(chatMemberExtractor)
                       .map(ChatMember::getStatus)
                       .orElse(null);
    }

    /**
     * Gets chat's id
     *
     * @param update the update
     * @return chat's id as long
     */
    private long getChatId(Update update) {
        return update.getMyChatMember().getChat().getId();
    }
}

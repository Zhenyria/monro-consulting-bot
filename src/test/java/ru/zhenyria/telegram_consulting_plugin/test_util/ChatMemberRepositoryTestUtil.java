package ru.zhenyria.telegram_consulting_plugin.test_util;

import lombok.experimental.UtilityClass;
import ru.zhenyria.telegram_consulting_plugin.model.ChatMember;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility for {@link ru.zhenyria.telegram_consulting_plugin.repository.ChatMemberRepository} test
 */
@UtilityClass
public class ChatMemberRepositoryTestUtil {
    public static final BigInteger NOT_EXISTING_CHAT_MEMBER_ID = BigInteger.valueOf(3L);

    private static final ChatMember CHAT_MEMBER_1 = new ChatMember(BigInteger.valueOf(1L), "default_user_1");
    private static final ChatMember CHAT_MEMBER_2 = new ChatMember(BigInteger.valueOf(2L), "default_user_2");

    private static final List<ChatMember> CHAT_MEMBERS = List.of(CHAT_MEMBER_1, CHAT_MEMBER_2);

    public static List<String> getAllNamesOrderedByUserName() {
        return CHAT_MEMBERS
                .stream()
                .map(ChatMember::getUserName)
                .collect(Collectors.toList());
    }

    public static List<BigInteger> getExistingChatMemberIds() {
        return CHAT_MEMBERS
                .stream()
                .map(ChatMember::getChatMemberId)
                .collect(Collectors.toList());
    }

    public static long getTotalCount() {
        return CHAT_MEMBERS.size();
    }
}

package ru.zhenyria.monro_consulting_bot.test_util;

import lombok.experimental.UtilityClass;
import ru.zhenyria.monro_consulting_bot.model.ChatMember;

import java.util.List;

/**
 * Utility for {@link ru.zhenyria.monro_consulting_bot.repository.ChatMemberRepository} test
 */
@UtilityClass
public class ChatMemberRepositoryTestUtil {
    private static final ChatMember CHAT_MEMBER_1 = new ChatMember(1L, "default_user_1");
    private static final ChatMember CHAT_MEMBER_2 = new ChatMember(2L, "default_user_2");

    private static final List<ChatMember> CHAT_MEMBERS = List.of(CHAT_MEMBER_1, CHAT_MEMBER_2);

    public static long getTotalCount() {
        return CHAT_MEMBERS.size();
    }
}
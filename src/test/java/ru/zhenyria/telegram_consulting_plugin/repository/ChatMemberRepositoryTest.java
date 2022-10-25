package ru.zhenyria.telegram_consulting_plugin.repository;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.zhenyria.telegram_consulting_plugin.test_util.ChatMemberRepositoryTestUtil.NOT_EXISTING_CHAT_MEMBER_ID;
import static ru.zhenyria.telegram_consulting_plugin.test_util.ChatMemberRepositoryTestUtil.getAllNamesOrderedByUserName;
import static ru.zhenyria.telegram_consulting_plugin.test_util.ChatMemberRepositoryTestUtil.getExistingChatMemberIds;
import static ru.zhenyria.telegram_consulting_plugin.test_util.ChatMemberRepositoryTestUtil.getTotalCount;

/**
 * Test for {@link ChatMemberRepository}
 */
class ChatMemberRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private ChatMemberRepository chatMemberRepository;

    @Test
    void getAllNamesOrderedByUserNameTest() {
        List<String> actualUserNames = chatMemberRepository.getAllNamesOrderedByUserName();
        List<String> expectedUserNames = getAllNamesOrderedByUserName();

        assertEquals(expectedUserNames, actualUserNames);
    }

    @Test
    void countAllTest() {
        val actualCount = chatMemberRepository.getTotalCount();
        val expectedCount = getTotalCount();

        assertEquals(expectedCount, actualCount);
    }

    @Test
    void isChatMemberAlreadyExistingTest() {
        List<BigInteger> expectedExistingChatMemberIds = getExistingChatMemberIds();

        for (BigInteger chatMemberId : expectedExistingChatMemberIds) {
            val isChatMemberExisting = chatMemberRepository.isChatMemberAlreadyExisting(chatMemberId);
            assertTrue(isChatMemberExisting);
        }

        val isNotExistingChatMemberExisting =
                chatMemberRepository.isChatMemberAlreadyExisting(NOT_EXISTING_CHAT_MEMBER_ID);

        assertFalse(isNotExistingChatMemberExisting);
    }
}

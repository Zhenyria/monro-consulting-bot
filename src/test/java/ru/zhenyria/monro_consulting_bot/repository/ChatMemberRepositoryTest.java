package ru.zhenyria.monro_consulting_bot.repository;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.zhenyria.monro_consulting_bot.test_util.ChatMemberRepositoryTestUtil.getTotalCount;

/**
 * Test for {@link ChatMemberRepository}
 */
class ChatMemberRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private ChatMemberRepository chatMemberRepository;

    @Test
    void getTotalCountTest() {
        val actualCount = chatMemberRepository.getTotalCount();
        val expectedCount = getTotalCount();

        assertEquals(expectedCount, actualCount);
    }
}
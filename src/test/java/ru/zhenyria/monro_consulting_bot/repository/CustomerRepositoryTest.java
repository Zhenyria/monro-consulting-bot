package ru.zhenyria.monro_consulting_bot.repository;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zhenyria.monro_consulting_bot.AbstractSpringTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.zhenyria.monro_consulting_bot.test_util.ChatMemberRepositoryTestUtil.getTotalCount;

/**
 * Test for {@link CustomerRepository}
 */
class CustomerRepositoryTest extends AbstractSpringTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void getTotalCountTest() {
        val actualCount = customerRepository.getTotalCount();
        val expectedCount = getTotalCount();

        assertEquals(expectedCount, actualCount);
    }
}

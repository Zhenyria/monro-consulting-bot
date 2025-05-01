package ru.zhenyria.monro_consulting_bot.test_util;

import lombok.experimental.UtilityClass;
import ru.zhenyria.monro_consulting_bot.model.Customer;
import ru.zhenyria.monro_consulting_bot.repository.CustomerRepository;

import java.util.Collections;
import java.util.List;

/**
 * Utility for {@link CustomerRepository} test
 */
@UtilityClass
public class ChatMemberRepositoryTestUtil {
    private static final Customer CHAT_MEMBER_1 = new Customer(1L,
                                                               "default_user_1",
                                                               null,
                                                               null,
                                                               null,
                                                               null,
                                                               Collections.emptyList());

    private static final Customer CHAT_MEMBER_2 = new Customer(2L, "default_user_2",
                                                               null,
                                                               null,
                                                               null,
                                                               null,
                                                               Collections.emptyList());

    private static final List<Customer> CHAT_MEMBERS = List.of(CHAT_MEMBER_1, CHAT_MEMBER_2);

    public static long getTotalCount() {
        return CHAT_MEMBERS.size();
    }
}

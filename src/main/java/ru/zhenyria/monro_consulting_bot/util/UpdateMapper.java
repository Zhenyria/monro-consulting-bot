package ru.zhenyria.monro_consulting_bot.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.zhenyria.monro_consulting_bot.model.Customer;

/**
 * The mapper for {@link Update}
 */
@Mapper(componentModel = "spring")
public interface UpdateMapper {

    /**
     * Maps {@link Update} instance to {@link Customer} instance
     *
     * @param update {@link Update} instance
     * @return {@link Customer} instance
     */
    @Mapping(target = "chatMemberId", source = "update.myChatMember.newChatMember.user.id")
    @Mapping(target = "userName", source = "update.myChatMember.from.userName")
    Customer mapToChatMember(Update update);
}

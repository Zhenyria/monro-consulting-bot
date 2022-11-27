package ru.zhenyria.telegram_consulting_plugin.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.zhenyria.telegram_consulting_plugin.model.ChatMember;

/**
 * The mapper for {@link Update}
 */
@Mapper(componentModel = "spring")
public interface UpdateMapper {

    /**
     * Maps {@link Update} instance to {@link ChatMember} instance
     *
     * @param update {@link Update} instance
     * @return {@link ChatMember} instance
     */
    @Mapping(target = "chatMemberId", source = "update.myChatMember.newChatMember.user.id")
    @Mapping(target = "userName", source = "update.myChatMember.newChatMember.user.userName")
    ChatMember mapToChatMember(Update update);
}

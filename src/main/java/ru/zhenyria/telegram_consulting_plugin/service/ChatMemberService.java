package ru.zhenyria.telegram_consulting_plugin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zhenyria.telegram_consulting_plugin.model.ChatMember;
import ru.zhenyria.telegram_consulting_plugin.repository.ChatMemberRepository;

import javax.transaction.Transactional;
import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class ChatMemberService {
    private final ChatMemberRepository chatMemberRepository;

    @Transactional
    public void save(ChatMember chatMember) {
        chatMemberRepository.save(chatMember);
    }

    public long getChatMembersTotalCount() {
        return chatMemberRepository.getTotalCount();
    }

    @Transactional
    public boolean removeByChatMemberId(BigInteger chatMemberId) {
        return chatMemberRepository.removeByChatMemberId(chatMemberId) > 0;
    }
}

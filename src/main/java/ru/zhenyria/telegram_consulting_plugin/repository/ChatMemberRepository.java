package ru.zhenyria.telegram_consulting_plugin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.zhenyria.telegram_consulting_plugin.model.ChatMember;

@Repository
public interface ChatMemberRepository extends JpaRepository<ChatMember, Integer> {

    @Query("SELECT COUNT(cm) FROM ChatMember cm")
    long getTotalCount();

    @Modifying
    @Query("DELETE FROM ChatMember cm WHERE cm.chatMemberId = :chatMemberId")
    int removeByChatMemberId(Long chatMemberId);
}

package ru.zhenyria.monro_consulting_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.zhenyria.monro_consulting_bot.model.ChatMember;

@Repository
public interface ChatMemberRepository extends JpaRepository<ChatMember, Integer> {

    @Query("SELECT COUNT(cm) FROM ChatMember cm")
    long getTotalCount();

    @Modifying
    @Query("DELETE FROM ChatMember cm WHERE cm.chatMemberId = :chatMemberId")
    int removeByChatMemberId(Long chatMemberId);
}

package ru.zhenyria.telegram_consulting_plugin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.zhenyria.telegram_consulting_plugin.model.ChatMember;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface ChatMemberRepository extends JpaRepository<ChatMember, Integer> {

    @Query("SELECT cm.userName FROM ChatMember cm ORDER BY cm.userName ASC")
    List<String> getAllNamesOrderedByUserName();

    @Query("SELECT COUNT(cm) FROM ChatMember cm")
    long getTotalCount();

    @Query("SELECT COUNT(cm) > 0 FROM ChatMember cm WHERE cm.chatMemberId = :chatMemberId")
    boolean isChatMemberAlreadyExisting(BigInteger chatMemberId);
}

package ru.zhenyria.monro_consulting_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.zhenyria.monro_consulting_bot.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT COUNT(cm) FROM Customer cm")
    long getTotalCount();

    @Modifying
    @Query("DELETE FROM Customer cm WHERE cm.chatMemberId = :chatMemberId")
    int removeByChatMemberId(Long chatMemberId);
}

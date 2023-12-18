package ru.zhenyria.monro_consulting_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.zhenyria.monro_consulting_bot.model.Season;

import java.util.List;

@Repository
public interface SeasonRepository extends JpaRepository<Season, String> {

    @Query("""
            SELECT DISTINCT s
            FROM Season s
            JOIN Shoes shoes ON shoes.season = s
            JOIN Customer c ON c.chatMemberId = :customerId
            WHERE (c.scale.size, c.scale.volume) IN (SELECT sc.size, sc.volume FROM shoes.scales sc)
            """)
    List<Season> getLocalizedNamesOfRelatedToShoesWithCustomersScale(Long customerId);
}

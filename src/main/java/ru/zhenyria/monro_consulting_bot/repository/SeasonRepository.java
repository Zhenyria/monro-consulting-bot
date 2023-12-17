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
                            AND c.scale.size = shoes.scale.size
                            AND c.scale.volume = shoes.scale.volume
            """)
    List<Season> getLocalizedNamesOfRelatedToShoesWithCustomersScale(Long customerId);
}

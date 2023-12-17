package ru.zhenyria.monro_consulting_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.zhenyria.monro_consulting_bot.model.ShoesModel;

import java.util.List;

@Repository
public interface ShoesModelRepository extends JpaRepository<ShoesModel, String> {

    @Query("""
            SELECT DISTINCT sm
            FROM ShoesModel sm
            JOIN Shoes s ON s.model = sm
            JOIN Customer c ON c.chatMemberId = :customerId
                            AND c.scale.size = s.scale.size
                            AND c.scale.volume = s.scale.volume
            """)
    List<ShoesModel> getLocalizedNamesOfRelatedToShoesWithCustomersScale(Long customerId);
}

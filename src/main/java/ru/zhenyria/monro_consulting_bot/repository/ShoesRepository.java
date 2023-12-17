package ru.zhenyria.monro_consulting_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.zhenyria.monro_consulting_bot.model.Shoes;

import java.util.List;

@Repository
public interface ShoesRepository extends JpaRepository<Shoes, String> {

    @Query("""
            SELECT DISTINCT s
            FROM Shoes s
            JOIN Customer c ON c.chatMemberId = :customerId AND c.scale.size = s.scale.size
                                                            AND c.scale.volume = s.scale.volume
            JOIN ShoesModel sm ON sm.name = s.model.name
            JOIN Season se ON se.name = s.season.name
            WHERE (:seasonName IS NULL OR se.name = :seasonName)
            AND (:shoesModelName IS NULL OR sm.name = :shoesModelName)
            """)
    List<Shoes> getAllByCustomerIdAndSeasonNameAndShoesModelName(Long customerId,
                                                                 String seasonName,
                                                                 String shoesModelName);
}

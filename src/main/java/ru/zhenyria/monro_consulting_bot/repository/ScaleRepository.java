package ru.zhenyria.monro_consulting_bot.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.zhenyria.monro_consulting_bot.model.Scale;
import ru.zhenyria.monro_consulting_bot.model.ScaleId;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ScaleRepository extends JpaRepository<Scale, ScaleId> {

    @Query("""
            SELECT s FROM Scale s
            WHERE s.footLength >= :minFootLength
            AND s.footGirth >= :minFootGirth""")
    List<Scale> getAllWithFootLengthAndGirthMoreThan(BigDecimal minFootLength,
                                                     BigDecimal minFootGirth,
                                                     Pageable pageable);

    @Query("SELECT DISTINCT s.footLength FROM Scale s")
    List<BigDecimal> getAllLengths(Sort sort);

    @Query("SELECT DISTINCT s.footGirth FROM Scale s")
    List<BigDecimal> getAllGirths(Sort sort);
}

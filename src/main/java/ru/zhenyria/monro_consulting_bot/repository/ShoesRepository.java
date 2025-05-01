package ru.zhenyria.monro_consulting_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.zhenyria.monro_consulting_bot.dto.inner.WishedShoesDto;
import ru.zhenyria.monro_consulting_bot.model.Shoes;

import java.util.List;

@Repository
public interface ShoesRepository extends JpaRepository<Shoes, Integer> {

    @Query(value = """
                   SELECT DISTINCT crs.consultation_request_id AS consultation_request_id,
                                   s.name AS name,
                                   s.vendor_code AS vendor_code
                   FROM            monro.consultation_requests_shoes AS crs
                   LEFT JOIN       monro.shoes AS s ON s.id = crs.shoes_id
                   WHERE           crs.consultation_request_id IN (:consultationRequestsIds)
                   """,
            nativeQuery = true)
    List<WishedShoesDto> findAllByConsultationRequestsIds(List<Integer> consultationRequestsIds);

    @Query("""
           SELECT DISTINCT s
           FROM            Shoes s
           JOIN            ShoesModel sm ON sm.name = s.model.name
           JOIN            Season se ON se.name = s.season.name
           JOIN            Customer c ON c.chatMemberId = :customerId
           WHERE           (:seasonName IS NULL OR se.name = :seasonName)
           AND             (:shoesModelName IS NULL OR sm.name = :shoesModelName)
           AND             (c.scale.size, c.scale.volume) IN (SELECT sc.size, sc.volume FROM s.scales sc)
           """)
    List<Shoes> findAllByCustomerIdAndSeasonNameAndShoesModelName(Long customerId,
                                                                  String seasonName,
                                                                  String shoesModelName);

    @Query("""
           SELECT DISTINCT s
           FROM            Shoes s
           JOIN            Customer c ON c.chatMemberId = :customerId
           WHERE           s.id IN (SELECT ws.id FROM c.wishedShoes AS ws)
           """)
    List<Shoes> findAllFromWishedListByCustomerId(Long customerId);

    @Query("""
           SELECT          s
           FROM            Shoes s
           LEFT JOIN FETCH s.scales
           WHERE           s.id IN (:ids)
           """)
    List<Shoes> findAllByIdsFetchingScales(List<Integer> ids);
}

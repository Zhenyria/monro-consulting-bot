package ru.zhenyria.monro_consulting_bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.zhenyria.monro_consulting_bot.model.Season;
import ru.zhenyria.monro_consulting_bot.repository.SeasonRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeasonService {
    private final SeasonRepository repository;

    @Transactional(propagation = Propagation.MANDATORY)
    public Season getReference(String name) {
        return repository.getReferenceById(name);
    }

    /**
     * Retrieves seasons which contains shoes available for a customer
     *
     * @param customerId the id of the customer
     * @return list of seasons
     */
    public List<Season> getLocalizedNamesOfRelatedToShoesAvailableToCustomer(Long customerId) {
        return repository.getLocalizedNamesOfRelatedToShoesWithCustomersScale(customerId);
    }
}

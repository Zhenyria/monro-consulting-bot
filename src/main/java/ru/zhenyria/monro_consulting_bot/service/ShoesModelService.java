package ru.zhenyria.monro_consulting_bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zhenyria.monro_consulting_bot.model.ShoesModel;
import ru.zhenyria.monro_consulting_bot.repository.ShoesModelRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoesModelService {
    private final ShoesModelRepository repository;

    /**
     * Retrieves models of shoes available for a customer
     *
     * @param customerId the id of the customer
     * @return list of shoes models
     */
    public List<ShoesModel> getLocalizedNamesOfRelatedToShoes(Long customerId) {
        return repository.getLocalizedNamesOfRelatedToShoesWithCustomersScale(customerId);
    }
}

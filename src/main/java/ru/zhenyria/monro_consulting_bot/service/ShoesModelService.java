package ru.zhenyria.monro_consulting_bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.zhenyria.monro_consulting_bot.model.ShoesModel;
import ru.zhenyria.monro_consulting_bot.repository.ShoesModelRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoesModelService {
    private final ShoesModelRepository repository;

    @Transactional(propagation = Propagation.MANDATORY)
    public ShoesModel getReference(String name) {
        return repository.getReferenceById(name);
    }

    public List<ShoesModel> getAll() {
        return repository.findAll();
    }


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

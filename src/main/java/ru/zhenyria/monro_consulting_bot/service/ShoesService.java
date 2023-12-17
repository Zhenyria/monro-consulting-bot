package ru.zhenyria.monro_consulting_bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zhenyria.monro_consulting_bot.model.Shoes;
import ru.zhenyria.monro_consulting_bot.repository.ShoesRepository;

@Service
@RequiredArgsConstructor
public class ShoesService {
    private final ShoesRepository repository;

    /**
     * Retrieves random shoes available to a customer
     *
     * @param customerId the id of the customer
     * @param season     the season of the shoes
     * @param shoesModel the model of the shoes
     * @return randomly picked shoes
     */
    public Shoes getRandomByCustomerAndSeasonAndModel(Long customerId, String season, String shoesModel) {
        var shoes = repository.getAllByCustomerIdAndSeasonNameAndShoesModelName(customerId, season, shoesModel);

        if (shoes.isEmpty()) {
            return null;
        }

        int randomIndex = (int) ((shoes.size() - 1) * Math.random());
        return shoes.get(randomIndex);
    }
}

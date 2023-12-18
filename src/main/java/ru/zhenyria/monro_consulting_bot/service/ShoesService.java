package ru.zhenyria.monro_consulting_bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zhenyria.monro_consulting_bot.model.Shoes;
import ru.zhenyria.monro_consulting_bot.repository.ShoesRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class ShoesService {
    private final ShoesRepository repository;

    public Shoes getByVendorCode(String vendorCode) {
        return repository.findById(vendorCode)
                         .orElse(null);
    }

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

        int randomIndex = BigDecimal.valueOf((shoes.size() - 1) * Math.random())
                                    .setScale(0, RoundingMode.HALF_UP)
                                    .intValue();

        return shoes.get(randomIndex);
    }
}

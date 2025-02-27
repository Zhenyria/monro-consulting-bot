package ru.zhenyria.monro_consulting_bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zhenyria.monro_consulting_bot.dto.ShoesCreateRequestDto;
import ru.zhenyria.monro_consulting_bot.model.ScaleId;
import ru.zhenyria.monro_consulting_bot.model.Shoes;
import ru.zhenyria.monro_consulting_bot.repository.ShoesRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoesService {
    private final CustomerService customerService;
    private final ScaleService scaleService;
    private final SeasonService seasonService;
    private final ShoesModelService modelService;
    private final ShoesRepository repository;

    @Transactional
    public void create(ShoesCreateRequestDto createRequest) {
        var season = seasonService.getReference(createRequest.getSeasonName());
        var model = modelService.getReference(createRequest.getModelName());
        var scales = scaleService.getReferences(Optional.ofNullable(createRequest.getSizes())
                                                        .stream()
                                                        .flatMap(List::stream)
                                                        .map(dto -> new ScaleId(dto.getSize(), dto.getVolume()))
                                                        .collect(Collectors.toList()));

        var shoes = new Shoes(createRequest.getVendorCode(),
                              createRequest.getUrl(),
                              createRequest.getName(),
                              createRequest.getDescription(),
                              createRequest.getImageUrl(),
                              scales,
                              season,
                              model);

        repository.save(shoes);
    }

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

    public List<Shoes> getAll() {
        return repository.findAll();
    }

    /**
     * Retrieves all shoes from customer's wish list
     *
     * @param customerId the id of the customer
     * @return list of shoes
     */
    public List<Shoes> getAllFromWishList(Long customerId) {
        return repository.getAllFromWishedListByCustomerId(customerId);
    }

    @Transactional
    public void delete(String vendorCode) {
        if (!repository.existsById(vendorCode)) {
            return;
        }

        customerService.removeShoesFromWishLists(vendorCode);
        repository.deleteById(vendorCode);
    }
}

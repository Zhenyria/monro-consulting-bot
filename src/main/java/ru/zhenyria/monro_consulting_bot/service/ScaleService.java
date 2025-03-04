package ru.zhenyria.monro_consulting_bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.zhenyria.monro_consulting_bot.model.Scale;
import ru.zhenyria.monro_consulting_bot.model.ScaleId;
import ru.zhenyria.monro_consulting_bot.repository.ScaleRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScaleService {
    private static final String FOOT_LENGTH_PARAMETER = "footLength";
    private static final String FOOT_GIRTH_PARAMETER = "footGirth";
    private static final String[] SORTED_PARAMETERS = {FOOT_LENGTH_PARAMETER, FOOT_GIRTH_PARAMETER};
    private static final int MAX_RESULTS_COUNT_FOR_PERSON = 1;

    private final ScaleRepository repository;

    public List<Scale> getAll() {
        return repository.findAll();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<Scale> getReferences(List<ScaleId> ids) {
        return Optional.ofNullable(ids)
                       .stream()
                       .flatMap(List::stream)
                       .map(repository::getReferenceById)
                       .collect(Collectors.toList());
    }

    public Scale getMostSuitable(BigDecimal footLength, BigDecimal footGirth) {
        return Optional.of(repository.getAllWithFootLengthAndGirthMoreThan(
                               footLength,
                               footGirth,
                               PageRequest.of(0,
                                              MAX_RESULTS_COUNT_FOR_PERSON,
                                              Sort.by(Sort.Direction.ASC, SORTED_PARAMETERS))
                       ))
                       .stream()
                       .flatMap(List::stream)
                       .findFirst()
                       .orElse(null);
    }

    public List<BigDecimal> getAllLengths() {
        return repository.getAllLengths(Sort.by(Sort.Direction.ASC, FOOT_LENGTH_PARAMETER));
    }

    public List<BigDecimal> getAllGirths() {
        return repository.getAllGirths(Sort.by(Sort.Direction.ASC, FOOT_GIRTH_PARAMETER));
    }
}

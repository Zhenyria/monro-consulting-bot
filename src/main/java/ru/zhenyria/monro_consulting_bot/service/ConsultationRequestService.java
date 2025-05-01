package ru.zhenyria.monro_consulting_bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zhenyria.monro_consulting_bot.model.ConsultationRequest;
import ru.zhenyria.monro_consulting_bot.repository.ConsultationRequestRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConsultationRequestService {
    private final ConsultationRequestRepository repository;

    @Transactional(rollbackFor = RuntimeException.class)
    public void save(ConsultationRequest consultationRequest) {
        repository.save(consultationRequest);
        repository.flush();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void removeAllByCustomerId(Long customerId) {
        repository.deleteByCustomerId(customerId);
        repository.flush();
    }
}

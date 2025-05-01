package ru.zhenyria.monro_consulting_bot.service.update_processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Main service which handles all telegram's updates
 */
@Service
@Transactional
@RequiredArgsConstructor
public class MonroConsultingService {
    private final List<UpdateProcessableService> updateProcessableServices;

    /**
     * Handles all received updates. Finds suitable {@link UpdateProcessableService} implementation and uses it for
     * update processing
     *
     * @param update received update
     * @return message to reply or null if there are no any reply (or no any suitable service for update processing)
     */
    public SendMessage handleUpdate(Update update) {
        return updateProcessableServices.stream()
                                        .filter(service -> service.isServiceSuitable(update))
                                        .map(UpdateProcessableService::getUpdateHandlers)
                                        .map(Map::entrySet)
                                        .flatMap(Set::stream)
                                        .filter(entry -> entry.getKey().test(update))
                                        .findFirst()
                                        .map(Map.Entry::getValue)
                                        .map(messageProducer -> messageProducer.apply(update))
                                        .orElse(null);
    }
}

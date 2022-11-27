package ru.zhenyria.telegram_consulting_plugin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.zhenyria.telegram_consulting_plugin.service.update_processor.MonroConsultingService;

import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class MonroConsultingBot extends TelegramLongPollingBot {

    @Value("${telegram.botName}")
    private String botName;

    @Value("${telegram.botToken}")
    private String botToken;

    private final MonroConsultingService monroConsultingService;

    @Override
    public String getBotUsername() {
        return this.botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = monroConsultingService.handleUpdate(update);
        if (Objects.nonNull(message)) {
            try {
                super.execute(message);
            } catch (TelegramApiException e) {
                log.warn(e.getMessage());
            }
        }
    }
}

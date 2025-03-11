package ru.zhenyria.monro_consulting_bot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.zhenyria.monro_consulting_bot.MonroConsultingBot;

@Configuration
public class TelegramConfiguration {

    @Bean
    public TelegramBotsApi telegramBotsApi(MonroConsultingBot monroConsultingBot) throws TelegramApiException {
        var telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(monroConsultingBot);
        return telegramBotsApi;
    }
}

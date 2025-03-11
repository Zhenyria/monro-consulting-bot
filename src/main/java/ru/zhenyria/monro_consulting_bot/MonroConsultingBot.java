package ru.zhenyria.monro_consulting_bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.zhenyria.monro_consulting_bot.service.update_processor.MonroConsultingService;
import ru.zhenyria.monro_consulting_bot.util.StartCommand;
import ru.zhenyria.monro_consulting_bot.util.StartCommandMapper;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MonroConsultingBot extends TelegramLongPollingBot {
    private final String botName;
    private final MonroConsultingService monroConsultingService;
    private final StartCommandMapper startCommandMapper;

    public MonroConsultingBot(@Value("${telegram.botName}") String botName,
                              @Value("${telegram.botToken}") String botToken,
                              MonroConsultingService monroConsultingService,
                              StartCommandMapper startCommandMapper) {
        super(botToken);
        this.botName = botName;
        this.monroConsultingService = monroConsultingService;
        this.startCommandMapper = startCommandMapper;
    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var message = monroConsultingService.handleUpdate(update);
        if (Objects.nonNull(message)) {
            try {
                super.execute(message);
            } catch (TelegramApiException e) {
                log.warn(e.getMessage());
            }
        }
    }

    @Override
    public void onRegister() {
        try {
            if (!super.execute(getCommands())) {
                log.error("Bot commands list was not initialized");
            }
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Gets list of bot commands
     *
     * @return {@link SetMyCommands} instance which contains list of available commands
     */
    private SetMyCommands getCommands() {
        var botCommands = startCommandMapper.mapToBotCommand(Arrays.stream(StartCommand.values())
                                                                   .collect(Collectors.toList()));
        var myCommands = new SetMyCommands();
        myCommands.setCommands(botCommands);
        return myCommands;
    }
}

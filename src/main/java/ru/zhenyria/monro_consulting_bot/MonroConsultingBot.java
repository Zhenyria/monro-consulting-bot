package ru.zhenyria.monro_consulting_bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.zhenyria.monro_consulting_bot.service.update_processor.MonroConsultingService;
import ru.zhenyria.monro_consulting_bot.util.Command;
import ru.zhenyria.monro_consulting_bot.util.CommandMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class MonroConsultingBot extends TelegramLongPollingBot {

    private final CommandMapper commandMapper;

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
        List<BotCommand> botCommands = commandMapper.mapToBotCommand(
                Arrays.stream(Command.values()).collect(Collectors.toList())
        );
        SetMyCommands myCommands = new SetMyCommands();
        myCommands.setCommands(botCommands);
        return myCommands;
    }
}

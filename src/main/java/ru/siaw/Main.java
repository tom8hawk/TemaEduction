package ru.siaw;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.siaw.bot.Bot;
import ru.siaw.bot.DailyNotification;
import ru.siaw.database.Database;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main {
    public static final Executor executor = Executors.newWorkStealingPool();

    public static void main(String[] args) {
        Database.read();

        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        DailyNotification.start();
        Runtime.getRuntime().addShutdownHook(new Thread(Database::write));
    }
}

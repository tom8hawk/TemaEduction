package ru.siaw.bot;

import lombok.Getter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.siaw.database.Course;
import ru.siaw.database.Database;
import ru.siaw.database.Poll;
import ru.siaw.database.User;
import ru.siaw.instances.Service;
import ru.siaw.messages.MessageHandler;
import ru.siaw.messages.Support;
import ru.siaw.motivation.*;

import java.util.*;

import static ru.siaw.Main.executor;

/** Обработка сообщеий */
public class Bot extends TelegramLongPollingBot  {
    @Getter private static final Set<Long> started = new HashSet<>();
    @Getter private static final Map<Long, Course> inSetup = new HashMap<>();
    @Getter private static final Map<Long, Course> atThePoll = new HashMap<>();
    public static Bot inst;

    public Bot() {
        inst = this;
    }

    @Override
    public void onUpdateReceived(Update update) {
        executor.execute(() -> {
            // Обработка текстового сообщения
            if (update.hasMessage()) {
                Message message = update.getMessage();

                long userId = message.getFrom().getId(); // id отправителя сообщения
                long chatId = message.getChatId(); // id чата

                if (message.hasText()) {
                    User user = Database.findUser(userId); // Получаем информацию о студенте
                    String text = message.getText(); // Получаем сообщение

                    if (text.startsWith("/start")) { // Команда start
                        if (user.getCourse() == null) {
                            MessageHandler.startMessage(chatId);
                            started.add(userId);
                        } else {
                            MessageHandler.alreadyExists(chatId);
                        }
                    } else if (text.startsWith("/support")) { // Поддержка
                        MessageHandler.support(chatId);
                    } else if (atThePoll.containsKey(userId)) { // Студент на "опросе"
                        Poll poll = atThePoll.get(userId).getPolls().getLast();

                        if (poll.getImportant() == null) {
                            poll.setImportant(text);
                            MessageHandler.getNotEnoughInfo(chatId);
                        } else if (poll.isNotEnoughInfo()) {
                            poll.setLearn(text);

                            MessageHandler.fillTheGap(chatId);
                            MessageHandler.emotion(chatId);
                        }
                    } else if (started.contains(userId)) { // Студент вводит название курса и свое имя (опционально)
                        inSetup.put(userId, user.newCourse(text));

                        if (user.getName() == null)
                            MessageHandler.getName(chatId);
                        else
                            MessageHandler.getDurationWithoutName(chatId);

                        started.remove(userId);
                    } else if (inSetup.containsKey(userId)) { // Студент говорит про курс
                        Course course = inSetup.get(userId);

                        if (user.getName() == null) {
                            user.setName(text);
                            MessageHandler.getDurationWithName(chatId);
                        } else if (course.getEndTime() == null) {
                            try {
                                int duration = Integer.parseInt(text);

                                if (duration > 0 && duration < 25) {
                                    course.calculateEnd(duration);
                                    MessageHandler.getGoal(chatId);
                                } else {
                                    MessageHandler.tooLongDuration(chatId);
                                }
                            } catch (NumberFormatException ignored) {
                                MessageHandler.tooLongDuration(chatId);
                            }
                        } else if (course.getUserGoal() == null) {
                            course.setUserGoal(text);
                            MessageHandler.briefGoal(message.getMessageId(), chatId);
                        } else if (course.getGoal() == null) {
                            MessageHandler.weCantContinue(chatId);
                        }
                    }
                }
            // Обработка нажатия кнопки
            } else if (update.hasCallbackQuery()) {
                long id = update.getCallbackQuery().getMessage().getChatId(); // Получаем чат
                String data = update.getCallbackQuery().getData(); // Получаем данные кнопки

                if (data.equals("удалить")) { // Удаление курса
                    Database.getUsers()
                            .remove(Database.findUser(id));

                    removeFromWaiting(id);
                    MessageHandler.deleted(id);
                } else if (Arrays.stream(Support.values()).map(Support::toString).anyMatch(data::equals)) { // Кнопки поддержки
                    MessageHandler.sendSimpleMessage(id, Support.valueOf(data).answer);
                } else if (inSetup.containsKey(id)) { // Кнопки при настройке курса
                    // Помощь постановки цели S.M.A.R.T
                    if (data.equals("подробнее")) {
                        MessageHandler.smart(id);
                    } else if (Arrays.stream(Goal.values()).map(Goal::toString).anyMatch(data::equals)) { // Кнопки цели
                        if (inSetup.containsKey(id)) {
                            Course course = inSetup.get(id);

                            if (course.getGoal() == null) {
                                course.setGoal(Goal.valueOf(data));

                                MessageHandler.finishGreeting(id);
                                inSetup.remove(id);
                            }
                        }
                    }
                } else if (atThePoll.containsKey(id)) { // Кнопки при "опросе"
                    if (data.equals("да, были")) {
                        Database.findUser(id).getCourse().getPolls().getLast()
                                .setNotEnoughInfo(true);

                        MessageHandler.notEnoughInfo(id);
                    } else if (data.equals("нет, не было")) {
                        MessageHandler.emotion(id);
                    } else if (Arrays.stream(Emotion.values()).map(Emotion::toString).anyMatch(data::equals)) { // Кнопки эмоций
                        Emotion emotion = Emotion.valueOf(data);
                        Database.findUser(id).getCourse().getPolls().getLast()
                                .setEmotion(emotion);

                        if (!emotion.isPositive)
                            MessageHandler.badly(id);

                        MessageHandler.getDifficulties(id);
                    } else if (Arrays.stream(Difficulties.values()).map(Difficulties::toString).anyMatch(data::equals)) { // Кнопки сложностей
                        Difficulties diff = Difficulties.valueOf(data);
                        Database.findUser(id).getCourse().getPolls().getLast()
                                .setDifficulties(diff);

                        MessageHandler.getEasily(id);
                    } else if (Arrays.stream(Easily.values()).map(Easily::toString).anyMatch(data::equals)) { // Кнопки легкостей
                        Easily easily = Easily.valueOf(data);
                        Database.findUser(id).getCourse().getPolls().getLast()
                                .setEasily(easily);

                        MessageHandler.getMotivation(id);
                    } else if (Arrays.stream(Motivation.values()).map(Motivation::toString).anyMatch(data::equals)) { // Кнопки мотивации
                        Course course = Database.findUser(id).getCourse();
                        course.getPolls().getLast()
                                .setMotivation(Motivation.valueOf(data));

                        MessageHandler.endOfSurvey(id, course);
                        atThePoll.remove(id);
                    }
                }
            }
        });
    }

    public static void removeFromWaiting(long id) {
        started.remove(id);
        inSetup.remove(id);
        atThePoll.remove(id);
    }

    @Override
    public String getBotUsername() {
        return Service.USERNAME;
    }

    @Override
    public String getBotToken() {
        return Service.TOKEN;
    }
}

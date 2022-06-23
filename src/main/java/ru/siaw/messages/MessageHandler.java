package ru.siaw.messages;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.siaw.bot.Bot;
import ru.siaw.database.Course;
import ru.siaw.database.Poll;
import ru.siaw.instances.Messages;
import ru.siaw.motivation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Отправка сообщений */
public class MessageHandler {

    private MessageHandler() {
        throw new IllegalStateException("Utility class");
    }

    public static void startMessage(long chatId) {
        try {
            InputFile helloPhoto = new InputFile(MessageHandler.class.getClassLoader()
                    .getResourceAsStream("Hello.jpg"), "Hello.jpg");

            SendPhoto message = new SendPhoto(String.valueOf(chatId), helloPhoto);
            message.setCaption(Messages.START_MESSAGE);

            Bot.inst.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void alreadyExists(long chatId) {
        try {
            SendMessage message = new SendMessage(String.valueOf(chatId), Messages.ALREADY_EXISTS);

            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
            List<InlineKeyboardButton> bottom = new ArrayList<>();

            InlineKeyboardButton confirm = new InlineKeyboardButton();
            confirm.setText("Да");
            confirm.setCallbackData("удалить");

            InlineKeyboardButton reject = new InlineKeyboardButton();
            reject.setText("Нет");
            reject.setCallbackData("не удалять");

            bottom.add(confirm);
            bottom.add(reject);

            buttons.add(bottom);
            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(buttons);

            message.setReplyMarkup(keyboard);
            Bot.inst.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void support(long chatId) {
        try {
            InputFile helloPhoto = new InputFile(MessageHandler.class.getClassLoader()
                    .getResourceAsStream("Support.jpg"), "Support.jpg");

            SendPhoto message = new SendPhoto(String.valueOf(chatId), helloPhoto);
            message.setCaption(Messages.SUPPORT);

            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

            for (Support support : Support.values()) {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText(support.text);
                inlineKeyboardButton.setCallbackData(support.toString());

                buttons.add(Collections.singletonList(inlineKeyboardButton));
            }

            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(buttons);
            message.setReplyMarkup(keyboard);

            Bot.inst.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void deleted(long chatId) {
        sendSimpleMessage(chatId, Messages.DELETED);
    }

    public static void getName(long chatId) {
        sendSimpleMessage(chatId, Messages.GET_NAME);
    }

    public static void getDurationWithName(long chatId) {
        sendSimpleMessage(chatId, Messages.GET_DURATION_WITH_NAME);
    }

    public static void getDurationWithoutName(long chatId) {
        sendSimpleMessage(chatId, Messages.GET_DURATION_WITHOUT_NAME);
    }

    public static void tooLongDuration(long chatId) {
        sendSimpleMessage(chatId, Messages.TOO_LONG_DURATION);
    }

    public static void getGoal(long chatId) {
        try {
            SendMessage message = new SendMessage(String.valueOf(chatId), Messages.SET_A_GOAL);
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText("Подробнее");
            inlineKeyboardButton.setCallbackData("подробнее");

            buttons.add(Collections.singletonList(inlineKeyboardButton));
            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(buttons);

            message.setReplyMarkup(keyboard);
            Bot.inst.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void smart(long chatId) {
        try {
            InputFile helloPhoto = new InputFile(MessageHandler.class.getClassLoader()
                    .getResourceAsStream("Need_to_think.jpg"), "Need_to_think.jpg");

            SendPhoto message = new SendPhoto(String.valueOf(chatId), helloPhoto);
            message.setCaption(Messages.SMART);

            Bot.inst.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void briefGoal(int replyToMessage, long chatId) {
        try {
            SendMessage message = new SendMessage(String.valueOf(chatId), Messages.BRIEF_GOAL);
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

            for (Goal goal : Goal.values()) {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText(goal.text);
                inlineKeyboardButton.setCallbackData(goal.toString());

                buttons.add(Collections.singletonList(inlineKeyboardButton));
            }

            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(buttons);
            message.setReplyMarkup(keyboard);

            message.setReplyToMessageId(replyToMessage);
            Bot.inst.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void weCantContinue(long chatId) {
        sendSimpleMessage(chatId, Messages.WE_CANT_CONTINUE);
    }

    public static void finishGreeting(long chatId) {
        sendSimpleMessage(chatId, Messages.FINISH_GREETING);
    }

    public static void dayIsOver(long chatId) {
        try {
            InputFile gladToHelpPhoto = new InputFile(MessageHandler.class.getClassLoader()
                    .getResourceAsStream("Glad_to_help.jpg"), "Glad_to_help.jpg");

            SendPhoto message = new SendPhoto(String.valueOf(chatId), gladToHelpPhoto);
            message.setCaption(Messages.DAY_IS_OVER);

            Bot.inst.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void getNotEnoughInfo(long chatId) {
        try {
            SendMessage message = new SendMessage(String.valueOf(chatId), Messages.GET_NOT_ENOUGH_INFO);

            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
            List<InlineKeyboardButton> bottom = new ArrayList<>();

            InlineKeyboardButton confirm = new InlineKeyboardButton();
            confirm.setText("Да");
            confirm.setCallbackData("да, были");

            InlineKeyboardButton reject = new InlineKeyboardButton();
            reject.setText("Нет");
            reject.setCallbackData("нет, не было");

            bottom.add(confirm);
            bottom.add(reject);

            buttons.add(bottom);
            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(buttons);

            message.setReplyMarkup(keyboard);
            Bot.inst.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void notEnoughInfo(long chatId) {
        sendSimpleMessage(chatId, Messages.NOT_ENOUGH_INFO);

    }

    public static void fillTheGap(long chatId) {
        sendSimpleMessage(chatId, Messages.FILL_THE_GAP);
    }

    public static void emotion(long chatId) {
        try {
            SendMessage message = new SendMessage(String.valueOf(chatId), Messages.EMOTION);
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

            int num = 0;

            for (int i = 0; i < 2; i++) {
                List<InlineKeyboardButton> bottom = new ArrayList<>();

                for (int j = 0; j < 3; j++) {
                    Emotion emotion = Emotion.values()[num++];

                    InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                    inlineKeyboardButton.setText(emotion.text);
                    inlineKeyboardButton.setCallbackData(emotion.toString());

                    bottom.add(inlineKeyboardButton);
                }

                buttons.add(bottom);
            }

            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(buttons);
            message.setReplyMarkup(keyboard);

            Bot.inst.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void badly(long chatId) {
        sendSimpleMessage(chatId, Messages.BADLY);
    }

    public static void getDifficulties(long chatId) {
        try {
            SendMessage message = new SendMessage(String.valueOf(chatId), Messages.GET_DIFFICULTIES);
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

            for (Difficulties diff : Difficulties.values()) {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText(diff.text);
                inlineKeyboardButton.setCallbackData(diff.toString());

                buttons.add(Collections.singletonList(inlineKeyboardButton));
            }

            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(buttons);
            message.setReplyMarkup(keyboard);

            Bot.inst.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void getEasily(long chatId) {
        try {
            SendMessage message = new SendMessage(String.valueOf(chatId), Messages.GET_EASILY);
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

            for (Easily easily : Easily.values()) {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText(easily.text);
                inlineKeyboardButton.setCallbackData(easily.toString());

                buttons.add(Collections.singletonList(inlineKeyboardButton));
            }

            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(buttons);
            message.setReplyMarkup(keyboard);

            Bot.inst.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void getMotivation(long chatId) {
        try {
            SendMessage message = new SendMessage(String.valueOf(chatId), Messages.GET_MOTIVATION);
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

            for (Motivation motivation : Motivation.values()) {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText(motivation.text);
                inlineKeyboardButton.setCallbackData(motivation.toString());

                buttons.add(Collections.singletonList(inlineKeyboardButton));
            }

            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(buttons);
            message.setReplyMarkup(keyboard);

            Bot.inst.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void endOfSurvey(long chatId, Course course) {
        Poll poll = course.getPolls().getLast();

        sendSimpleMessage(chatId, String.format(Messages.END_OF_SURVEY,
                poll.getDay(), course.getDays(), course.getUserGoal()));
    }

    public static void endOfCourse(long chatId) {
        try {
            InputFile endOfCoursePhoto = new InputFile(MessageHandler.class.getClassLoader()
                    .getResourceAsStream("End_of_course.jpg"), "End_of_course.jpg");

            SendPhoto message = new SendPhoto(String.valueOf(chatId), endOfCoursePhoto);
            message.setCaption(Messages.END_OF_COURSE);

            Bot.inst.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void sendSimpleMessage(long chatId, String message) {
        try {
            Bot.inst.execute(new SendMessage(String.valueOf(chatId), message));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

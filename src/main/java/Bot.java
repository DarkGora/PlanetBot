import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;


public class Bot extends TelegramLongPollingBot {
    public static final String USER_NAME = "";
    public static final String TOKEN = "";
    public Map<Long, Student> heroes = new HashMap<>();
    public List<Question> listQuestion = new ArrayList<>();

    public static final long GROUP_ID = -1002474189401l;

    public Bot() {
        listQuestion.add(new Question("", List.of(""), 2));
        listQuestion.add(new Question("", List.of(""), 1));
        listQuestion.add(new Question("", List.of(""), 0));
        listQuestion.add(new Question("", List.of(""), 0));
        listQuestion.add(new Question("", List.of(""), 0));
        listQuestion.add(new Question("", List.of(""), 2));
        listQuestion.add(new Question("", List.of(""), 2));
        listQuestion.add(new Question("", List.of(""), 0));
        listQuestion.add(new Question("", List.of(""), 0));
        listQuestion.add(new Question("", List.of(""), 3));
    }

    @Override
    public String getBotUsername() {
        return USER_NAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chartId = update.getMessage().getChatId();
            Message message = update.getMessage();
            if (!heroes.containsKey(chartId)) {
                sendMassage(chartId, "Привет! Ну что поехали ");
                heroes.put(chartId, new Student(message.getFrom().getId(), message.getFrom().getFirstName()));
                sendQuestion(chartId, 0);
            }

        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();

            if (heroes.containsKey(chatId)) {
                Student student = heroes.get(chatId);

                int questionIndex = student.getNumber();
                if (questionIndex < listQuestion.size()) {
                    Question question = listQuestion.get(questionIndex);
                    if (callbackData.equals(question.getAnswer().get(question.getIndex()))) {
                        student.veryGoodQuestion();
                        sendMassage(chatId, "Ответ верный! " + student.getGoodQuestion() + " правильных ответов.");
                    } else {
                        sendMassage(chatId, "Ответ неверный. У вас все еще " + student.getGoodQuestion() + " правильных ответов.");
                    }


                    student.addAnswer(callbackData);
                    student.vetynumber();


                    if (student.getNumber() == listQuestion.size()) {
                        sendMassage(chatId, "Тест завершен!");
                        sendMassage(GROUP_ID, student.getFirstName() + " завершил тест с " + student.getGoodQuestion() + " правильными ответами.");
                    }
                }
            }
        }
    }


    private void sendQuestion(Long chatId, int questionIndex) {
        if (questionIndex < listQuestion.size()) {
            Question question = listQuestion.get(questionIndex);
            sendMassage(chatId, question.getName(), question.getAnswer());
        } else {
            sendMassage(chatId, "Все вопросы закончились.");
        }
    }
    private void sendMassage(Long chartId, String text) {
        SendMessage reply = new SendMessage();
        reply.setChatId(chartId.toString());
        reply.setText(text);

        try {
            execute(reply);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void sendMassage (Long chartId, String text, List < String > button){
        SendMessage reply = new SendMessage();
        reply.setChatId(chartId.toString());
        reply.setText(text);
        if (button != null) {
            reply.setReplyMarkup(createButtons(button));
        }
        try {
            execute(reply);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public static InlineKeyboardMarkup createButtons(List<String> buttonsName){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        buttonsName .forEach(name->{
            List<InlineKeyboardButton> rowInLine = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(name);
            button.setCallbackData(name);
            rowInLine.add(button);
            rowsInLine.add(rowInLine);
        });
        inlineKeyboardMarkup.setKeyboard(rowsInLine);

        return inlineKeyboardMarkup;
    }
}




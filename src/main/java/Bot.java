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
    public static final String USER_NAME = "ChartNewbot";
    public static final String TOKEN = "7735646655:AAGOWK0hN_dNlpbPdzaEM4IIuvEFT1orC4U";
    public Map<Long, Student> heroes = new HashMap<>();
    public List<Question> listQuestion = new ArrayList<>();

    public static final long GROUP_ID = -1002474189401l;

    public Bot() {
        listQuestion.add(new Question("Как изначально назывался язык java", List.of("Oak", "Tree", "Brich", "Pine"), 0));
        listQuestion.add(new Question("Кто создал Джаву", List.of("Гоплинг", "Гослинг", "Готлинг", "Годлинг"), 1));
        listQuestion.add(new Question("Сколько байт памяти занимает тип переменных", List.of("2", "4", "8", "16"), 2));
        listQuestion.add(new Question("Два важных ключевых слова, используемых в циклах", List.of("Break и Contine", "Break и Add", "Break и loop", "loop и Add"), 0));
        listQuestion.add(new Question("Какие данные возвращает метод  main()", List.of("String", "Int", "Не может возвращать данные", "Указанные в скобках"), 2));
        listQuestion.add(new Question("Сколько методов у класса  Object", List.of("8", "9", "11", "12"), 2));
        listQuestion.add(new Question("Выберите несуществующий метод Object", List.of("String toString()", "Object clone()", "int hashCode()", "void patify()"), 3));
        listQuestion.add(new Question("Какие элементы может содержать класс", List.of("Поля", "Конструкоры", "Методы", "Интерфейсы", "Все вышеперечислонные"), 4));
        listQuestion.add(new Question("Что означает этот метасимвол регулярных выражений -$ ", List.of("Начало строки", "Конец строки", "Начало слова", "Конец ввода"), 1));
        listQuestion.add(new Question("Что озн  ачает этот метасимвол регулярных выражений -\s ", List.of("Цифровой символ", "Не цифровой символ", "символ пробела", "бкувенно-цифровой символ", "Все вышеперечислонные"), 2));
//hnk;
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
                    }else{
                        ///  sendMessage(GROUP_ID,
                        //   listQuestion.get(student.getLastQuestion(random )).getNameQuestion(random),
                        //  listQuestion.get(student.getLastQuestion(random )).getAnswers(random));
                        //
                        // Question question = listQuestion.get(questionIndex);
                        sendMassage(chatId, question.getName(), question.getAnswer());

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




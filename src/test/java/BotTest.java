import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Path.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class BotTest {

    @Test
    void createButtons() {
        List<String> rowsInLine = List.of("Buttons 1", "Button 2");
        InlineKeyboardMarkup markup = Bot.createButtons(rowsInLine);
        assertNotNull(markup);
        assertEquals(2, markup.getKeyboard().size());
        assertEquals(1, markup.getKeyboard().get(0).size());
        assertEquals(1, markup.getKeyboard().get(1).size());
        assertEquals("Buttons 1", markup.getKeyboard().get(0).get(0).getText());
        assertEquals("Button 2", markup.getKeyboard().get(1).get(0).getText());
        assertEquals("Buttons 1", markup.getKeyboard().get(0).get(0).getCallbackData());
        assertEquals("Button 2", markup.getKeyboard().get(1).get(0).getCallbackData());
    }

    @Test
    void sendMessageTest() {
        long chartId =  -1002474189401l;
        String text= "Hello";
        List<String> listQuestion = List.of("jjj");
        Bot bot = Mockito.spy(Bot.class);
        bot.sendMassage(chartId,  text,listQuestion);
        try {
            verify(bot).execute(any(SendMessage.class));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
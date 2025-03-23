import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

import static java.nio.file.Path.of;
import static org.junit.jupiter.api.Assertions.*;
class BotTest {

    @Test
    void createButtons() {
        List <String> rowsInLine= List.of("Buttons 1","Button 2");
        InlineKeyboardMarkup markup =Bot.createButtons(rowsInLine);
        assertNotNull(markup);
        assertEquals(2,markup.getKeyboard().size());
        assertEquals(1,markup.getKeyboard().get(0).size());
        assertEquals(1,markup.getKeyboard().get(1).size());
        assertEquals("Buttons 1",markup.getKeyboard().get(0).get(0).getText());
        assertEquals("Button 2",markup.getKeyboard().get(1).get(0).getText());
        assertEquals("Buttons 1", markup.getKeyboard().get(0).get(0).getCallbackData());
        assertEquals("Button 2", markup.getKeyboard().get(1).get(0).getCallbackData());
    }
}
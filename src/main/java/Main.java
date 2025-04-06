
import lombok.extern.log4j.Log4j;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.logging.LogManager;
public class Main {
    public static final String PROXY_HOST = "84.247.168.26";
    public static final int PROXY_PORT = 40245;
    private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(Main.class);

    public static void main(String[] args) throws TelegramApiException {
        log.error("Program start");
        DefaultBotOptions botOptions = new DefaultBotOptions();
        botOptions.setProxyHost(PROXY_HOST);
        botOptions.setProxyPort(PROXY_PORT);
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS4);

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        Bot bot = new Bot();
        log.info("bot создан");
        try {
            telegramBotsApi.registerBot(bot);
        }catch (TelegramApiRequestException e){
            log.error("TelegramApiRequestException:"+e.getMessage());
            e.printStackTrace();

        }
    }
}
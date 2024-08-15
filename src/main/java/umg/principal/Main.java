package umg.principal;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import umg.principal.TelegramBot.tareaBot;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {


            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            tareaBot mibot = new tareaBot();
            botsApi.registerBot(mibot);

            System.out.println("¡Good! Bot ejecutándose");
        } catch (  Exception ex ) {

            System.out.println("¡Warning! Bot no ejecutado" +ex.getMessage());
        }
    }
}
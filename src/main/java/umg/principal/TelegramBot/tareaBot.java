package umg.principal.TelegramBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class tareaBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "PrograJGbot";
    }

    @Override
    public String getBotToken() {
        return "7459219621:AAE-AW4p6URdHcqPmoU5u-ILIgl7PCA5idE";
    }

    @Override
    public void onUpdateReceived(Update update) {
        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        String nombre = update.getMessage().getFrom().getFirstName();
        String apellido = update.getMessage().getFrom().getLastName();
        String nickname = update.getMessage().getFrom().getUserName();

        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            if (messageText.equalsIgnoreCase("/start")) {
                sendText(chatId, "¡Ayooo! Bienvenido a mi botcito\n\n" +
                        "Que pex, te dejo acá las instrucciones:\n" +
                        "/info - (Información personal)\n" +
                        "/progra - (Comentarios sobre la clase de programación)\n" +
                        "/hola - (Saludo con la fecha actual)\n" +
                        "/cambio [cantidad] - Conversión de Euros a Quetzales\n" +
                        "/grupal [mensaje] - Enviar mensaje a tus compañeros");
            }

            if (message_text.toLowerCase().equals("/info")){
                sendText(chat_id,"Nombre: [Juan Neftalí Gamez Martínez]" +
                        "\nCarnet: [0905-22-21541]" +
                        "\nSemestre: 6to");
            }

            else if (message_text.toLowerCase().equals("/progra")){
                sendText(chat_id,"Comentario: Está chida la clase");
            }

            else if (messageText.equalsIgnoreCase("/hola")) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy");
                String fecha = now.format(formatter);
                sendText(chatId, "Hola, " + nombre + ", hoy es " + fecha);
            }

            else if (messageText.startsWith("/cambio ")) {
                String[] parts = messageText.split(" ");
                double euros = Double.parseDouble(parts[1]);
                double tipoCambio = 8.9;  // Actualiza con el tipo de cambio actual
                double quetzales = euros * tipoCambio;
                sendText(chatId, "Son " + quetzales + " quetzales.");
            }

            else if (messageText.startsWith("/grupal ")) {
                String mensaje = messageText.substring(8);
                List<Long> chatIds = List.of(123456789L, 987654321L);  // Agrega los chat IDs de tus compañeros
                for (Long id : chatIds) {
                    sendText(id, mensaje);
                }
            }
            System.out.println("Hola "+nombre+ " tu nombre es: "+nombre+" "+apellido);
            System.out.println("Enviaste "+message_text);
        }
    }

    public void sendText(Long who, String what){
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString()) //Who are we sending a message to
                .text(what).build();    //Message content
        try {
            execute(sm);                        //Actually sending the message
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);      //Any error will be printed here
        }
    }
}

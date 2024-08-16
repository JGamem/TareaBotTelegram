package umg.principal.TelegramBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.InputStream;
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
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String nombre = update.getMessage().getFrom().getFirstName();
            String apellido = update.getMessage().getFrom().getLastName();
            String nickname = update.getMessage().getFrom().getUserName();

            if (messageText.equalsIgnoreCase("/start")) {
                sendImage(chatId, "img/umgLogo.png");
                sendText(chatId, "¡Ayooo! Bienvenido a mi botcito\n\n" +
                        "Que onda! te dejo acá las instrucciones:\n\n" +
                        "/info - (Información personal)\n" +
                        "/progra - (Comentarios sobre la clase de programación)\n" +
                        "/hola - (Saludo con la fecha actual)\n" +
                        "/cambio [cantidad] - Conversión de Euros a Quetzales\n" +
                        "/grupal [mensaje] - Enviar mensaje a tus compañeros\n" +
                        "/maven - Imagen de referencia Maven Telegrambots");
            }

            if (messageText.equalsIgnoreCase("/maven")) {
                sendText(chatId, "Referencia de maven de telegrambots:");
                sendImage(chatId, "img/MavenTelegrambots.png");
            }

            if (messageText.equalsIgnoreCase("/info")) {
                sendText(chatId, "Nombre: [Juan Neftalí Gamez Martínez]" +
                        "\nCarnet: [0905-22-21541]" +
                        "\nSemestre: 6to");
            }

            else if (messageText.equalsIgnoreCase("/progra")) {
                sendText(chatId, "Comentario: Está chida la clase");
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
                double tipoCambio = 8.90;
                double quetzales = euros * tipoCambio;
                sendText(chatId, "Son " + quetzales + " quetzales.");
            }

            else if (messageText.startsWith("/grupal ")) {
                String mensaje = messageText.substring(8);
                List<Long> chatIds = List.of(7070992511L, 6313324344L, 2064783549L, 1645907091L);
                for (Long id : chatIds) {
                    sendText(id, mensaje);
                }
            }

            System.out.println("Hola " + nombre + " tu nombre es: " + nombre + " " + apellido);
            System.out.println("Enviaste " + messageText);
            System.out.println("Chat ID: " + chatId);
        }
    }

    public void sendText(Long who, String what) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendImage(Long chatId, String resourcePath) {
        // Cargar la imagen desde los recursos del proyecto usando ClassLoader
        InputStream imageStream = getClass().getClassLoader().getResourceAsStream(resourcePath);

        if (imageStream != null) {
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(chatId.toString());
            sendPhoto.setPhoto(new InputFile(imageStream, "MavenTelegrambots.png"));
            sendPhoto.setPhoto(new InputFile(imageStream, "umgLogo.png"));

            try {
                execute(sendPhoto);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            sendText(chatId, "No hay imagen para enviar " + resourcePath);
        }
    }
}

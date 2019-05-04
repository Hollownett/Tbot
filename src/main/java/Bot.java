//import com.google.inject.spi.Message;
import java.io.IOException;
import java.util.List;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;


public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi= new TelegramBotsApi();
        try{
            telegramBotsApi.registerBot(new Bot());
        }
        catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

public void sendMsg(Message message, String text){
        SendMessage sendMessage= new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try{
            setButtons(sendMessage);
            sendMessage(sendMessage);

        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

public void setButtons(SendMessage sendMessage){
    ReplyKeyboardMarkup replyKeyboardMarkup=new ReplyKeyboardMarkup();
    sendMessage.setReplyMarkup(replyKeyboardMarkup);
    replyKeyboardMarkup.setSelective(true);
    replyKeyboardMarkup.setResizeKeyboard(true);
    replyKeyboardMarkup.setOneTimeKeyboard(false);


    List<KeyboardRow> keyboardRowList = new ArrayList<KeyboardRow>();
    KeyboardRow keyboardFirstRow = new KeyboardRow();

    keyboardFirstRow.add(new KeyboardButton("/h"));
    keyboardFirstRow.add(new KeyboardButton("/settigns"));




    keyboardRowList.add(keyboardFirstRow);
    replyKeyboardMarkup.setKeyboard(keyboardRowList);
}

    public void onUpdateReceived(Update update) {
        Model model= new Model();
        Message message = update.getMessage();
        if(message!=null && (message.hasText())){
            switch (message.getText()){
                case"/h":
                    sendMsg(message, "чем могу помочь, пидр?");
                            break;
                case "/settigns":
                    sendMsg(message, "что настроить?");
                            break;
                    default:
                        try {
                            sendMsg(message,Wheather.getWeather(message.getText(), model));
                        }catch(IOException e){
                            sendMsg(message,"Нет города такого!!Пидр");
                        }
            }

        }

    }

    public String getBotUsername() {
        return "WetherBot_bot";
    }

    public String getBotToken() {
        return "812360764:AAHKO9ffzh9bXwX8-W7k_PG_SXXhMAq7o_Q";
    }
}

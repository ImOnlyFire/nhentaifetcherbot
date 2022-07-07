package me.onlyfire.whyamidoingthis;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.Random;

public class FetcherBot {

    private static final String URL = "https://nhentai.to/g/%s/";

    public static void main(String[] args) {
        TelegramBot bot = new TelegramBot(System.getenv("NFETCHER_TELEGRAM_TOKEN"));
        System.out.println("bot is online.");


        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                System.out.println(update);
                Message message = update.message();
                if (message != null) {
                    String text = message.text();
                    if (text != null) {
                        String[] split = text.split(" ");
                        if (split[0].equalsIgnoreCase("/fetch")) {
                            if (split.length != 2) {
                                bot.execute(new SendMessage(message.chat().id(), "Correct usage: /fetch <id>"));
                            } else {
                                try {
                                    bot.execute(new SendMessage(message.chat().id(), String.format(URL, Integer.parseInt(split[1]))));
                                } catch (NumberFormatException e) {
                                    bot.execute(new SendMessage(message.chat().id(), "Incorrect id"));
                                }
                            }
                        } else if (split[0].equalsIgnoreCase("/nrandom")) {
                            bot.execute(new SendMessage(message.chat().id(), String.format(URL,
                                    new Random().nextInt(0, 250000))));
                        }
                    }
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

    }

}

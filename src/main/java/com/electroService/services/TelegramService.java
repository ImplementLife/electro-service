package com.electroService.services;

import com.electroService.entitys.order.Order;
import com.electroService.entitys.telegram.Chats;
import com.electroService.repositories.telegram.ChatsDao;
import com.electroService.services.order.OrderService;
import com.electroService.telegram.Bot;
import com.electroService.telegram.Com;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

@Service
@Slf4j
public class TelegramService {
    private static TelegramBotsApi botsApi;
    private static Bot bot;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ChatsDao chatsDao;

    private String name = "Electro-service bot";
    private String token = "1846232018:AAHdxuH0bsgCncN-Y8ngdfZcXKrJST0VzZ4";

    public Bot telegramBotInit() {
        log.info("telegram.bot.name={}", name);
        log.info("telegram.bot.token={}", token);
        try {
            if (botsApi == null) {
                botsApi = new TelegramBotsApi(DefaultBotSession.class);
            }
            if (bot == null) {
                bot = new Bot(name, token);

                String[] buttons = Arrays.asList(
                        "/getCountOrders",
                        "/checkNewOrders",
                        "/subscribe",
                        "/unsubscribe"
                ).toArray(new String[0]);

                bot.register(new Com("start", "Погнали!", buttons));
                bot.register(new Com("getCountOrders", this::getCountOrders, buttons));
                bot.register(new Com("checkNewOrders", this::getCountNewOrders, buttons));
                bot.register(new Com("subscribe", this::subscribe, buttons));
                bot.register(new Com("unsubscribe", this::unsubscribe, buttons));

                botsApi.registerBot(bot);
                log.info("Telegram bot init successful.");
            }
        } catch (TelegramApiException e) {
            log.error("Telegram boi init Error", e);
        }
        return bot;
    }

    private String getCountOrders() {
        Collection<Order> newOrders = orderService.getAll();
        return String.format("Количество заказов: %s;", newOrders.size());
    }
    private String getCountNewOrders() {
        Collection<Order> newOrders = orderService.getByStatusId(1L);
        return String.format("Количество новых заказов: %s;", newOrders.size());
    }

    private String subscribe(Chat chat) {
        if (chatsDao.findByChatId(chat.getId()) != null) {
            return "Уже подписан";
        }
        Chats chats = new Chats();
        chats.setChatId(chat.getId());
        chatsDao.save(chats);
        return "Теперь в этот чат будут приходить уведомления о новых заказах.";
    }

    private String unsubscribe(Chat chat) {
        Chats chats = chatsDao.findByChatId(chat.getId());
        if (chats == null) {
            return "Ещё не подписан";
        }
        chatsDao.deleteById(chats.getId());
        return "Уведомления о новых заказах отключены.";
    }

    public void notificationAllSubscribers(Order order) {
        List<Chats> allChats = chatsDao.findAll();
        for (Chats chat : allChats) {
            bot.send(chat.getChatId(),
                    String.format("На сайт был добавлен новый заказ: " +
                    "https://electro-service.dp.ua/admin/orders/%s",
                    order.getId())
            );
        }
    }

}

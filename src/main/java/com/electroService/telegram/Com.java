package com.electroService.telegram;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Com extends BotCommand {
    private ReplyKeyboardMarkup keyboard;
    private String response;
    private RS rs;
    private RSC rsc;

    public Com(String commandIdentifier, RS rs, String... buttons) {
        this(commandIdentifier, buttons);
        this.rs = rs;
    }
    public Com(String commandIdentifier, RSC rsc, String... buttons) {
        this(commandIdentifier, buttons);
        this.rsc = rsc;
    }

    public Com(String commandIdentifier, String... buttons) {
        this(commandIdentifier, (String) null, buttons);
    }

    public Com(String commandIdentifier, String response, String... buttons) {
        this(commandIdentifier, "", response, buttons);
    }

    public Com(String commandIdentifier, String description, String response, String... buttons) {
        super(commandIdentifier, description);
        this.response = response;

        keyboard = new ReplyKeyboardMarkup();
        keyboard.setSelective(true);
        keyboard.setResizeKeyboard(true);

        List<KeyboardRow> keyCol = new ArrayList<>();
        KeyboardRow keyRow = new KeyboardRow();
        keyCol.add(keyRow);
        for (String str : buttons) keyRow.add(new KeyboardButton(str));
        this.keyboard.setKeyboard(keyCol);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage message = new SendMessage();
        message.setReplyMarkup(this.keyboard);
        if (response != null) {
            message.setText(response);
        } else if (rs != null) {
            message.setText(rs.getResponse());
        } else if (rsc != null) {
            message.setText(rsc.getResponse(chat));
        } else {
            message.setText("no message");
        }
        message.setChatId(chat.getId().toString());
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static class ComBuilder {
        private String commandIdentifier;
        private String description;
        private String response;
        private String[] buttons;

        public ComBuilder setCommandIdentifier(String commandIdentifier) {
            this.commandIdentifier = commandIdentifier;
            return this;
        }

        public ComBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public ComBuilder setResponse(String response) {
            this.response = response;
            return this;
        }

        public ComBuilder setButtons(String[] buttons) {
            this.buttons = buttons;
            return this;
        }

        public Com build() {
            return new Com(commandIdentifier, description, response, buttons);
        }
    }
}

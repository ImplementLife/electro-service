package com.electroService.telegram;

import org.telegram.telegrambots.meta.api.objects.Chat;

@FunctionalInterface
public interface RSC {
    String getResponse(Chat chat);
}

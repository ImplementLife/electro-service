package com.electroService.repositories.telegram;

import com.electroService.entitys.telegram.Chats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatsDao extends JpaRepository<Chats, Long> {
    Chats findByChatId(Long chatId);
}

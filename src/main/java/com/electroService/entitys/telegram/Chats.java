package com.electroService.entitys.telegram;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter

@Entity
@Table(name = "chats")
public class Chats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chatId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chats chats = (Chats) o;
        return Objects.equals(id, chats.id) && Objects.equals(chatId, chats.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId);
    }
}

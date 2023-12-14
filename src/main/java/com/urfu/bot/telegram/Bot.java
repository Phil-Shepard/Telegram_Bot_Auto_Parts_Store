package com.urfu.bot.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * Интерфейс бота
 */
public interface Bot {
    /**
     * Выводит сообщение
     * @param message класс сообщения
     */
    void sendMessage(SendMessage message);
}

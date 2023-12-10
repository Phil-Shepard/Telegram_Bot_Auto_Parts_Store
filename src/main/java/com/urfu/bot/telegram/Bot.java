package com.urfu.bot.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * Интерфейс бота, для выполнения некоторых команд
 */
public interface Bot {
    /**
     *
     * @param message класс сообщения
     */
    void sendMessage(SendMessage message);
}

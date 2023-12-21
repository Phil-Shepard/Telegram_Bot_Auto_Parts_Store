package com.urfu.bot;

import com.urfu.domain.message.MessageToUser;

/**
 * Интерфейс бота
 */
public interface Bot {
    /**
     * Выводит сообщение
     * @param message класс сообщения
     */
    void sendMessage(MessageToUser message);
}

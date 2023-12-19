package com.urfu.domain.message;

/**
 * Сообщение от пользователя
 */
public class MessageFromUser {
    private final long chatId;
    private final String message;
    private final String name;

    public MessageFromUser(long chatId, String message, String name) {
        this.chatId = chatId;
        this.message = message;
        this.name = name;
    }

    /**
     * Получение id чата
     *
     * @return Id текущего чата
     */
    public long getChatId() {
        return chatId;
    }

    /**
     * Получение текста сообщения
     *
     * @return Текст сообщения
     */
    public String getMessage() {
        return message;
    }

    /**
     * Получение имя пользователя
     *
     * @return Имя пользователя
     */
    public String getUserName() {
        return name;
    }
}

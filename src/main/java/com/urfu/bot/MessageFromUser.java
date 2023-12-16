package com.urfu.bot;

/*
    Class for working with information from User
 */
public class MessageFromUser {
    private long chatId;
    private String message;
    private String name;

    MessageFromUser(long chatId, String message, String name) {
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

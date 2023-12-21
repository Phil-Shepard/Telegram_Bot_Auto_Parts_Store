package com.urfu.domain.message;

/**
 * Сообщение от бота
 */
public class MessageToUser {
    private final long chatId;
    private final String text;
    private final Boolean removeMarkup;
    private final String buttonNamesSeparatedBySpaces;

    public MessageToUser(long chatId, String text, Boolean removeMarkup, String buttonNamesSeparatedBySpaces) {
        this.chatId = chatId;
        this.text = text;
        this.removeMarkup = removeMarkup;
        this.buttonNamesSeparatedBySpaces = buttonNamesSeparatedBySpaces;
    }

    public MessageToUser() {
        this.chatId = 0;
        this.text = null;
        this.removeMarkup = null;
        this.buttonNamesSeparatedBySpaces = null;
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
     * Получение флага удаления кнопок
     *
     * @return Флаг удаления кнопок
     */
    public Boolean getRemoveMarkup() {
        return removeMarkup;
    }


    /**
     * Получение текста сообщения
     *
     * @return Текст сообщения
     */
    public String getText() {
        return text;
    }

    /**
     * Возвращает названия кнопок, которые нужно добавить пользователю
     *
     * @return Название кнопок с пробелами
     */
    public String getButtonNamesSeparatedBySpaces() {
        return buttonNamesSeparatedBySpaces;
    }
}

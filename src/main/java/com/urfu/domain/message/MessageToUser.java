package com.urfu.domain.message;

/**
 * Сообщение от бота
 */
public class MessageToUser implements Cloneable {
    private long chatId;
    private String text = null;
    private Boolean replyMarkup = false;
    private String buttonNamesSeparatedBySpaces = null;

    /**
     * Установка id чата
     *
     * @param chatId Id текущего чата
     */
    public void setChatId(long chatId) {
        this.chatId = chatId;
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
     * Установление параметра для удаления кнопок
     *
     * @param flagRemoved Флаг удаления кнопок
     */
    public void setRemoveKeyboard(Boolean flagRemoved) {
        if (flagRemoved == null) {
            throw new NullPointerException("flagRemoved is marked non-null but is null");
        } else {
            this.replyMarkup = flagRemoved;
        }
    }

    /**
     * Получение флага удаления кнопок
     *
     * @return Флаг удаления кнопок
     */
    public Boolean getReplyMarkup() {
        return replyMarkup;
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
     * Добавление текста в сообщение
     *
     * @param text Текст сообщения
     */
    public void setText(String text) {
        if (text == null) {
            throw new NullPointerException("text is marked non-null but is null");
        } else {
            this.text = text;
        }
    }

    /**
     * Возвращает названия кнопок, которые нужно добавить пользователю
     *
     * @return Название кнопок с пробелами
     */
    public String getButtonNamesSeparatedBySpaces() {
        return buttonNamesSeparatedBySpaces;
    }

    /**
     * Устанавливает названия кнопок, которые нужно добавить пользователю
     *
     * @param buttonNamesSeparatedBySpaces Название кнопок с пробелами
     */
    public void setButtonNamesSeparatedBySpaces(String buttonNamesSeparatedBySpaces) {
        this.buttonNamesSeparatedBySpaces = buttonNamesSeparatedBySpaces;
    }

    @Override
    public MessageToUser clone() {
        try {
            MessageToUser clone = (MessageToUser) super.clone();
            clone.chatId = this.chatId;
            clone.text = this.text;
            clone.replyMarkup = this.replyMarkup;
            clone.buttonNamesSeparatedBySpaces = this.buttonNamesSeparatedBySpaces;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

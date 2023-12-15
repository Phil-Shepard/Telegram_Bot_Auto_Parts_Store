package com.urfu.bot.telegram;

/**
 * Необходимая конфигурационная информация для бота.
 */
public class BotConfig {
    private final String botName = System.getenv("NAME");
    private final String botToken =  System.getenv("TOKEN");

    /**
     * Возвращает необходимый для работы бота токен.
     * @return
     */
    public String getBotToken(){
        return botToken;
    }

    /**
     * Возвращает имя нашего бота.
     * @return
     */
    public String getBotName(){
        return botName;
    }
}

package com.urfu.bot.telegram;

/**
 * Необходимая конфигурационная информация для бота.
 */
public class BotConfig {
    private final String botName = "EKBAutoPartsStoreBot";
    private final String botToken = "6661500007:AAFEMtc9b4GSHMvynNhimKRopwNPdsQaQ2U";

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

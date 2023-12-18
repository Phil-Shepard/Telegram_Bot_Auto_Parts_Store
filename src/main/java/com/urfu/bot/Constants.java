package com.urfu.bot;

/**
 * Класс для текстовых констант
 */
public class Constants {
    public static final String COMMAND_START = "/start";
    public static final String COMMAND_EXIT = "/exit";
    public static final String COMMAND_SHOP = "/shop";
    public static final String COMMAND_HELP = "/help";
    public static final String HELP = """
            Справка о дуступных командах:
            /shop
            /add
            /basket
            /order
            /history
            /delete
            /exit
            /help""";
    public static final String TEXT_START = """
             Доступны следующие команды:
            /shop – Перейти в каталог запчастей.
            /add - добавить в корзину выбранную запчасть.
            /basket - вывести содержимое корзины.
            /order - оформить заказ
            /history - вывести историю заказов.
            /delete - удалить из корзины выбранные комплектующие.
            /exit - выйти из каталога запчастей.
            /help - Справка.
            """;
}

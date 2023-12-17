package com.urfu.bot;

import com.urfu.commands.Commands;
import org.junit.Assert;
import org.junit.Test;

import static com.urfu.bot.Constants.*;

/**
 * Класс тестов для проверки работы команд для бота
 */
public class TestCommands {

    /**
     * Тест, проверяющий выполнение команды /start
     */
    @Test
    public void testStart() {
        FakeBot fakeBot = new FakeBot();
        Commands commands = new Commands(fakeBot);
        MessageFromUser messageFromUser = new MessageFromUser(0L, COMMAND_START, "John");

        commands.onUpdateReceived(messageFromUser);

        Assert.assertEquals("""
                Привет, John, Это телеграмм бот магазина  автозапчастей. Доступны следующие команды:
                /shop – Перейти в каталог запчастей.
                /add - добавить в корзину выбранную запчасть.
                /basket - вывести содержимое корзины.
                /order - оформить заказ
                /history - вывести историю заказов.
                /delete - удалить из корзины выбранные комплектующие.
                /exit - выйти из каталога запчастей.
                /help - Справка.
                """, fakeBot.getLastMessage());
    }

    /**
     * Тест проверяющий выполнение команды /help
     */
    @Test
    public void testHelp(){
        FakeBot fakeBot = new FakeBot();
        Commands commands = new Commands(fakeBot);
        MessageFromUser messageFromUser = new MessageFromUser(0L, COMMAND_HELP, "John");

        commands.onUpdateReceived(messageFromUser);

        Assert.assertEquals("""
                Справка о дуступных командах:
                /shop
                /add
                /basket
                /order
                /history
                /delete
                /exit
                /help""", fakeBot.getLastMessage());
    }

    /**
     * Тест, проверяющий выполнение команды /shop
     */
    @Test
    public void testShop(){
        FakeBot fakeBot = new FakeBot();
        Commands commands = new Commands(fakeBot);
        MessageFromUser messageFromUser = new MessageFromUser(0L, COMMAND_SHOP, "John");

        commands.onUpdateReceived(messageFromUser);

        Assert.assertEquals("""
                 В наличии комплектующие для автомобилей:
                 BMW, Renault, Lada""", fakeBot.getLastMessage());

        String keyboardRows = fakeBot.getMessage().getButtonNamesSeparatedBySpaces();

        Assert.assertFalse(keyboardRows.isEmpty());

        Assert.assertEquals("BMW Renault Lada ", keyboardRows);
    }
}

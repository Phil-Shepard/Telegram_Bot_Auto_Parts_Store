package com.urfu.bot;

import com.urfu.domain.car.Car;
import com.urfu.domain.message.MessageFromUser;
import com.urfu.services.CarService;
import org.junit.Assert;
import org.junit.Test;

import static com.urfu.bot.Constants.*;

/**
 * Класс тестов для проверки работы команд для бота
 */
public class TestBotLogic {

    /**
     * Тест, проверяющий выполнение команды /start
     */
    @Test
    public void testStart() {
        FakeBot fakeBot = new FakeBot();
        BotLogic botLogic = new BotLogic(fakeBot);
        MessageFromUser messageFromUser = new MessageFromUser(0L, COMMAND_START, "John");

        botLogic.onUpdateReceived(messageFromUser);

        Assert.assertEquals("""
                Привет, John, это телеграмм бот магазина автозапчастей. Доступны следующие команды:
                /shop – Перейти в каталог запчастей.
                /basket - Вывести содержимое корзины.
                /order - Оформить заказ.
                /history - Вывести историю заказов.
                /delete - Удалить все содержимое корзины.
                /exit - Выйти из каталога запчастей.
                /help - Справка.
                """, fakeBot.getLastMessage());
    }

    /**
     * Тест проверяющий выполнение команды /help
     */
    @Test
    public void testHelp() {
        FakeBot fakeBot = new FakeBot();
        BotLogic botLogic = new BotLogic(fakeBot);
        MessageFromUser messageFromUser = new MessageFromUser(0L, COMMAND_HELP, "John");

        botLogic.onUpdateReceived(messageFromUser);

        Assert.assertEquals("""
                Справка о доступных командах:
                /shop
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
    public void testShop() {
        FakeBot fakeBot = new FakeBot();
        BotLogic botLogic = new BotLogic(fakeBot);
        MessageFromUser messageFromUser = new MessageFromUser(0L, COMMAND_SHOP, "John");

        botLogic.onUpdateReceived(messageFromUser);

        Assert.assertEquals("""
                В наличии комплектующие для автомобилей:
                BMW, Renault, Lada""", fakeBot.getLastMessage());

        Assert.assertFalse(fakeBot.getMessage().getReplyMarkup());

        String keyboardRows = fakeBot.getMessage().getButtonNamesSeparatedBySpaces();

        Assert.assertFalse(keyboardRows.isEmpty());

        Assert.assertEquals("BMW Renault Lada", keyboardRows);
    }

    /**
     * Тест, проверяющий выполнение команды /exit
     */
    @Test
    public void testExit() {
        FakeBot fakeBot = new FakeBot();
        BotLogic botLogic = new BotLogic(fakeBot);
        MessageFromUser messageFromUser = new MessageFromUser(0L, COMMAND_EXIT, "John");

        botLogic.onUpdateReceived(messageFromUser);

        Assert.assertEquals("Вы закрыли каталог товаров", fakeBot.getLastMessage());

        Assert.assertTrue(fakeBot.getMessage().getReplyMarkup());
    }

    /**
     * Тест проверяет, что при команде в виде названия автомобиля
     */
    @Test
    public void testCommandCars() {
        FakeBot fakeBot = new FakeBot();
        BotLogic botLogic = new BotLogic(fakeBot);
        CarService carService = new CarService();
        MessageFromUser messageFromUser;
        String keyboardRows;

        for (String carName : carService.getCars().stream().map(Car::getName).toList()) {
            messageFromUser = new MessageFromUser(0L, carName, "John");
            botLogic.onUpdateReceived(messageFromUser);
            Assert.assertEquals(carService.getCar(messageFromUser.getUserName()).getAvailabilityParts(), fakeBot.getLastMessage());
            keyboardRows = fakeBot.getMessage().getButtonNamesSeparatedBySpaces();
            Assert.assertFalse(keyboardRows.isEmpty());
        }
    }
}

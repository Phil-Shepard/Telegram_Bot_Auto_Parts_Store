package com.urfu.bot;

import com.urfu.domain.basket.Basket;
import com.urfu.domain.car.Car;
import com.urfu.services.CarService;
import com.urfu.storage.Storage;
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
        fakeBot.onUpdateReceived(COMMAND_START);

        Assert.assertEquals("""
                Привет, John, это телеграм бот магазина автозапчастей.
                Доступны следующие команды:
                /shop – Перейти в каталог запчастей.
                /basket - Вывести содержимое корзины.
                /order - Оформить заказ.
                /orderHistory - Вывести историю заказов.
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
        fakeBot.onUpdateReceived(COMMAND_HELP);

        Assert.assertEquals("""
                Справка о доступных командах:
                /shop
                /basket
                /order
                /orderHistory
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
        fakeBot.onUpdateReceived(COMMAND_SHOP);

        Assert.assertEquals("""
                В наличии комплектующие для автомобилей:
                BMW, Renault, Lada""", fakeBot.getLastMessage());

        Assert.assertFalse(fakeBot.getMessage().getRemoveMarkup());

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
        fakeBot.onUpdateReceived(COMMAND_EXIT);

        Assert.assertEquals("Вы закрыли каталог товаров", fakeBot.getLastMessage());

        Assert.assertTrue(fakeBot.getMessage().getRemoveMarkup());
    }

    /**
     * Тест проверяет, что при команде в виде названия автомобиля выводятся автозапчасти, которые есть в наличии
     * на этот автомобиль.
     */
    @Test
    public void testCommandCars() {
        FakeBot fakeBot = new FakeBot();
        Storage storage = new Storage();
        CarService carService = new CarService();
        String keyboardRows;

        for (String carName : storage.getListCars().stream().map(Car::getName).toList()) {
            fakeBot.onUpdateReceived(carName);
            Assert.assertEquals(carService.getCar(carName).getAvailabilityParts(", "), fakeBot.getLastMessage());
            keyboardRows = fakeBot.getMessage().getButtonNamesSeparatedBySpaces();
            Assert.assertFalse(keyboardRows.isEmpty());
        }
    }

    /**
     * Тест проверяет, что при команде в виде автозапчасти, она добавляется в корзину.
     */
    @Test
    public void testCommandSpareParts() {
        FakeBot fakeBot = new FakeBot();
        Storage storage = new Storage();
        CarService carService = new CarService();

        for (String carName : storage.getListCars().stream().map(Car::getName).toList()) {
            fakeBot.onUpdateReceived(carName);
            for (String sparePart : carService.getCar(carName).getAvailabilityParts(" ").split(" ")) {
                fakeBot.onUpdateReceived(sparePart);
                Assert.assertEquals("Товар \"" + sparePart + "\" успешно добавлен в корзину.",
                        fakeBot.getLastMessage());
                Assert.assertTrue(fakeBot.getBasket().getBasket().containsKey(carName));
                Assert.assertEquals(sparePart, fakeBot.getBasket().getBasket().get(carName).get(0).getName());
                fakeBot.getBasket().deleteAllPartsFromBasket();
            }
        }
    }

    /**
     * Тест проверяет, что при вызове команды /basket, выводится содержимое корзины
     */
    @Test
    public void testCommandBasket() {
        FakeBot fakeBot = new FakeBot();

        fakeBot.onUpdateReceived("Lada");
        fakeBot.onUpdateReceived("фары");
        fakeBot.onUpdateReceived("колёса");
        fakeBot.onUpdateReceived("фары");
        fakeBot.onUpdateReceived("/basket");
        Assert.assertEquals("""
                Содержимое корзины:
                Машина: Lada
                Запчасти:
                фары - 2
                колёса - 1""", fakeBot.getLastMessage());
    }

    /**
     * Тест проверяет, что при вызове команды /order, корзина очищается а на экран выводится текст о успешном заказе
     */
    @Test
    public void testCommandOrder() {
        FakeBot fakeBot = new FakeBot();

        fakeBot.onUpdateReceived("BMW");
        fakeBot.onUpdateReceived("фары");
        fakeBot.onUpdateReceived("/order");

        Assert.assertEquals("""
                Успешно заказано:
                Машина: BMW
                Запчасти:
                фары - 1""", fakeBot.getLastMessage());

        Assert.assertTrue(fakeBot.getBasket().getBasket().isEmpty());
    }

    /**
     * Тест проверяет, что при вызове команды /orderHistory, выводится история заказов
     */
    @Test
    public void testCommandHistory(){
        FakeBot fakeBot = new FakeBot();

        fakeBot.onUpdateReceived("BMW");
        fakeBot.onUpdateReceived("фары");
        fakeBot.onUpdateReceived("/order");

        fakeBot.onUpdateReceived("Lada");
        fakeBot.onUpdateReceived("колёса");
        fakeBot.onUpdateReceived("/order");

        fakeBot.onUpdateReceived("/orderHistory");

        Assert.assertEquals("""
                История заказов:
                      
                Машина: BMW
                Запчасти:
                фары - 1
                                
                Машина: Lada
                Запчасти:
                колёса - 1""", fakeBot.getLastMessage());
    }
}

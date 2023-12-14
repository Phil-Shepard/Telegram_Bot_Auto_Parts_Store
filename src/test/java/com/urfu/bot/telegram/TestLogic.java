package com.urfu.bot.telegram;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.*;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import static com.urfu.bot.telegram.Constants.*;


/**
 * Класс тестов для проверки работы команд для бота
 */
public class TestLogic {

    /**
     * Метод создает моки объектов Update и заполняет список messages сообщениями от бота.
     * @param command команда, вводимая пользователем
     * @return мок объекта Update
     */
    private Update createUpdate(String command) {
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);
        Chat chatMock = Mockito.mock(Chat.class);

        Mockito.when(updateMock.hasMessage()).thenReturn(true);
        Mockito.when(updateMock.getMessage()).thenReturn(messageMock);
        Mockito.when(messageMock.hasText()).thenReturn(true);
        Mockito.when(messageMock.getText()).thenReturn(command);
        Mockito.when(messageMock.getChatId()).thenReturn(0L);
        Mockito.when(messageMock.getChat()).thenReturn(chatMock);
        Mockito.when(chatMock.getFirstName()).thenReturn("John");

        return updateMock;
    }

    /**
     * Тест, проверяющий выполнение команды /start
     */
    @Test
    public void testStart() {
        FakeBot fakeBot = new FakeBot();
        Logic logic = new Logic(fakeBot);

        logic.onUpdateReceived(createUpdate(COMMAND_START));

        Assert.assertEquals("Привет, " + "John" +  ", Это телеграмм бот магазина  автозапчастей." +
                " Доступны следующие команды:\n" +
                "/shop – Перейти в каталог запчастей.\n" +
                "/add - добавить в корзину выбранную запчасть.\n" +
                "/basket - вывести содержимое корзины.\n" +
                "/order - оформить заказ\n" +
                "/history - вывести историю заказов.\n" +
                "/delete - удалить из корзины выбранные комплектующие.\n" +
                "/exit - выйти из каталога запчастей.\n" +
                "/help - Справка.\n", fakeBot.getLastMessage());
    }

    /**
     * Тест проверяющий выполнение команды /help
     */
    @Test
    public void testHelp(){
        FakeBot fakeBot = new FakeBot();
        Logic logic = new Logic(fakeBot);

        logic.onUpdateReceived(createUpdate(COMMAND_HELP));

        Assert.assertEquals("Справка о дуступных командах:\n" +
                "/shop\n" +
                "/add\n" +
                "/basket\n" +
                "/order\n" +
                "/history\n" +
                "/delete\n" +
                "/exit\n" +
                "/help", fakeBot.getLastMessage());
    }

    /**
     * Тест, проверяющий выполнение команды /shop
     */
    @Test
    public void testShop(){
        FakeBot fakeBot = new FakeBot();
        Logic logic = new Logic(fakeBot);

        logic.onUpdateReceived(createUpdate(COMMAND_SHOP));

        Assert.assertEquals("В наличии комплектующиие для автомобилей: \n" + " BMW, Renault, Lada", fakeBot.getLastMessage());
    }
}

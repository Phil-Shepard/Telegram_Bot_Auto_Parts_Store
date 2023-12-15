package com.urfu.bot.telegram;

import com.urfu.bot.Logic;
import org.junit.Assert;
import org.junit.Test;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;
import java.util.stream.Collectors;

import static com.urfu.bot.Constants.*;

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
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();

        message.setText(command);
        chat.setId(0L);
        chat.setFirstName("John");
        message.setChat(chat);
        update.setMessage(message);

        return update;
    }

    /**
     * Тест, проверяющий выполнение команды /start
     */
    @Test
    public void testStart() {
        FakeBot fakeBot = new FakeBot();
        Logic logic = new Logic(fakeBot);

        logic.onUpdateReceived(createUpdate(COMMAND_START));

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
        Logic logic = new Logic(fakeBot);

        logic.onUpdateReceived(createUpdate(COMMAND_HELP));

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
        Logic logic = new Logic(fakeBot);

        logic.onUpdateReceived(createUpdate(COMMAND_SHOP));

        Assert.assertEquals("""
                 В наличии комплектующие для автомобилей:
                 BMW, Renault, Lada""", fakeBot.getLastMessage());

        ReplyKeyboardMarkup keyboardMarkup = (ReplyKeyboardMarkup)fakeBot.getMessage().getReplyMarkup();
        List<KeyboardRow> keyboardRows = keyboardMarkup.getKeyboard();

        Assert.assertFalse(keyboardRows.isEmpty());

        String buttons = keyboardRows.get(0).stream().map(KeyboardButton::getText).collect(Collectors.joining(" "));
        Assert.assertEquals("BMW Renault Lada", buttons);
    }
}

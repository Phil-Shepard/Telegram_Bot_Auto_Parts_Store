package testTask1;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Класс тестов для проверки работы команд для бота
 */
public class TestCommands extends TestCase {

    /**
     * Метод создает мок объекта Update и заполняет список messages сообщениями от бота.
     * @param command команда, вводимая пользователем
     * @param chatId чат-айди
     * @param firstName имя пользователя
     * @param messages список сообщений
     * @param botLogic бот, выполняющий команду
     * @return мок объекта Update
     */
    private Update createUpdate(String command, long chatId, String firstName, List<String> messages, Logic botLogic) {
        Update update = mock(Update.class);
        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage().hasText()).thenReturn(true);
        when(update.getMessage().getText()).thenReturn(command);
        when(update.getMessage().getChatId()).thenReturn(chatId);
        when(update.getMessage().getChat().getFirstName()).thenReturn(firstName);
        doAnswer(invocation -> {
            SendMessage message = invocation.getArgument(0);
            messages.add(message.getText());
            return null;
        }).when(botLogic).execute(any(SendMessage.class));
        return update;
    }

    /**
     * Тест, проверяющий выполнение команды /start
     */
    @Test
    public void testStart(){
        List<String> messages = new ArrayList<>();

        Logic botLogic = new Logic(new BotConfig());

        Update startUpdate = createUpdate("/start", 123L, "John", messages, botLogic);
        botLogic.onUpdateReceived(startUpdate);

        Assert.assertEquals("Привет, " + "John. " +  "Это телеграмм бот магазина автозапчастей." +
                " Доступны следующие команды:\n" +
                "/shop – Перейти в каталог запчастей.\n" +
                "/add - добавить в корзину выбранную запчасть.\n" +
                "/basket - вывести содержимое корзины.\n" +
                "/order - оформить заказ.\n" +
                "/history - вывести историю заказов.\n" +
                "/delete - удалить из корзины выбранные комплектующие.\n" +
                "/exit - выйти из каталога запчастей." +
                "/help - Справка.\n", messages.get(0));

        Assert.assertEquals(1, messages.size());
    }

    /**
     * Тест проверяющий выполнение команды /help
     */
    @Test
    public void testHelp(){
        List<String> messages = new ArrayList<>();

        Logic botLogic = new Logic(new BotConfig());

        Update startUpdate = createUpdate("/start", 123L, "John", messages, botLogic);
        botLogic.onUpdateReceived(startUpdate);

        Update helpUpdate = createUpdate("/help", 123L, "John", messages, botLogic);
        botLogic.onUpdateReceived(helpUpdate);

        Assert.assertEquals("Справка о дуступных командах:\n" +
                "/shop\n" +
                "/add\n" +
                "/basket\n" +
                "/order\n" +
                "/history\n" +
                "/delete\n" +
                "/exit\n" +
                "/help", messages.get(1));

        Assert.assertEquals(2, messages.size());
    }

    /**
     * Тест, проверяющий выполнение команды /shop
     */
    @Test
    public void testShop(){
        List<String> messages = new ArrayList<>();

        Logic botLogic = new Logic(new BotConfig());

        Update startUpdate = createUpdate("/start", 123L, "John", messages, botLogic);
        botLogic.onUpdateReceived(startUpdate);

        Update helpUpdate = createUpdate("/shop", 123L, "John", messages, botLogic);
        botLogic.onUpdateReceived(helpUpdate);

        Assert.assertEquals("В наличии комплектующиие для автомобилей: BMW, Reanault, Lada.", messages.get(1));

        Assert.assertEquals(2, messages.size());
    }
}

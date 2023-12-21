package com.urfu.bot;

import com.urfu.domain.basket.Basket;
import com.urfu.domain.history.History;
import com.urfu.domain.message.MessageFromUser;
import com.urfu.domain.message.MessageToUser;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

public class FakeBot implements Bot {
    private final Basket basket = new Basket();
    private final History history = new History();
    private final List<String> messages = new ArrayList<>();
    private MessageToUser message = new MessageToUser();
    private final BotLogic botLogic = new BotLogic(this);

    public String getLastMessage() {
        return messages.get(messages.size() - 1);
    }

    public List<String> getMessages() {
        return messages;
    }

    public MessageToUser getMessage() {
        return message;
    }

    public Basket getBasket() {
        return basket;
    }

    public History getHistory() {
        return history;
    }

    public void onUpdateReceived(String command) {
        MessageFromUser message = new MessageFromUser(0L, command, "John");
        botLogic.onUpdateReceived(message, basket, history);
    }

    @Override
    public void sendMessage(MessageToUser message) {
        messages.add(message.getText());
        this.message = message;
    }
}

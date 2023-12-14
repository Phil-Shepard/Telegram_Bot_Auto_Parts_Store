package com.urfu.bot.telegram;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.ArrayList;
import java.util.List;

public class FakeBot implements Bot{
    private final List<String> messages = new ArrayList<>();

    public String getLastMessage() {
        return messages.get(messages.size() - 1);
    }

    public List<String> getMessages() { return messages; }

    @Override
    public void sendMessage(SendMessage message) {
        messages.add(message.getText());
    }
}
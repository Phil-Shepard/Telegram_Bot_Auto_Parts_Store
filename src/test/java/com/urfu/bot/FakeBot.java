package com.urfu.bot;

import com.urfu.domain.message.MessageToUser;

import java.util.ArrayList;
import java.util.List;

public class FakeBot implements Bot {
    private final List<String> messages = new ArrayList<>();
    private MessageToUser message = new MessageToUser();

    public String getLastMessage() {
        return messages.get(messages.size() - 1);
    }

    public List<String> getMessages() { return messages; }

    public MessageToUser getMessage() { return message; }

    @Override
    public void sendMessage(MessageToUser message) {
        messages.add(message.getText());
        this.message = message;
    }
}

package com.urfu.bot.telegram;

import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static com.urfu.bot.telegram.Constants.listOfCommands;

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

package com.urfu.domain.history;

import com.urfu.domain.message.MessageToUser;

import java.util.ArrayList;

public class History {
    private final ArrayList<String> historyList = new ArrayList<String>();

    public ArrayList<String> getHistoryList() {
        return historyList;
    }

    public MessageToUser getHistory(long chatId) {
        String answer;
        if (historyList.isEmpty()) {
            answer = "История заказов пуста";
            return new MessageToUser(chatId, answer);
        } else {
            answer = "История заказов:\n";
            for (String order : historyList) {
                answer += order + "\n"; // Добавление символа новой строки после каждого заказа
            }
        }
        answer = answer.replaceAll("\\n{3,}", "\n\n");
        return new MessageToUser(chatId, answer);
    }

}

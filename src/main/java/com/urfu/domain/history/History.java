package com.urfu.domain.history;

import com.urfu.domain.message.MessageToUser;
import java.util.ArrayList;

/**
 * Класс истории заказов
 */
public class History {
    private final ArrayList<String> historyList = new ArrayList<String>();

    /**
     * возвращает историю заказов в виде листа строк
     */
    public ArrayList<String> getHistoryList() {
        return historyList;
    }

    /**
     * Возвращает историю заказов в виде класса сообщения
     */
    public MessageToUser getHistory(long chatId) {
        String answer;
        if (historyList.isEmpty()) {
            answer = "История заказов пуста";
            return new MessageToUser(chatId, answer);
        } else {
            answer = "История заказов:\n";
            for (String order : historyList) {
                answer += order + "\n";
            }
        }
        answer = answer.replaceAll("\\n{3,}", "\n\n");
        return new MessageToUser(chatId, answer);
    }

}

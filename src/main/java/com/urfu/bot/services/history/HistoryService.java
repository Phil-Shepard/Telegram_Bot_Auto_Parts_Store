package com.urfu.bot.services.history;

import java.util.HashMap;
import java.util.List;

/**
 * Описывает отчёт о покупках
 */
public interface HistoryService {
    List<HashMap<String, String>> getHistoryList();

}

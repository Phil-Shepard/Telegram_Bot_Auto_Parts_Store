package com.urfu.bot.services.history;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistoryServiceImpl implements HistoryService {
    public ArrayList<HashMap<String,String>> historyList = new ArrayList<HashMap<String,String>>();

    @Override
    public List<HashMap<String, String>> getHistoryList() {
        return historyList;
    }
}

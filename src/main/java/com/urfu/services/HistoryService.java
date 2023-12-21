package com.urfu.services;

import java.util.ArrayList;
import java.util.HashMap;
import com.urfu.domain.sparePart.SparePart;

public class HistoryService {
    public ArrayList<HashMap<String, ArrayList<SparePart>>> historyList = new ArrayList<HashMap<String,ArrayList<SparePart>>>();


    public ArrayList<HashMap<String,ArrayList<SparePart>>> getHistoryList() {
        return historyList;
    }
}

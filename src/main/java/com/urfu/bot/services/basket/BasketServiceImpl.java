package com.urfu.bot.services.basket;

import com.urfu.bot.domain.spareParts.SpareParts;


import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * Описывает корзину, в которой находятся выбранные запчасти
 */
public class BasketServiceImpl implements BasketService{

    public HashMap<String, String> contentsBasket = new HashMap<>();


    @Override
    public HashMap<String, String> getContentsBasket() {
        return contentsBasket;
    }
}

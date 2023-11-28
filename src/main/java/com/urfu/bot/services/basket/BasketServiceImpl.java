package com.urfu.bot.services.basket;

import com.urfu.bot.domain.spareParts.SpareParts;

public class BasketServiceImpl implements BasketService{
    private String contentsBasket;

    @Override
    public String ContentsBasket() {
        return contentsBasket;
    }
}

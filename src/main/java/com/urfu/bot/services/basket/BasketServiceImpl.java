package com.urfu.bot.services.basket;

import com.urfu.bot.domain.spareParts.SpareParts;
/**
 * Описывает корзину, в которой находятся выбранные запчасти
 */
public class BasketServiceImpl implements BasketService{
    public String contentsBasket;


    @Override
    public String getContentsBasket() {
        return contentsBasket;
    }
}

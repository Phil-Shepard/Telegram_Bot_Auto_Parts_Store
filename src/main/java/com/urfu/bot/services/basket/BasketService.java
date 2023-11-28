package com.urfu.bot.services.basket;

import com.urfu.bot.domain.spareParts.SpareParts;

import java.util.ArrayList;

/**
 * Описывает корзину, в которой находятся выбранные запчасти
 */
public interface BasketService {
    /**
     * Возвращает содержимое корзины в виде строки
     * @return
     */
    public String ContentsBasket();
}

package com.urfu.bot.services.basket;

import com.urfu.bot.domain.spareParts.SpareParts;

import java.util.ArrayList;
import java.util.Map;

/**
 * Описывает корзину, в которой находятся выбранные запчасти
 */
public interface BasketService {
    /**
     * Возвращает содержимое корзины в виде словаря
     * @return
     */
    public Map getContentsBasket();
}

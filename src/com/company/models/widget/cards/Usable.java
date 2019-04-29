package com.company.models.widget.cards;

import com.company.models.widget.cards.Card;

public abstract class Usable extends Card
{
    private int price;

    public Usable(String name, int cash, int manaCost) {
        super(name, cash, manaCost);
    }

    public abstract void affectUsable();
}

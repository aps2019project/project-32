package com.company.models.widget.cards;

import com.company.models.widget.cards.Card;

public class Usable extends Card
{
    private int price;

    @Override
    public String toShow()
    {
        return String.format("(Passive) Name : %s - SellCost : %d\n", this.getName(), this.getCash() / 2);
    }

    public void affectUsable(){

    }
}

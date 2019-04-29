package com.company.models.widget.cards;

public class Usable extends Card
{
    @Override
    public String toShow()
    {
        return String.format("(Passive) Name : %s - SellCost : %d\n", this.getName(), this.getPrice() / 2);
    }

    public void affectUsable(){

    }
}

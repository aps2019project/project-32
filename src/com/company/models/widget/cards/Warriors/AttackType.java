package com.company.models.widget.cards.Warriors;

public enum AttackType
{
    Melee,Ranged,Hybrid;


    @Override
    public String toString()
    {
        return this.name();
    }
}

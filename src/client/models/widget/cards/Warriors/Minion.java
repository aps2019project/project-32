package client.models.widget.cards.Warriors;


import client.controller.AnimationGIFSPack;
import client.models.widget.cards.Card;
import client.models.widget.cards.spells.Spell;

import java.io.Serializable;

public class Minion extends Warrior implements Serializable {
    private int manaCost;

    public Minion(String name, int manaCost, int power, int health, int price, AttackType attackType, int attackRange, Spell spell, AnimationGIFSPack gifsPack) {
        super(name, price, health, power, attackType, attackRange, spell, gifsPack);
        this.manaCost = manaCost;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Minion minion = null;
        if (this.getSpecialSpell() != null)
            minion = new Minion(name, manaCost, power, health, price, attackType, attackRadius, ((Spell) specialSpell.clone()), gifsPack);
        else
            minion = new Minion(name, manaCost, power, health, price, attackType, attackRadius, null, gifsPack);

        minion.ownerPlayer = this.ownerPlayer;

        return minion;
    }

    @Override
    public String getImageAddress() {
        return "file:Css/pictures/New folder (3)/card_backgrounds/craftable_prismatic_unit.png";
    }

    @Override
    public String getCharacterImage() {
        return "file:Css/pictures/New folder (3)/generals/general_portrait_image_hex_f6-third.png";
    }

    public void attack(Card defender) {

    }


    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }
}

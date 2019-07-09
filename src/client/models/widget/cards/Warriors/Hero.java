package client.models.widget.cards.Warriors;

import client.controller.AnimationGIFSPack;
import client.models.widget.cards.spells.Spell;

import java.io.Serializable;

public class Hero extends Warrior implements Serializable {

    public Hero() {

    }

    public Hero(String name, int power, int health, int price, AttackType attackType, int attackRadius, Spell spell, AnimationGIFSPack gifsPack) {
        super(name, price, health, power, attackType, attackRadius, spell, gifsPack);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Hero hero = null;

        if (this.getSpecialSpell() != null)
            hero = new Hero(name, power, health, price, attackType, attackRadius, ((Spell) specialSpell.clone()), gifsPack);
        else
            hero = new Hero(name, power, health, price, attackType, attackRadius, null, gifsPack);

        hero.ownerPlayer = this.ownerPlayer;

        return hero;
    }

    @Override
    public String getImageAddress() {
        return "file:Css/pictures/New folder (3)/card_backgrounds/neutral_prismatic_unit@2x.png";
    }

    @Override
    public String getCharacterImage() {
        return "file:Css/pictures/New folder (3)/generals/general_portrait_image_hex_f5-third@2x.png";
    }
}

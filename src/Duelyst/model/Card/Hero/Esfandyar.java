package Duelyst.model.Card.Hero;

import Duelyst.model.Player;

import Duelyst.model.Battle.Battle;
import Duelyst.model.Card.Minion.SpecialPower;
import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class Esfandyar extends Hero {
    public Esfandyar() {
        super("Esfandyar", 3, 35, 12000, 2);
        super.setCoolDownTime(0);
        super.setMp(0);
        super.cardImage = new Image("Duelyst/css/unit_gifs/boss_wraith_breathing.gif");
        super.setRange(3);
    }

    public Esfandyar(Esfandyar esfandyar) {
        super(esfandyar);
    }

    public Hero duplicate() {
        Esfandyar esfandyar = new Esfandyar(this);
        esfandyar.cardImage = new Image("Duelyst/css/unit_gifs/boss_wraith_breathing.gif");

        return esfandyar;
    }

    @Override
    public String showDetails() {
        String details = "Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : " + this.getHp()
                + " - Class : " + this.getTypeOfHit() + " – Special power : " + SpecialPower.ESFANDYAR.getMessage();
        return details;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player) {

    }

    public String getDesc() {
        return SpecialPower.ESFANDYAR.getMessage();
    }
}

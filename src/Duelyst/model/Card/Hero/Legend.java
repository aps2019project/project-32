package Duelyst.model.Card.Hero;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Card.Minion.SpecialPower;
import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class Legend extends Hero {
    public Legend() {
        super("Legend", 3, 40, 11000, 1);
        super.setCoolDownTime(2);
        super.setMp(1);
        super.setRange(3);
        super.cardImage = new Image("Duelyst/css/unit_gifs/f1_altgeneraltier2_breathing.gif");
    }

    public Legend(Legend legend) {
        super(legend);
    }

    public Hero duplicate() {
        Legend legend = new Legend(this);
        legend.cardImage = new Image("Duelyst/css/unit_gifs/f1_altgeneraltier2_breathing.gif");
        return legend;
    }

    @Override
    public String showDetails() {
        String details = "Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : " + this.getHp()
                + " - Class : " + this.getTypeOfHit() + " – Special power : " + SpecialPower.LEGEND.getMessage();
        return details;
    }

    public void castSpecialPower(Battle battle, Cell cell, Player player) {


    }

    public String getDesc() {
        return SpecialPower.LEGEND.getMessage();
    }
}

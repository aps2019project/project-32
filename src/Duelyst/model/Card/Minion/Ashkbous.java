package Duelyst.model.Card.Minion;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class Ashkbous extends Minion {
    public Ashkbous() {
        super("Ashkbous", 8, 14, 400, 7, 0, 0);
        super.setTimeOfActivationOfSpecialPower(6);
        super.cardImage = new Image("Duelyst/css/unit_gifs/boss_antiswarm_breathing.gif");
    }

    public Ashkbous(Ashkbous ashkbous) {
        super(ashkbous);
    }

    public Minion duplicate() {
        Ashkbous ashkbous = new Ashkbous(this);
        ashkbous.cardImage = new Image("Duelyst/css/unit_gifs/boss_antiswarm_breathing.gif");
        return ashkbous;
    }

    @Override
    public String showDetails() {
        String detail;
        detail = "Type : " + this.getType() + " - Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : " + this.getHp()
                + " - MP : " + this.getCostToUse() + " - Class : " + this.getTypeOfHit() + " - Special power : " + SpecialPower.ASHKBOUS.getMessage();
        return detail;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player, int activeTime) {

    }

    public String getDesc() {
        return SpecialPower.ASHKBOUS.getMessage();
    }
}

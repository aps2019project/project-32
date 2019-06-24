package Duelyst.model.Card.Minion;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class OneEyeGiant extends Minion {
    public OneEyeGiant() {
        super("OneEyeGiant", 11, 12, 500, 7, 2, 3);
        super.setTimeOfActivationOfSpecialPower(3);
        super.cardImage = new Image("Duelyst/css/unit_gifs/boss_kane_breathing.gif");
    }

    public OneEyeGiant(OneEyeGiant oneEyeGiant) {
        super(oneEyeGiant);
    }

    public Minion duplicate() {
        OneEyeGiant oneEyeGiant = new OneEyeGiant(this);
        oneEyeGiant.cardImage = new Image("Duelyst/css/unit_gifs/boss_kane_breathing.gif");
        return oneEyeGiant;
    }

    @Override
    public String showDetails() {
        String detail;
        detail = "Type : " + this.getType() + " - Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : " + this.getHp()
                + " - MP : " + this.getCostToUse() + " - Class : " + this.getTypeOfHit() + " - Special power: " + SpecialPower.ONE_EYE_GIANT.getMessage();
        return detail;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player, int activeTime) {
    }

    public String getDesc() {
        return SpecialPower.ONE_EYE_GIANT.getMessage();
    }
}

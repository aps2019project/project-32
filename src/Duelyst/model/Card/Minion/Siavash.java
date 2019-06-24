package Duelyst.model.Card.Minion;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class Siavash extends Minion {
    public Siavash() {
        super("Siavash", 5, 8, 350, 4, 0, 0);
        super.setTimeOfActivationOfSpecialPower(3);
        super.cardImage = new Image("Duelyst/css/unit_gifs/boss_serpenti_breathing.gif");
    }

    public Siavash(Siavash siavash) {
        super(siavash);
    }

    public Minion duplicate() {
        Siavash siavash = new Siavash(this);
        siavash.cardImage = new Image("Duelyst/css/unit_gifs/boss_serpenti_breathing.gif");
        return siavash;
    }

    @Override
    public String showDetails() {
        String detail;
        detail = "Type : " + this.getType() + " - Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : " + this.getHp()
                + " - MP : " + this.getCostToUse() + " - Class : " + this.getTypeOfHit() + " - Special power : " + SpecialPower.SIAVASH.getMessage();
        return detail;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player, int activeTime) {

    }

    public String getDesc() {
        return SpecialPower.SIAVASH.getMessage();
    }
}

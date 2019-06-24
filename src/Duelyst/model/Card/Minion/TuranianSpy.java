package Duelyst.model.Card.Minion;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class TuranianSpy extends Minion {
    public TuranianSpy() {
        super("TuranianSpy", 6, 6, 700, 4, 0, 0);
        super.setTimeOfActivationOfSpecialPower(0);
        super.cardImage = new Image("Duelyst/css/unit_gifs/boss_solfist_breathing.gif");
    }

    public TuranianSpy(TuranianSpy turanianSpy) {
        super(turanianSpy);
    }

    public Minion duplicate() {
        TuranianSpy turanianSpy = new TuranianSpy(this);
        turanianSpy.cardImage = new Image("Duelyst/css/unit_gifs/boss_solfist_breathing.gif");
        return turanianSpy;
    }

    @Override
    public String showDetails() {
        String detail;
        detail = "Type : " + this.getType() + " - Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : " + this.getHp()
                + " - MP : " + this.getCostToUse() + " - Class : " + this.getTypeOfHit() + " - Special power : " + SpecialPower.TURANIAN_SPY.getMessage();
        return detail;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player, int activeTime) {

    }

    public String getDesc() {
        return SpecialPower.TURANIAN_SPY.getMessage();
    }
}

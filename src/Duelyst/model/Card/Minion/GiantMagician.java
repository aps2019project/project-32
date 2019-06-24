package Duelyst.model.Card.Minion;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;

import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class GiantMagician extends Minion {
    public GiantMagician() {
        super("GiantMagician", 6, 6, 550, 6, 1, 5);
        super.setTimeOfActivationOfSpecialPower(4);
        super.cardImage = new Image("Duelyst/css/unit_gifs/boss_decepticlesword_breathing.gif");
    }

    public GiantMagician(GiantMagician giantMagician) {
        super(giantMagician);
    }

    public Minion duplicate() {
        GiantMagician giantMagician = new GiantMagician(this);
        giantMagician.cardImage = new Image("Duelyst/css/unit_gifs/boss_decepticlesword_breathing.gif");
        return giantMagician;
    }

    @Override
    public String showDetails() {
        String detail;
        detail = "Type : " + this.getType() + " - Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : " + this.getHp()
                + " - MP : " + this.getCostToUse() + " - Class : " + this.getTypeOfHit() + " - Special power : " + SpecialPower.GIANT_MAGICIAN.getMessage();
        return detail;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player, int activeTime) {

    }

    public String getDesc() {
        return SpecialPower.GIANT_MAGICIAN.getMessage();
    }
}

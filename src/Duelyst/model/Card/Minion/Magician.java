package Duelyst.model.Card.Minion;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class Magician extends Minion {
    public Magician() {
        super("Magician", 4, 5, 550, 4, 1, 3);
        super.cardImage = new Image("Duelyst/css/unit_gifs/boss_invader_breathing.gif");
        super.setTimeOfActivationOfSpecialPower(4);
    }

    public Magician(Magician magician) {
        super(magician);
    }

    public Minion duplicate() {
        Magician magician = new Magician(this);
        magician.cardImage = new Image("Duelyst/css/unit_gifs/boss_invader_breathing.gif");
        return magician;
    }

    @Override
    public String showDetails() {
        String detail;
        detail = "Type : " + this.getType() + " - Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : "
                + this.getHp() + " - MP : " + this.getCostToUse() + " - Class : " + this.getTypeOfHit() +
                " - Special power : " + SpecialPower.MAGICIAN.getMessage();
        return detail;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player, int activeTime) {

    }

    public String getDesc() {
        return SpecialPower.MAGICIAN.getMessage();
    }
}

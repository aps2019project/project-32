package Duelyst.model.Card.Minion;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class Bahman extends Minion {
    public Bahman() {
        super("Bahman", 9, 16, 450, 8, 0, 0);
        super.setTimeOfActivationOfSpecialPower(1);
        super.cardImage = new Image("Duelyst/css/unit_gifs/boss_borealjuggernaut_breathing.gif");

    }

    public Bahman(Bahman bahman) {
        super(bahman);
    }

    public Minion duplicate() {
        Bahman bahman = new Bahman(this);
        bahman.cardImage = new Image("Duelyst/css/unit_gifs/boss_borealjuggernaut_breathing.gif");
        return bahman;
    }

    @Override
    public String showDetails() {
        String detail;
        detail = "Type : " + this.getType() + " - Name : " + super.getName() + "Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : " + this.getHp()
                + " - MP : " + this.getCostToUse() + " - Class : " + this.getTypeOfHit() + " - Special power : " + SpecialPower.BAHMAN.getMessage();
        return detail;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player, int activeTime) {
    }

    public String getDesc() {
        return SpecialPower.BAHMAN.getMessage();
    }
}

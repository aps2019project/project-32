package Duelyst.model.Card.Minion;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class TuranianArcher extends Minion {
    public TuranianArcher() {
        super("TuranianArcher", 4, 3, 500, 1, 1, 5);
        super.cardImage = new Image("Duelyst/css/unit_gifs/boss_shinkagezendo_breathing.gif");
    }

    public TuranianArcher(TuranianArcher turanianArcher) {
        super(turanianArcher);
    }

    public Minion duplicate() {
        TuranianArcher turanianArcher = new TuranianArcher(this);
        turanianArcher.cardImage = new Image("Duelyst/css/unit_gifs/boss_shinkagezendo_breathing.gif");
        return turanianArcher;
    }

    @Override
    public String showDetails() {
        String detail;
        detail = "Type : " + this.getType() + " - Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : " + this.getHp()
                + " - MP : " + this.getCostToUse() + " - Class : " + this.getTypeOfHit() + " - Special power : -";
        return detail;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player, int activeTime) {

    }

    public String getDesc() {
        return " Nothing";
    }
}

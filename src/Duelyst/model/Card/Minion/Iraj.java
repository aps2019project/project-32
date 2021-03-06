package Duelyst.model.Card.Minion;
import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class Iraj extends Minion {
    public Iraj() {
        super("Iraj", 20, 6, 500, 4, 1, 3);
        super.cardImage = new Image("Duelyst/css/unit_gifs/boss_grym_breathing.gif");
    }

    public Iraj(Iraj iraj) {
        super(iraj);
    }

    public Minion duplicate() {
        Iraj iraj = new Iraj(this);
        iraj.cardImage = new Image("Duelyst/css/unit_gifs/boss_grym_breathing.gif");
        return iraj;
    }

    @Override
    public String showDetails() {
        String detail;
        detail = "Type : " + this.getType() + " - Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : " + this.getHp()
                + " - MP : " + this.getCostToUse() + " - Class : " + this.getTypeOfHit() + " - Special power :  .";
        return detail;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player, int activeTime) {

    }

    public String getDesc() {
        return "Nothing";
    }
}

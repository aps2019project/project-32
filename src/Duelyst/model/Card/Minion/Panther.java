package Duelyst.model.Card.Minion;
import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class Panther extends Minion {
    public Panther() {
        super("Panther", 2, 6, 400, 4, 0, 0);
        super.setTimeOfActivationOfSpecialPower(0);
        super.cardImage = new Image("Duelyst/css/unit_gifs/boss_kron_breathing.gif");
    }

    public Panther(Panther panther) {
        super(panther);
    }

    public Minion duplicate() {
        Panther panther = new Panther(this);
        panther.cardImage = new Image("Duelyst/css/unit_gifs/boss_kron_breathing.gif");
        return panther;
    }

    @Override
    public String showDetails() {
        String detail;
        detail = "Type : " + this.getType() + " - Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : "
                + this.getHp() + " - MP : " + this.getCostToUse() + " - Class : " + this.getTypeOfHit() +
                " - Special power: " + SpecialPower.PANTHER.getMessage();
        return detail;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player, int activeTime) {

  }

    public String getDesc() {
        return SpecialPower.PANTHER.getMessage();
    }
}

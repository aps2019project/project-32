package Duelyst.model.Card.Minion;
import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class TwoHeadGiant extends Minion {
    public TwoHeadGiant() {
        super("TwoHeadGiant", 4, 10, 550, 4, 0, 0);
        super.setTimeOfActivationOfSpecialPower(4);
        super.cardImage = new Image("Duelyst/css/unit_gifs/boss_taskmaster_breathing.gif");
    }

    public TwoHeadGiant(TwoHeadGiant twoHeadGiant) {
        super(twoHeadGiant);
    }

    public Minion duplicate() {
        TwoHeadGiant twoHeadGiant = new TwoHeadGiant(this);
        twoHeadGiant.cardImage = new Image("Duelyst/css/unit_gifs/boss_taskmaster_breathing.gif");
        return twoHeadGiant;
    }

    @Override
    public String showDetails() {
        String detail;
        detail = "Type : " + this.getType() + " - Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : " + this.getHp()
                + " - MP : " + this.getCostToUse() + " - Class : " + this.getTypeOfHit() + " - Special power : " + SpecialPower.TWO_HEAD_GIANT.getMessage();
        return detail;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player, int activeTime) {

    }

    public String getDesc() {
        return SpecialPower.TWO_HEAD_GIANT.getMessage();
    }
}

package Duelyst.model.Card.Minion;
import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class FieryDragon extends Minion {
    public FieryDragon() {
        super("FieryDragon", 5, 9, 250, 5, 1, 4);
        super.cardImage = new Image("Duelyst/css/unit_gifs/boss_decepticlechassis_breathing.gif");
    }

    public FieryDragon(FieryDragon fieryDragon) {
        super(fieryDragon);
    }

    public Minion duplicate() {
        FieryDragon fieryDragon = new FieryDragon(this);
        fieryDragon.cardImage = new Image("Duelyst/css/unit_gifs/boss_decepticlechassis_breathing.gif");
        return fieryDragon;
    }

    @Override
    public String showDetails() {
        String detail;
        detail = "Type : " + this.getType() + " - Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : " + this.getHp()
                + " - MP : " + this.getCostToUse() + " - Class : " + this.getTypeOfHit() + " - Special power:";
        return detail;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player, int activeTime) {

    }

    public String getDesc() {
        return "Nothing";
    }
}

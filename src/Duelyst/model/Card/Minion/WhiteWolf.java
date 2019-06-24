package Duelyst.model.Card.Minion;
import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class WhiteWolf extends Minion {
    public WhiteWolf() {
        super("WhiteWolf", 2, 8, 400, 5, 0, 0);
        super.setTimeOfActivationOfSpecialPower(0);
        super.cardImage = new Image("Duelyst/css/unit_gifs/boss_umbra_breathing.gif");
    }

    public WhiteWolf(WhiteWolf whiteWolf) {
        super(whiteWolf);
    }

    public Minion duplicate() {
        WhiteWolf whiteWolf = new WhiteWolf(this);
        whiteWolf.cardImage = new Image("Duelyst/css/unit_gifs/boss_umbra_breathing.gif");
        return whiteWolf;
    }

    @Override
    public String showDetails() {
        String detail;
        detail = "Type : " + this.getType() + " - Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : " +
                this.getHp() + " - MP : " + this.getCostToUse() + " - Class : " + this.getTypeOfHit()
                + " - Special power: " + SpecialPower.WHITE_WOLF.getMessage();
        return detail;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player, int activeTime) {

    }

    public String getDesc() {
        return SpecialPower.WHITE_WOLF.getMessage();
    }
}

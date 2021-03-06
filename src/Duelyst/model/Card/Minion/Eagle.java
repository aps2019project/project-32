package Duelyst.model.Card.Minion;
import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class Eagle extends Minion {
    public Eagle() {
        super("Eagle", 2, 0, 200, 2, 1, 3);
        super.setTimeOfActivationOfSpecialPower(4);
        super.cardImage = new Image("Duelyst/css/unit_gifs/boss_crystal_breathing.gif");
    }

    public Eagle(Eagle eagle) {
        super(eagle);
    }

    public Minion duplicate() {
        Eagle eagle = new Eagle(this);
        eagle.cardImage = new Image("Duelyst/css/unit_gifs/boss_crystal_breathing.gif");

        return eagle;
    }

    @Override
    public String showDetails() {
        String detail;
        detail = "Type : " + this.getType() + " - Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : " + this.getHp()
                + " - MP : " + this.getCostToUse() + " - Class : " + this.getTypeOfHit() + " - Special power: " + SpecialPower.EAGLE.getMessage();
        return detail;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player, int activeTime) {

    }

    public String getDesc() {
        return SpecialPower.EAGLE.getMessage();
    }
}

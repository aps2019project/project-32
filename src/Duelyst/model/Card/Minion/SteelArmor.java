package Duelyst.model.Card.Minion;
import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;

import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class SteelArmor extends Minion {
    public SteelArmor() {
        super("SteelArmor", 1, 1, 650, 3, 0, 0);
        super.setTimeOfActivationOfSpecialPower(1);
        super.cardImage = new Image("Duelyst/css/unit_gifs/boss_shadowlord_breathing.gif");
    }

    public SteelArmor(SteelArmor steelArmor) {
        super(steelArmor);
    }

    public Minion duplicate() {
        SteelArmor steelArmor = new SteelArmor(this);
        steelArmor.cardImage = new Image("Duelyst/css/unit_gifs/boss_shadowlord_breathing.gif");

        return steelArmor;
    }

    @Override
    public String showDetails() {
        String detail;
        detail = "Type : " + this.getType() + " - Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : " + this.getHp()
                + " - MP : " + this.getCostToUse() + " - Class : " + this.getTypeOfHit() + " - Special power : " + SpecialPower.STEEL_ARMOR.getMessage();
        return detail;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player, int activeTime) {

    }

    public String getDesc() {
        return SpecialPower.STEEL_ARMOR.getMessage();
    }
}

package Duelyst.model.Item.UsableItem;

import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import Duelyst.model.Player;

public class ShockHammer extends UsableItem {

    public ShockHammer() {
        super(15000, "ShockHammer");
    }

    public ShockHammer(ShockHammer shockHammer) {
        super(shockHammer);
    }


    @Override
    public void applyEffect(Battle battle, Cell cell, Player player, int activeTime) {

    }

    public UsableItem duplicate() {
        ShockHammer shockHammer = new ShockHammer(this);
        return shockHammer;
    }

    @Override
    public String showDetails() {
        String details;
        details = "Name : " + this.getClass().getSimpleName() +
                " - Desc: " + UsableItemWork.SHOCK_HAMMER.getMessage();
        return details;
    }
}

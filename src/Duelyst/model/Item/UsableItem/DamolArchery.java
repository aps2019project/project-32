package Duelyst.model.Item.UsableItem;

import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import Duelyst.model.Player;

public class DamolArchery extends UsableItem {

    public DamolArchery() {
        super(30000, "DamolArchery");
    }

    public DamolArchery(DamolArchery damolArchery) {
        super(damolArchery);
    }


    @Override
    public void applyEffect(Battle battle, Cell cell, Player player, int activeTime) {

    }


    @Override
    public UsableItem duplicate() {
        DamolArchery damolArchery = new DamolArchery(this);
        return damolArchery;
    }

    @Override
    public String showDetails() {
        String details;
        details = "Name : " + this.getClass().getSimpleName() +
                " - Desc: " + UsableItemWork.DAMOL_ARCHERY.getMessage();
        return details;
    }
}

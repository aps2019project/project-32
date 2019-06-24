package Duelyst.model.Item.UsableItem;

import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import Duelyst.model.Player;

public class ShameEmblem extends UsableItem {

    public ShameEmblem() {
        super(4000, "ShameEmblem");
    }

    public ShameEmblem(ShameEmblem shameEmblem) {
        super(shameEmblem);
    }


    @Override
    public void applyEffect(Battle battle, Cell cell, Player player, int activeTime) {

    }

    @Override
    public UsableItem duplicate() {
        ShameEmblem shameEmblem = new ShameEmblem(this);
        return shameEmblem;
    }

    @Override
    public String showDetails() {
        String details;
        details = "Name : " + this.getClass().getSimpleName() +
                " - Desc: " + UsableItemWork.SHAME_EMBLEM.getMessage();
        return details;
    }
}

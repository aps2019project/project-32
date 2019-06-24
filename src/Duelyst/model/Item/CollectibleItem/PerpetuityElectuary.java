package Duelyst.model.Item.CollectibleItem;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;

import Duelyst.model.Cell;

public class PerpetuityElectuary extends CollectibleItem {

    public PerpetuityElectuary() {
        super("PerpetuityElectuary");
    }

    public PerpetuityElectuary(PerpetuityElectuary perpetuityElectuary) {
        super(perpetuityElectuary);
    }


    @Override
    public void applyEffect(Battle battle, Cell cell, Player player) {

    }

    public CollectibleItem duplicate() {
        PerpetuityElectuary perpetuityElectuary = new PerpetuityElectuary(this);
        return perpetuityElectuary;
    }

    @Override
    public String showDetails() {
        String details;
        details = "Name : " + this.getClass().getSimpleName() +
                " - Desc: " + CollectibleItemWork.PERPETUITY_ELECTUARY.getMessage();
        return details;
    }
}

package Duelyst.model.Item.CollectibleItem;


import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;

public class ManaElectuary extends CollectibleItem {

    public ManaElectuary() {
        super("ManaElectuary");
    }

    public ManaElectuary(ManaElectuary manaElectuary) {
        super(manaElectuary);
    }


    @Override
    public void applyEffect(Battle battle, Cell cell, Player player) {

    }

    public CollectibleItem duplicate() {
        ManaElectuary manaElectuary = new ManaElectuary(this);
        return manaElectuary;
    }

    @Override
    public String showDetails() {
        String details;
        details = "Name : " + this.getClass().getSimpleName() +
                " - Desc: " + CollectibleItemWork.MANA_ELECTUARY.getMessage();
        return details;
    }
}

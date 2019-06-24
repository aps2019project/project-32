package Duelyst.model.Item.UsableItem;

import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import Duelyst.model.Player;

public class Baptism extends UsableItem {

    public Baptism() {
        super(20000, "Baptism");
    }

    public Baptism(Baptism baptism) {
        super(baptism);
    }


    @Override
    public void applyEffect(Battle battle, Cell cell, Player player, int activeTime) {

    }

    @Override
    public UsableItem duplicate() {
        Baptism baptism = new Baptism(this);
        return baptism;
    }

    @Override
    public String showDetails() {
        String details;
        details = "Name : " + this.getClass().getSimpleName() +
                " - Desc: " + UsableItemWork.BAPTISM.getMessage();
        return details;
    }
}

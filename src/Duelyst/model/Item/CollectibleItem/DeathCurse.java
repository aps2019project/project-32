package Duelyst.model.Item.CollectibleItem;


import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;

public class DeathCurse extends CollectibleItem {

    public DeathCurse() {
        super("DeathCurse");
    }

    public DeathCurse(DeathCurse deathCurse) {
        super(deathCurse);
    }


    @Override
    public void applyEffect(Battle battle, Cell cell, Player player) {
        Cell insiderCell = getRandomInsiderForce(battle, player);
        while (insiderCell.getMinion() == null) {
            insiderCell = getRandomInsiderForce(battle, player);
        }
        insiderCell.getMinion().setDeathCurse(true);
    }

    @Override
    public CollectibleItem duplicate() {
        DeathCurse deathCurse = new DeathCurse(this);
        return deathCurse;
    }

    @Override
    public String showDetails() {
        String details;
        details = "Name : " + this.getClass().getSimpleName() +
                " - Desc: " + CollectibleItemWork.DEATH_CURSE.getMessage();
        return details;
    }
}

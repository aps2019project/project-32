package Duelyst.model.Item.CollectibleItem;


import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;

public class RandomDamage extends CollectibleItem {

    public RandomDamage() {
        super("RandomDamage");
    }

    public RandomDamage(RandomDamage randomDamage) {
        super(randomDamage);
    }


    @Override
    public void applyEffect(Battle battle, Cell cell, Player player) {
        Cell insiderCell = getRandomInsiderForce(battle, player);
        if (insiderCell.getHero() != null) {
            insiderCell.getHero().incrementAp(2);
        }
        if (insiderCell.getMinion() != null)
            insiderCell.getMinion().incrementAp(2);
    }

    @Override
    public CollectibleItem duplicate() {
        RandomDamage randomDamage = new RandomDamage(this);
        return randomDamage;
    }

    @Override
    public String showDetails() {
        String details;
        details = "Name : " + this.getClass().getSimpleName() +
                " - Desc: " + CollectibleItemWork.RANDOM_DAMAGE.getMessage();
        return details;
    }
}

package Duelyst.model.Item.CollectibleItem;


import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;

public class Elexir extends CollectibleItem {

    public Elexir() {
        super("Elexir");
    }

    public Elexir(Elexir elexir) {
        super(elexir);
    }


    @Override
    public void applyEffect(Battle battle, Cell cell, Player player) {


    }

    public CollectibleItem duplicate() {
        Elexir elexir = new Elexir(this);
        return elexir;
    }

    @Override
    public String showDetails() {
        String details;
        details = "Name : " + this.getClass().getSimpleName() +
                " - Desc: " + CollectibleItemWork.ELIXIR.getMessage();
        return details;
    }
}

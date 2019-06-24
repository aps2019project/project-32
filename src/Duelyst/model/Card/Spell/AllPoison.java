package Duelyst.model.Card.Spell;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import Duelyst.model.ErrorType;
import javafx.scene.image.Image;

public class AllPoison extends Spell {

    public AllPoison() {
        super("AllPoison", 8, 1500);
        super.cardImage = new Image("Duelyst/css/unit_gifs/f3_altgeneraltier2_breathing.gif");
    }

    public AllPoison(String name, int costToUse, int costOfBuy) {
        super(name, costToUse, costOfBuy);
    }

    @Override
    public ErrorType castSpell(Battle battle, Cell cell, Player player) {

        return null;
    }

    public AllPoison(AllPoison allPoison) {
        super(allPoison);
    }

    public Spell duplicate() {
        AllPoison allPoison = new AllPoison(this);

        allPoison.cardImage = new Image("Duelyst/css/unit_gifs/f3_altgeneraltier2_breathing.gif");
        return allPoison;
    }


    @Override
    public String getDesc() {
        return SpellWork.ALL_POISON.getMessage();
    }

    @Override
    public String showDetails() {
        String details;
        details = " Type : " + getType() + " - Name : " +
                this.getClass().getSimpleName() + " - MP : " + this.getCostToUse() +
                " - Desc: " + SpellWork.ALL_POISON.getMessage();
        return details;
    }
}

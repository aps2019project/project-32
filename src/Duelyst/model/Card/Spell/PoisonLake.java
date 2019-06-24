package Duelyst.model.Card.Spell;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import Duelyst.model.ErrorType;
import javafx.scene.image.Image;

public class PoisonLake extends Spell {

    public PoisonLake() {
        super("PoisonLake", 5, 900);
        super.cardImage = new Image("Duelyst/css/unit_gifs/f6_ilenamk2_breathing.gif");
    }

    public PoisonLake(PoisonLake poisonLake) {
        super(poisonLake);
    }

    @Override
    public ErrorType castSpell(Battle battle, Cell cell, Player player) {

        return null;
    }

    public Spell duplicate() {
        PoisonLake poisonLake = new PoisonLake(this);
        poisonLake.cardImage = new Image("Duelyst/css/unit_gifs/f6_ilenamk2_breathing.gif");
        return poisonLake;
    }

    @Override
    public String showDetails() {
        String details;
        details = " Type : " + getType() + " - Name : " +
                this.getClass().getSimpleName() + " - MP : " + this.getCostToUse() +
                " - Desc: " + SpellWork.POISON_LAKE.getMessage();
        return details;
    }

    @Override
    public String getDesc() {
        return SpellWork.POISON_LAKE.getMessage();
    }
}

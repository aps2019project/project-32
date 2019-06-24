package Duelyst.model.Card.Spell;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import Duelyst.model.ErrorType;
import javafx.scene.image.Image;

public class Dispel extends Spell {

    public Dispel() {
        super("Dispel", 0, 2100);
        super.cardImage = new Image("Duelyst/css/unit_gifs/f3_zirixfestive_breathing.gif");
    }

    public Dispel(Dispel dispel) {
        super(dispel);
    }

    @Override
    public ErrorType castSpell(Battle battle, Cell cell, Player player) {

       return null;
    }

    public Spell duplicate() {
        Dispel dispel = new Dispel(this);
        dispel.cardImage = new Image("Duelyst/css/unit_gifs/f3_zirixfestive_breathing.gif");
        return dispel;
    }

    @Override
    public String getDesc() {
        return SpellWork.DISPEL.getMessage();
    }

    @Override
    public String showDetails() {
        String details;
        details = " Type : " + getType() + " - Name : " +
                this.getClass().getSimpleName() + " - MP : " + this.getCostToUse() +
                " - Desc: " + SpellWork.DISPEL.getMessage();
        return details;
    }
}

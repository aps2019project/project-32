package Duelyst.model.Card.Spell;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import Duelyst.model.ErrorType;
import javafx.scene.image.Image;

public class AreaDispel extends Spell {

    public AreaDispel() {
        super("AreaDispel", 2, 1500);
        super.cardImage = new Image("Duelyst/css/unit_gifs/f3_tier2general_breathing.gif");
    }

    public AreaDispel(AreaDispel areaDispel) {
        super(areaDispel);
    }

    public String getDesc() {
        return SpellWork.AREA_DISPEL.getMessage();
    }

    @Override
    public ErrorType castSpell(Battle battle, Cell cell, Player player) {

        return null;
    }

    @Override
    public Spell duplicate() {
        AreaDispel areaDispel = new AreaDispel(this);

        areaDispel.cardImage = new Image("Duelyst/css/unit_gifs/f3_tier2general_breathing.gif");
        return areaDispel;
    }

    @Override
    public String showDetails() {
        String details;
        details = " Type : " + getType() + " - Name : " +
                this.getClass().getSimpleName() + " - MP : " + this.getCostToUse() +
                " - Desc: " + SpellWork.AREA_DISPEL.getMessage();
        return details;
    }
}

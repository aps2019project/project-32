package Duelyst.model.Card.Spell;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import Duelyst.model.ErrorType;
import javafx.scene.image.Image;

public class GodStrength extends Spell {

    public GodStrength() {
        super("GodStrength", 2, 450);
        super.cardImage = new Image("Duelyst/css/unit_gifs/f4_tier2general_breathing.gif");
    }

    public GodStrength(GodStrength godStrength) {
        super(godStrength);
    }

    @Override
    public ErrorType castSpell(Battle battle, Cell cell, Player player) {


        return null;
    }

    public Spell duplicate() {
        GodStrength godStrength = new GodStrength(this);
        godStrength.cardImage = new Image("Duelyst/css/unit_gifs/f4_tier2general_breathing.gif");
        return godStrength;
    }


    @Override
    public String getDesc() {
        return SpellWork.GOD_STRENGTH.getMessage();
    }

    @Override
    public String showDetails() {
        String details;
        details = " Type : " + getType() + " - Name : " +
                this.getClass().getSimpleName() + " - MP : " + this.getCostToUse() +
                " - Desc: " + SpellWork.GOD_STRENGTH.getMessage();
        return details;
    }
}

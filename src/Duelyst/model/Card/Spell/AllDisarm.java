package Duelyst.model.Card.Spell;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import Duelyst.model.ErrorType;
import javafx.scene.image.Image;

public class AllDisarm extends Spell {

    public AllDisarm() {
        super("AllDisarm", 9, 2000);
        super.cardImage = new Image("Duelyst/css/unit_gifs/f2_tier2general_breathing.gif");
    }

    public AllDisarm(AllDisarm allDisarm) {
        super(allDisarm);
    }

    @Override
    public ErrorType castSpell(Battle battle, Cell cell, Player player) {

        return ErrorType.SUCCESSFUL_INSERT;
    }

    public Spell duplicate() {
        AllDisarm allDisarm = new AllDisarm(this);

        allDisarm.cardImage = new Image("Duelyst/css/unit_gifs/f2_tier2general_breathing.gif");
        return allDisarm;
    }


    @Override
    public String getDesc() {
        return SpellWork.ALL_DISARM.getMessage();
    }


    @Override
    public String showDetails() {
        String details;
        details = " Type : " + getType() + " - Name : " +
                this.getClass().getSimpleName() + " - MP : " + this.getCostToUse() +
                " - Desc: " + SpellWork.ALL_DISARM.getMessage();
        return details;
    }
}

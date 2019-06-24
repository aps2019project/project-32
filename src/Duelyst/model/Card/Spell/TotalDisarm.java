package Duelyst.model.Card.Spell;


import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import Duelyst.model.ErrorType;
import javafx.scene.image.Image;

public class TotalDisarm extends Spell {

    public TotalDisarm() {
        super("TotalDisarm", 0, 1000);
        super.cardImage = new Image("Duelyst/css/unit_gifs/neutral_zyxfestive_breathing.gif");
    }

    @Override
    public ErrorType castSpell(Battle battle, Cell cell, Player player) {

        return null;
    }

    public TotalDisarm(TotalDisarm totalDisarm) {
        super(totalDisarm);
    }

    public Spell duplicate() {
        TotalDisarm totalDisarm = new TotalDisarm(this);
        totalDisarm.cardImage = new Image("Duelyst/css/unit_gifs/neutral_zyxfestive_breathing.gif");
        return totalDisarm;
    }


    @Override
    public String getDesc() {
        return SpellWork.TOTAL_DISARM.getMessage();
    }


    @Override
    public String showDetails() {
        String details;
        details = " Type : " + getType() + " - Name : " +
                this.getClass().getSimpleName() + " - MP : " + this.getCostToUse() +
                " - Desc: " + SpellWork.TOTAL_DISARM.getMessage();
        return details;
    }
}
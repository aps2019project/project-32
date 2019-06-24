package Duelyst.model.Card.Spell;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import Duelyst.model.ErrorType;
import javafx.scene.image.Image;

public class AllPower extends Spell {

    public AllPower() {
        super("AllPower", 4, 2000);
        super.cardImage = new Image("Duelyst/css/unit_gifs/f3_ciphyronmk2_breathing.gif");
    }

    public AllPower(AllPower allPower) {
        super(allPower);
    }

    @Override
    public ErrorType castSpell(Battle battle, Cell cell, Player player) {


        return null;
    }

    public Spell duplicate() {
        AllPower allPower = new AllPower(this);
        allPower.cardImage = new Image("Duelyst/css/unit_gifs/f3_ciphyronmk2_breathing.gif");
        return allPower;
    }

    @Override
    public String getDesc() {
        return SpellWork.ALL_POWER.getMessage();
    }

    @Override
    public String showDetails() {
        String details;
        details = " Type : " + getType() + " - Name : " +
                this.getClass().getSimpleName() + " - MP : " + this.getCostToUse() +
                " - Desc: " + SpellWork.ALL_POWER.getMessage();
        return details;
    }

}

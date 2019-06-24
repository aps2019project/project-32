package Duelyst.model.Card.Spell;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import Duelyst.model.ErrorType;
import javafx.scene.image.Image;

public class Madness extends Spell {

    public Madness() {
        super("Madness", 0, 650);
        super.cardImage = new Image("Duelyst/css/unit_gifs/f6_faiefestive_breathing.gif");
    }

    public Madness(Madness madness) {
        super(madness);
    }

    @Override
    public ErrorType castSpell(Battle battle, Cell cell, Player player) {

        return null;
    }

    public Spell duplicate() {
        Madness madness = new Madness(this);
        madness.cardImage = new Image("Duelyst/css/unit_gifs/f6_faiefestive_breathing.gif");
        return madness;
    }


    @Override
    public String getDesc() {
        return SpellWork.MADNESS.getMessage();
    }


    @Override
    public String showDetails() {
        String details;
        details = " Type : " + getType() + " - Name : " +
                this.getClass().getSimpleName() + " - MP : " + this.getCostToUse() +
                " - Desc: " + SpellWork.MADNESS.getMessage();
        return details;
    }
}

package Duelyst.model.Card.Spell;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import Duelyst.model.ErrorType;
import javafx.scene.image.Image;

public class Weakening extends Spell {

    public Weakening() {
        super("Weakening", 1, 1000);
        super.cardImage = new Image("Duelyst/css/unit_gifs/neutral_saberspinemk2_breathing.gif");
    }

    public Weakening(Weakening weakening) {
        super(weakening);
    }

    @Override
    public ErrorType castSpell(Battle battle, Cell cell, Player player) {

        return null;
    }

    public Spell duplicate() {
        Weakening weakening = new Weakening(this);
        weakening.cardImage = new Image("Duelyst/css/unit_gifs/neutral_saberspinemk2_breathing.gif");
        return weakening;
    }

    @Override
    public String getDesc() {
        return SpellWork.WEAKENING.getMessage();
    }

    @Override
    public String showDetails() {
        String details;
        details = " Type : " + getType() + " - Name : " +
                this.getClass().getSimpleName() + " - MP : " + this.getCostToUse() +
                " - Desc: " + SpellWork.WEAKENING.getMessage();
        return details;
    }
}

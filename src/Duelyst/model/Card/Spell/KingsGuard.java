package Duelyst.model.Card.Spell;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import Duelyst.model.ErrorType;
import javafx.scene.image.Image;

public class KingsGuard extends Spell {

    public KingsGuard() {
        super("KingsGuard", 9, 1750);
        super.cardImage = new Image("Duelyst/css/unit_gifs/f5_tier2general_breathing.gif");
    }

    public KingsGuard(KingsGuard kingsGuard) {
        super(kingsGuard);
    }

    @Override
    public ErrorType castSpell(Battle battle, Cell cell, Player player) {

        return null;
    }

    @Override
    public Spell duplicate() {
        KingsGuard kingsGuard = new KingsGuard(this);
        kingsGuard.cardImage = new Image("Duelyst/css/unit_gifs/f5_tier2general_breathing.gif");
        return kingsGuard;
    }

    @Override
    public String getDesc() {
        return SpellWork.KINGS_GUARD.getMessage();
    }

    @Override
    public String showDetails() {
        String details;
        details = " Type : " + getType() + " - Name : " +
                this.getClass().getSimpleName() + " - MP : " + this.getCostToUse() +
                " - Desc: " + SpellWork.KINGS_GUARD.getMessage();
        return details;
    }
}

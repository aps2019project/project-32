package Duelyst.model.Card.Spell;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import Duelyst.model.ErrorType;
import javafx.scene.image.Image;

public class HellFire extends Spell {

    public HellFire() {
        super("HellFire", 3, 600);
        super.cardImage = new Image("Duelyst/css/unit_gifs/f5_altgeneraltier2_breathing.gif");
    }

    public HellFire(HellFire hellFire) {
        super(hellFire);
    }

    @Override
    public ErrorType castSpell(Battle battle, Cell cell, Player player) {


        return null;
    }

    @Override
    public Spell duplicate() {
        HellFire hellFire = new HellFire(this);
        hellFire.cardImage = new Image("Duelyst/css/unit_gifs/f5_altgeneraltier2_breathing.gif");
        return hellFire;
    }

    @Override
    public String getDesc() {
        return SpellWork.HELL_FIRE.getMessage();
    }

    @Override
    public String showDetails() {
        String details;
        details = " Type : " + getType() + " - Name : " +
                this.getClass().getSimpleName() + " - MP : " + this.getCostToUse() +
                " - Desc: " + SpellWork.HELL_FIRE.getMessage();
        return details;
    }
}

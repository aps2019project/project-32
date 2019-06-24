package Duelyst.model.Card.Spell;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import Duelyst.model.ErrorType;
import javafx.scene.image.Image;

public class FireBall extends Spell {

    public FireBall() {
        super("FireBall", 1, 400);
        super.cardImage = new Image("Duelyst/css/unit_gifs/f4_maehvmk2_breathing.gif");
    }

    public FireBall(FireBall fireBall) {
        super(fireBall);
    }

    @Override
    public ErrorType castSpell(Battle battle, Cell cell, Player player) {

     return null;
    }

    public Spell duplicate() {
        FireBall fireBall = new FireBall(this);
        fireBall.cardImage = new Image("Duelyst/css/unit_gifs/f4_maehvmk2_breathing.gif");
        return fireBall;
    }


    @Override
    public String getDesc() {
        return SpellWork.FIREBALL.getMessage();
    }

    @Override
    public String showDetails() {
        String details;
        details = " Type : " + getType() + " - Name : " +
                this.getClass().getSimpleName() + " - MP : " + this.getCostToUse() +
                " - Desc: " + SpellWork.FIREBALL.getMessage();
        return details;
    }
}

package Duelyst.model.Card.Hero;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Card.Minion.SpecialPower;
import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class Dragon extends Hero {
    public Dragon() {
        super("Dragon", 4, 50, 8000, 0);
        this.cardImage = new Image("Duelyst/css/unit_gifs/boss_wolfpunch_breathing.gif");
        super.setCoolDownTime(1);
        super.cardImage = new Image("Duelyst/css/unit_gifs/boss_wolfpunch_breathing.gif");
        super.setMp(0);
    }

    public Dragon(Dragon dragon) {
        super(dragon);
    }

    public Hero duplicate() {
        Dragon dragon = new Dragon(this);
        dragon.cardImage = new Image("Duelyst/css/unit_gifs/boss_wolfpunch_breathing.gif");
        return dragon;
    }

    @Override
    public String showDetails() {
        String details = "Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : " + this.getHp()
                + " - Class : " + this.getTypeOfHit() + " – Special power : " + SpecialPower.DRAGON.getMessage();
        return details;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player) {

    }

    public String getDesc() {
        return SpecialPower.DRAGON.getMessage();
    }
}

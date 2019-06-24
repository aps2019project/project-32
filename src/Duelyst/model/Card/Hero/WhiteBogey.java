package Duelyst.model.Card.Hero;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Card.Minion.SpecialPower;
import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class WhiteBogey extends Hero {
    public WhiteBogey() {
        super("WhiteBogey", 4, 50, 8000, 0);
        super.setCoolDownTime(2);
        super.setMp(1);
        super.cardImage = new Image("Duelyst/css/unit_gifs/f2_altgeneraltier2_breathing.gif");
    }

    public WhiteBogey(WhiteBogey whiteBogey) {
        super(whiteBogey);
    }

    public Hero duplicate() {
        WhiteBogey whiteBogey = new WhiteBogey(this);
        whiteBogey.cardImage = new Image("Duelyst/css/unit_gifs/f2_altgeneraltier2_breathing.gif");
        return whiteBogey;
    }

    @Override
    public String showDetails() {
        String details = "Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : " + this.getHp()
                + " - Class : " + this.getTypeOfHit() + " – Special power : " + SpecialPower.WHITE_BOGEY.getMessage();
        return details;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player) {

    }

    @Override
    public String getDesc() {
        return SpecialPower.WHITE_BOGEY.getMessage();
    }
}

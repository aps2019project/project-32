package Duelyst.model.Card.Hero;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class Rostam extends Hero {
    public Rostam() {
        super("Rostam", 7, 55, 8000, 2);
        super.setCoolDownTime(0);
        super.setRange(4);
        super.cardImage = new Image("Duelyst/css/unit_gifs/f1_general_skinroguelegacy_breathing.gif");
    }

    public Rostam(Rostam rostam) {
        super(rostam);
    }

    public Hero duplicate() {
        Rostam rostam = new Rostam(this);
        rostam.cardImage = new Image("Duelyst/css/unit_gifs/f1_general_skinroguelegacy_breathing.gif");
        return rostam;
    }

    @Override
    public String showDetails() {
        String details = "Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : " + this.getHp()
                + " - Class : " + this.getTypeOfHit() + " – Special power : -. - CoolDown Time : -.";
        return details;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player) {

    }

    public String getDesc() {
        return "Nothing" + " - CoolDown Time : -.";
    }
}

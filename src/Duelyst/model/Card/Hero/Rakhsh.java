package Duelyst.model.Card.Hero;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Card.Minion.SpecialPower;
import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class Rakhsh extends Hero {
    public Rakhsh() {
        super("Rakhsh", 4, 50, 8000, 0);
        super.setCoolDownTime(2);
        super.setMp(1);
        super.cardImage = new Image("Duelyst/css/unit_gifs/f1_bromemk2_breathing.gif");
    }

    public Rakhsh(Rakhsh rakhsh) {
        super(rakhsh);
    }

    public Hero duplicate() {
        Rakhsh rakhsh = new Rakhsh(this);
        rakhsh.cardImage = new Image("Duelyst/css/unit_gifs/f1_bromemk2_breathing.gif");
        return rakhsh;
    }

    @Override
    public String showDetails() {
        String details = "Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : " + this.getHp()
                + " - Class : " + this.getTypeOfHit() + " – Special power : " + SpecialPower.RAKHSH.getMessage();
        return details;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player) {


    }

    public String getDesc() {
        return SpecialPower.RAKHSH.getMessage();
    }
}

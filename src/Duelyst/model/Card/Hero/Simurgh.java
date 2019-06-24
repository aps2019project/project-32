package Duelyst.model.Card.Hero;


import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Card.Minion.SpecialPower;
import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class Simurgh extends Hero {
    public Simurgh() {
        super("Simurgh", 4, 50, 9000, 0);
        super.setCoolDownTime(8);
        super.setMp(3);
        super.cardImage = new Image("Duelyst/css/unit_gifs/f1_tier2general_breathing.gif");
    }

    public Simurgh(Simurgh simurgh) {
        super(simurgh);
    }

    public Hero duplicate() {
        Simurgh simurgh = new Simurgh(this);
        simurgh.cardImage = new Image("Duelyst/css/unit_gifs/f1_tier2general_breathing.gif");
        return simurgh;
    }

    @Override
    public String showDetails() {
        String details = "Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : " + this.getHp()
                + " - Class : " + this.getTypeOfHit() + " – Special power : " + SpecialPower.SIMURGH.getMessage();
        return details;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player) {


    }

    @Override
    public String getDesc() {
        return SpecialPower.SIMURGH.getMessage();
    }
}

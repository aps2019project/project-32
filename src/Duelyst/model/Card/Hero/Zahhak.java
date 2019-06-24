package Duelyst.model.Card.Hero;


import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Card.Minion.SpecialPower;
import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class Zahhak extends Hero {
    public Zahhak() {
        super("Zahhak", 4, 50, 10000, 0);

        super.cardImage = new Image("Duelyst/css/unit_gifs/f2_general_skindogehai_breathing.gif");
        super.setCoolDownTime(0);
        super.setMp(1);
    }

    public Zahhak(Zahhak zahhak) {
        super(zahhak);
    }

    public Hero duplicate() {
        Zahhak zahhak = new Zahhak(this);
        zahhak.cardImage = new Image("Duelyst/css/unit_gifs/f2_general_skindogehai_breathing.gif");
        return zahhak;
    }

    @Override
    public String showDetails() {
        String details;
        details = "Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : " + this.getHp()
                + " - Class : " + this.getTypeOfHit() + " – Special power : " + SpecialPower.ZAHHAK.getMessage();
        return details;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player) {

    }

    public String getDesc() {
        return SpecialPower.ZAHHAK.getMessage();
    }
}

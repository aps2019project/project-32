package Duelyst.model.Card.Minion;
import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class TuranianSwampy extends Minion {
    public TuranianSwampy() {
        super("TuranianSwampy", 10, 3, 450, 2, 0, 0);
        super.cardImage = new Image("Duelyst/css/unit_gifs/boss_spelleater_breathing.gif");
    }

    public TuranianSwampy(TuranianSwampy turanianSwampy) {
        super(turanianSwampy);
    }

    public Minion duplicate() {
        TuranianSwampy turanianSwampy = new TuranianSwampy(this);
        turanianSwampy.cardImage = new Image("Duelyst/css/unit_gifs/boss_spelleater_breathing.gif");
        return turanianSwampy;
    }

    @Override
    public String showDetails() {
        String detail;
        detail = "Type : " + this.getType() + " - Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : " + this.getHp()
                + " - MP : " + this.getCostToUse() + " - Class : " + this.getTypeOfHit() + " - Special power :- .";
        return detail;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player, int activeTime) {

    }

    public String getDesc() {
        return " Nothing";
    }
}

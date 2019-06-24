package Duelyst.model.Card.Minion;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class GiantSnake extends Minion {
    public GiantSnake() {
        super("GiantSnake", 7, 14, 500, 8, 1, 5);
        super.setTimeOfActivationOfSpecialPower(1);
        super.cardImage = new Image("Duelyst/css/unit_gifs/boss_decepticlewings_breathing.gif");
    }

    public GiantSnake(GiantSnake giantSnake) {
        super(giantSnake);
    }

    public Minion duplicate() {
        GiantSnake giantSnake = new GiantSnake(this);
        giantSnake.cardImage = new Image("Duelyst/css/unit_gifs/boss_decepticlewings_breathing.gif");
        return giantSnake;
    }

    @Override
    public String showDetails() {
        String detail;
        detail = "Type : " + this.getType() + " - Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : " + this.getHp()
                + " - MP : " + this.getCostToUse() + " - Class : " + this.getTypeOfHit() + " - Special power: " + SpecialPower.GIANT_SNAKE.getMessage();
        return detail;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player, int activeTime) {

    }

    public String getDesc() {
        return SpecialPower.GIANT_SNAKE.getMessage();
    }
}

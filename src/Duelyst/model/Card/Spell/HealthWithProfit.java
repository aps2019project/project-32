package Duelyst.model.Card.Spell;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import Duelyst.model.ErrorType;
import javafx.scene.image.Image;

public class HealthWithProfit extends Spell {

    public HealthWithProfit() {
        super("HealthWithProfit", 0, 2250);
        super.cardImage = new Image("Duelyst/css/unit_gifs/f5_ragnoramk2_breathing.gif");
    }

    public HealthWithProfit(HealthWithProfit healthWithProfit) {
        super(healthWithProfit);
    }

    @Override
    public ErrorType castSpell(Battle battle, Cell cell, Player player) {

        return null;
    }

    public Spell duplicate() {
        HealthWithProfit healthWithProfit = new HealthWithProfit(this);

        healthWithProfit.cardImage = new Image("Duelyst/css/unit_gifs/f5_ragnoramk2_breathing.gif");
        return healthWithProfit;
    }

    @Override
    public String getDesc() {
        return SpellWork.HEALTH_WITH_PROFIT.getMessage();
    }

    @Override
    public String showDetails() {
        String details;
        details = " Type : " + getType() + " - Name : " +
                this.getClass().getSimpleName() + " - MP : " + this.getCostToUse() +
                " - Desc: " + SpellWork.HEALTH_WITH_PROFIT.getMessage();
        return details;
    }
}

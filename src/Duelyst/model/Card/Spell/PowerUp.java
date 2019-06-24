package Duelyst.model.Card.Spell;
import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import Duelyst.model.ErrorType;
import javafx.scene.image.Image;

public class PowerUp extends Spell {

    public PowerUp() {
        super("PowerUp", 2, 2500);
        super.cardImage = new Image("Duelyst/css/unit_gifs/f6_snowchasermk2_breathing.gif");
    }

    public PowerUp(PowerUp powerUp) {
        super(powerUp);
    }

    @Override
    public ErrorType castSpell(Battle battle, Cell cell, Player player) {
        return null;
    }

    public Spell duplicate() {
        PowerUp powerUp = new PowerUp(this);
        powerUp.cardImage = new Image("Duelyst/css/unit_gifs/f6_snowchasermk2_breathing.gif");
        return powerUp;
    }

    @Override
    public String getDesc() {
        return SpellWork.POWER_UP.getMessage();
    }

    @Override
    public String showDetails() {
        String details;
        details = " Type : " + getType() + " - Name : " +
                this.getClass().getSimpleName() + " - MP : " + this.getCostToUse() +
                " - Desc: " + SpellWork.POWER_UP.getMessage();
        return details;
    }
}

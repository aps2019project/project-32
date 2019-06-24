package Duelyst.model.Card.Spell;
import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import Duelyst.model.ErrorType;
import javafx.scene.image.Image;

public class Sacrifice extends Spell {

    public Sacrifice() {
        super("Sacrifice", 2, 1600);
        super.cardImage=new Image("Duelyst/css/unit_gifs/f6_tier2general_breathing.gif");
    }

    public Sacrifice(Sacrifice sacrifice) {
        super(sacrifice);
    }

    @Override
    public ErrorType castSpell(Battle battle, Cell cell, Player player) {

        return null;
    }

    public Spell duplicate() {
        Sacrifice sacrifice = new Sacrifice(this);
        sacrifice.cardImage = new Image("Duelyst/css/unit_gifs/f6_tier2general_breathing.gif");
        return sacrifice;
    }

    @Override
    public String getDesc() {
        return SpellWork.SACRIFICE.getMessage();
    }


    @Override
    public String showDetails() {
        String details;
        details = " Type : " + getType() + " - Name : " +
                this.getClass().getSimpleName() + " - MP : " + this.getCostToUse() +
                " - Desc: " + SpellWork.SACRIFICE.getMessage();
        return details;
    }
}

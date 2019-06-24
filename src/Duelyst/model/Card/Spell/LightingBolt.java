package Duelyst.model.Card.Spell;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import Duelyst.model.ErrorType;
import javafx.scene.image.Image;

public class LightingBolt extends Spell {

    public LightingBolt() {
        super("LightingBolt", 2, 1250);
        super.cardImage = new Image("Duelyst/css/unit_gifs/f6_altgeneraltier2_breathing.gif");
    }

    public LightingBolt(LightingBolt lightingBolt) {
        super(lightingBolt);
    }

    @Override
    public ErrorType castSpell(Battle battle, Cell cell, Player player) {

        return null;
    }

    public Spell duplicate() {
        LightingBolt lightingBolt = new LightingBolt(this);
        lightingBolt.cardImage = new Image("Duelyst/css/unit_gifs/f6_altgeneraltier2_breathing.gif");
        return lightingBolt;
    }

    @Override
    public String getDesc() {
        return SpellWork.LIGHTING_BOLT.getMessage();
    }


    @Override
    public String showDetails() {
        String details;
        details = " Type : " + getType() + " - Name : " +
                this.getClass().getSimpleName() + " - MP : " + this.getCostToUse() +
                " - Desc: " + SpellWork.LIGHTING_BOLT.getMessage();
        return details;
    }
}

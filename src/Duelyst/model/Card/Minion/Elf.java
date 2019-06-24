package Duelyst.model.Card.Minion;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;
import javafx.scene.image.Image;

public class Elf extends Minion {
    public Elf() {
        super("Elf", 4, 10, 500, 5, 1, 4);
        super.setTimeOfActivationOfSpecialPower(4);
        super.cardImage = new Image("Duelyst/css/unit_gifs/boss_decepticle_breathing.gif");
    }

    public Elf(Elf elf) {
        super(elf);
    }

    public Minion duplicate() {
        Elf elf = new Elf(this);
        elf.cardImage = new Image("Duelyst/css/unit_gifs/boss_decepticle_breathing.gif");
        return elf;
    }

    @Override
    public String showDetails() {
        String detail;
        detail = "Type : " + this.getType() + " - Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : " + this.getHp()
                + " - MP : " + this.getCostToUse() + " - Class : " + this.getTypeOfHit() + " - Special power : " + SpecialPower.ELF.getMessage();
        return detail;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player, int activeTime) {

    }

    public String getDesc() {
        return SpecialPower.ELF.getMessage();
    }
}

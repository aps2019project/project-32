package Duelyst.model.Card.Hero;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;

import Duelyst.model.Card.Minion.SpecialPower;
import javafx.scene.image.Image;

public class Arash extends Hero {
    public Arash() {
        super("Arash", 2, 30, 10000, 1);
        super.cardImage = new Image("Duelyst/css/unit_gifs/boss_vampire_breathing.gif");
        super.setCoolDownTime(2);
        super.setMp(2);
        super.setRange(6);
    }

    public Arash(Arash arash) {
        super(arash);
    }

    public Hero duplicate() {
        Arash arash = new Arash(this);
        arash.cardImage = new Image("Duelyst/css/unit_gifs/boss_vampire_breathing.gif");
        return arash;
    }

    @Override
    public String showDetails() {
        String details = "Name : " + this.getName() + " - AP : " + this.getAp() + " - HP : " + this.getHp()
                + " - Class : " + this.getTypeOfHit() + " – Special power : " + SpecialPower.ARASH.getMessage();
        return details;
    }

    @Override
    public void castSpecialPower(Battle battle, Cell cell, Player player) {
        if (player.getMana() >= this.getMp()) {
            int indexOfRow = -1;
            for (int i = 0; i < 5; i++) {
                if (battle.getMap().get(i).contains(cell)) {
                    indexOfRow = i;
                    break;
                }
            }
            for (int i = 0; i < 9; i++) {
                if (battle.getMap().get(indexOfRow).get(i).getHero() != null) {
                    battle.getMap().get(indexOfRow).get(i).getHero().decrementHp(4 - battle.getMap().get(indexOfRow).get(i).getHero().getHolyCounter());
                } else if (battle.getMap().get(indexOfRow).get(i).getMinion() != null) {
                    battle.getMap().get(indexOfRow).get(i).getMinion().decrementHp(4 - battle.getMap().get(indexOfRow).get(i).getMinion().getHolyCounter());
                }
            }
        } else {

        }

    }

    public String getDesc() {
        return SpecialPower.ARASH.getMessage();
    }
}

package Duelyst.model.Buff;

import Duelyst.model.Player;
import Duelyst.model.Card.Hero.Hero;
import Duelyst.model.Card.Minion.Minion;
import Duelyst.model.Cell;

public class ManaItemBuff extends Buff {
    private Player player;
    private int unit;

    public ManaItemBuff(Player player, int unit) {
        this.player = player;
        this.unit = unit;
    }

    public void castBuff() {
        this.player.incrementMana(unit);
    }

    @Override
    public void setCasting(Cell cell, Hero hero, Minion minion) {

    }

    @Override
    public void dispel(Hero hero) {

    }

    @Override
    public void dispel(Minion minion) {

    }

}

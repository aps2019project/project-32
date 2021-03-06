package Duelyst.model.Buff;

import Duelyst.model.Card.Hero.Hero;
import Duelyst.model.Card.Minion.Minion;
import Duelyst.model.Cell;


import java.util.ArrayList;

public class MultiStageBuff extends Buff {
    private Minion minion;
    private ArrayList<Integer> units;

    public MultiStageBuff(ArrayList<Integer> units, Minion minion) {
        this.units = units;
        this.minion = minion;
    }

    public void decrementHp(int unit) {
        this.minion.decrementHp(unit);
    }

    @Override
    public void castBuff() {
        decrementHp(this.units.get(0));
        this.units.remove(0);
    }

    @Override
    public void setCasting(Cell cell, Hero hero, Minion minion) {

    }

    @Override
    public void dispel(Minion minion) {

    }

    @Override
    public void dispel(Hero hero) {

    }
}

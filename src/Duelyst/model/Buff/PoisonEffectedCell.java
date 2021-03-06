package Duelyst.model.Buff;

import Duelyst.model.Card.Hero.Hero;
import Duelyst.model.Card.Minion.Minion;
import Duelyst.model.Cell;

public class PoisonEffectedCell extends Buff {

    public void poison(Hero hero) {
        hero.decrementHp(1);
    }

    public void poison(Minion minion) {
        if (!minion.getClass().getSimpleName().equals("Piran"))
            minion.decrementHp(1);
    }

    private Cell cell;
    private Hero hero;
    private Minion minion;

    public void setCasting( Cell cell, Hero hero, Minion minion) {
        this.cell = cell;
        this.hero = hero;
        this.minion = minion;
    }

    public Cell getCell() {
        return cell;
    }

    public Hero getHero() {
        return hero;
    }

    public Minion getMinion() {
        return minion;
    }

    @Override
    public void castBuff() {
        if (this.cell != null) {
            if (this.cell.getMinion() != null) {
                poison(this.cell.getMinion());
            }
            if (this.cell.getHero() != null) {
                poison(this.cell.getHero());
            }
        }
    }

    @Override
    public void dispel(Hero hero) {

    }

    @Override
    public void dispel(Minion minion) {

    }

}

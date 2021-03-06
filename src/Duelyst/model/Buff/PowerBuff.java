package Duelyst.model.Buff;

import Duelyst.model.Card.Hero.Hero;
import Duelyst.model.Card.Minion.Minion;
import Duelyst.model.Cell;


public class PowerBuff extends Buff {

    private int unit;

    private boolean apOrHp;

    private Cell cell;
    private Hero hero;
    private Minion minion;

    public void setCasting(Cell cell,Hero hero,Minion minion) {
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

    public PowerBuff(int unit, boolean apOrHp){
        this.unit = unit;
        this.apOrHp = apOrHp;
    }

    public void incrementAp(Hero hero){
        hero.incrementAp(unit);
    }

    public void incrementHp(Hero hero){
        hero.incrementHp(unit);
    }

    public void incrementAp(Minion minion){
        minion.incrementAp(unit);
    }

    public void incrementHp(Minion minion){
        minion.incrementHp(unit);
    }

    @Override
    public void castBuff() {
        if (apOrHp){
            if (this.hero != null){
                incrementAp(this.hero);
            }
            if (this.minion != null){
                incrementAp(this.minion);
            }
        } else {
            if (this.hero != null){
                incrementHp(this.hero);
            }
            if (this.minion != null){
                incrementHp(this.minion);
            }
        }
    }

    @Override
    public void dispel(Hero hero) {
        if (apOrHp) {
            hero.decrementAp(unit);
        } else {
            hero.decrementHp(unit);
        }
    }

    @Override
    public void dispel(Minion minion) {
        if (apOrHp) {
            minion.decrementAp(unit);
        } else {
            minion.decrementHp(unit);
        }
    }


}

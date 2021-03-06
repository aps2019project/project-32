package Duelyst.model.Card.Hero;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Buff.Buff;
import Duelyst.model.Card.Card;
import Duelyst.model.Cell;
import javafx.scene.image.Image;

import java.util.ArrayList;

public abstract class Hero extends Card {
    private static ArrayList<Hero> heroes = new ArrayList<>();
    private int numberOfFlag = 0;

    public int getNumberOfFlag() {
        return numberOfFlag;
    }

    public void setNumberOfFlag(int numberOfFlag) {
        this.numberOfFlag = numberOfFlag;
    }

    static {
        new Arash();
        new Dragon();
        new Esfandyar();
        new Kaveh();
        new Legend();
        new Rakhsh();
        new Rostam();
        new Simurgh();
        new WhiteBogey();
        new Zahhak();
    }

    private int ap;
    private int hp;
    private int mp;
    private int holyCounter = 0;
    private boolean isStunning = false;
    private boolean counterAttack = true;
    private int typeOfRange;//0 melee 1 ranged 2 hybrid
    private int range;
    private int coolDownTime = 0;
    private int timeNeededToCool = 0;
    private ArrayList<Buff> ownBuffs = new ArrayList<>();

    public ArrayList<Buff> getOwnBuffs() {
        return ownBuffs;
    }

    public int getRange() {
        return range;
    }

    public int getTimeNeededToCool() {
        return timeNeededToCool;
    }

    public void setTimeNeededToCool(int timeNeededToCool) {
        this.timeNeededToCool = timeNeededToCool;
    }

    public int getTypeOfRange() {
        return typeOfRange;
    }

    public boolean isCounterAttack() {
        return counterAttack;
    }

    public void setCounterAttack(boolean counterAttack) {
        this.counterAttack = counterAttack;
    }

    public Hero(String name, int ap, int hp, int costOfBuy, int typeOfRange) {
        this.setName(name);
        this.setCostOfBuy(costOfBuy);
        this.typeOfRange = typeOfRange;
        this.ap = ap;
        this.hp = hp;
        heroes.add(this);
    }

    public Hero(Hero hero) {
        this.setName(hero.getName());
        this.setCostOfBuy(hero.getCostOfBuy());
        this.typeOfRange = hero.typeOfRange;
        this.range = hero.range;
        this.coolDownTime = hero.coolDownTime;
        this.ap = hero.ap;
        this.hp = hero.hp;
        this.mp = hero.mp;
    }

    public void setCoolDownTime(int coolDownTime) {
        this.coolDownTime = coolDownTime;
    }

    public int getCoolDownTime() {
        return coolDownTime;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setAp(int ap) {
        this.ap = ap;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public static ArrayList<Hero> getHeroes() {
        return heroes;
    }

    public int getHp() {
        return hp;
    }

    public int getAp() {
        return ap;
    }

    public String getType() {
        return "Hero";
    }

    public void setTypeOfHit(int typeOfRange) {
        this.typeOfRange = typeOfRange;
    }

    public String getTypeOfHit() {
        if (this.typeOfRange == 0)
            return "Melee";
        else if (this.typeOfRange == 1)
            return "Ranged";
        else if (this.typeOfRange == 2)
            return "Hybrid";
        else
            return null;
    }

    public boolean isStunning() {
        return isStunning;
    }

    public void setStunning(boolean stunning) {
        isStunning = stunning;
    }

    public int getHolyCounter() {
        return holyCounter;
    }

    public void incrementHolyCounter() {
        this.holyCounter++;
    }
    public void decrementHolyCounter() {
        this.holyCounter--;
    }

    public void setHolyCounter(int holyCounter) {
        this.holyCounter = holyCounter;
    }

    public void incrementAp(int unit) {
        this.ap += unit;
    }

    public void decrementAp(int unit) {
        if (unit > 0)
            this.ap -= unit;
    }

    public void incrementHp(int unit) {
        this.hp += unit;
    }

    public void decrementHp(int unit) {
        if (unit > 0)
            this.hp -= unit;
    }

    public abstract void castSpecialPower(Battle battle, Cell cell, Player player);

    public abstract String getDesc();

    public Hero duplicate() {
        return null;
    }
    public Image getImage(){
        return this.cardImage;
    }


}

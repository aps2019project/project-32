package com.company.models.widget.cards.Warriors;

import com.company.controller.Menus.battlemenus.BattleMenu;
import com.company.models.widget.cards.Card;
import com.company.models.widget.cards.spells.Spell;
import com.company.models.widget.cards.spells.SpellKind;
import com.company.models.widget.cards.spells.SpellType;

import java.util.ArrayList;
import java.util.Iterator;

public class Warrior extends Card implements Movable, Attackable, Defendable {
    protected int health;
    protected int power;
    protected AttackType attackType;
    protected int attackRadius;
    protected Spell specialSpell;
    protected boolean canMove;
    protected boolean canAttack;
    protected ArrayList<Spell> spellsOnWarrior;


    public Warrior(String name, int price, int health, int power, AttackType attackType, int attackRadius) {
        super(name, price);
        this.health = health;
        this.power = power;
        this.attackType = attackType;
        this.attackRadius = attackRadius;

    }


    @Override
    public String toShow() {
        return String.format("(Warrior) CardName : %s - CardID : %d - Health : %d - Power : %d\n",
                this.getName(), this.getID(), this.getHealth(), this.getPower());
    }

    @Override
    public void attack(Warrior defender) {
        defender.changeHealth(-this.getPower());
        if (this instanceof Minion && ((Minion)this).minionSpellType == MinionSpellType.OnAttack){
            this.getSpecialSpell().doEffectAction(BattleMenu.getBattle().getBattleMap(),BattleMenu.getBattle().getBattleMap().getPosition(defender));
        }
        if(defender instanceof Minion && ((Minion)this).minionSpellType == MinionSpellType.OnDefense){
            defender.getSpecialSpell().doEffectAction(BattleMenu.getBattle().getBattleMap(),BattleMenu.getBattle().getBattleMap().getPosition(this));
        }
    }

    @Override
    public void defend(Card attacker) {

    }

    @Override
    public void move() {

    }

    public void changeHealth(int value) {
        this.health += value;
    }

    public void changePower(int value) {
        this.power += value;
    }

    public boolean isDead() {
        return this.health <= 0;
    }


    public void moveTiredAffect() {
        canMove = false;
    }

    public void attackTiredAffect() {
        canAttack = false;
    }

    public boolean canAttack() {
        return canAttack;
    }

    public boolean canMove() {
        return canMove;
    }


    public int getHealth() {
        return health;
    }

    public void increaseHealth(int value) {
        health += value;
    }

    public void decreaseHealth(int value) {
        health -= value;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public void setAttackType(AttackType attackType) {
        this.attackType = attackType;
    }

    public int getAttackRadius() {
        return attackRadius;
    }

    public void setAttackRadius(int attackRadius) {
        this.attackRadius = attackRadius;
    }

    public Spell getSpecialSpell() {
        return specialSpell;
    }

    public void setSpecialSpell(Spell specialSpell) {
        this.specialSpell = specialSpell;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public void setSpecialSpell(SpellKind spellKind,int manaCost,int coolDown, int affectPoisonTurnNumber, int affectDisarmTurnNumber, int affectStunTurnNumber, int spellRange, int changeAttackPoint, int changeHealthPoint, SpellType... spellTypes) {
        if (spellKind == SpellKind.spellCard)
            this.specialSpell = new Spell(SpellKind.spellCard,"", 0, manaCost, coolDown, affectPoisonTurnNumber, affectDisarmTurnNumber, affectStunTurnNumber, spellRange, changeAttackPoint, changeHealthPoint, spellTypes);
        else if (spellKind == SpellKind.Buff)
            this.specialSpell = new Spell(SpellKind.Buff,"", 0, manaCost, coolDown, affectPoisonTurnNumber, affectDisarmTurnNumber, affectStunTurnNumber, spellRange, changeAttackPoint, changeHealthPoint, spellTypes);

    }

    public void removeNegativeBuffs() {
        Iterator<Spell> spellIterable = spellsOnWarrior.iterator();
        while (spellIterable.hasNext()) {
            Spell spell = spellIterable.next();
            if (spell.getSpellKind()==SpellKind.Buff && (spell.getChangeHealthPoint() < 0 || spell.getChangeHealthPoint() < 0)) {
                spellIterable.remove();
            }
        }
    }
    public void removePositiveBuffs() {
        Iterator<Spell> spellIterable = spellsOnWarrior.iterator();
        while (spellIterable.hasNext()) {
            Spell spell = spellIterable.next();
            if (spell.getSpellKind()==SpellKind.Buff && (spell.getChangeHealthPoint() > 0 || spell.getChangeHealthPoint() > 0)) {
                spellIterable.remove();
            }
        }
    }
}
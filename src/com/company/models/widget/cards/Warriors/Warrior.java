package com.company.models.widget.cards.Warriors;

import com.company.models.widget.cards.Card;
import com.company.models.widget.cards.spells.Spell;
import com.company.models.widget.cards.spells.SpellType;

import java.util.ArrayList;

public class Warrior extends Card implements Movable, Attackable, Defendable
{
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
    public String toShow()
    {
        return String.format("(Warrior) CardName : %s - CardID : %d - Health : %d - Power : %d\n",
                this.getName(), this.getID(), this.getHealth(), this.getPower());
    }

    @Override
    public void attack(Card defender)
    {

    }

    @Override
    public void defend(Card attacker)
    {

    }

    @Override
    public void move()
    {

    }

    public void changeHealth(int value){
        this.health += value;
    }

    public void changePower(int value){
        this.power += value;
    }

    public boolean isDead()
    {
        return this.health <= 0;
    }


    public void moveTiredAffect()
    {
        canMove = false;
    }

    public void attackTiredAffect()
    {
        canAttack = false;
    }

    public boolean canAttack()
    {
        return canAttack;
    }

    public boolean canMove()
    {
        return canMove;
    }


    public int getHealth()
    {
        return health;
    }

    public void increaseHealth(int value)
    {
        health += value;
    }

    public void decreaseHealth(int value)
    {
        health -= value;
    }

    public int getPower()
    {
        return power;
    }

    public void setPower(int power)
    {
        this.power = power;
    }

    public AttackType getAttackType()
    {
        return attackType;
    }

    public void setAttackType(AttackType attackType)
    {
        this.attackType = attackType;
    }

    public int getAttackRadius()
    {
        return attackRadius;
    }

    public void setAttackRadius(int attackRadius)
    {
        this.attackRadius = attackRadius;
    }

    public Spell getSpecialSpell()
    {
        return specialSpell;
    }

    public void setSpecialSpell(Spell specialSpell)
    {
        this.specialSpell = specialSpell;
    }

    public boolean isCanMove()
    {
        return canMove;
    }

    public void setCanMove(boolean canMove)
    {
        this.canMove = canMove;
    }

    public boolean isCanAttack()
    {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack)
    {
        this.canAttack = canAttack;
    }

    public void setSpecialSpell(int coolDown, int affectPoisonTurnNumber, int affectDisarmTurnNumber, int affectStunTurnNumber, int spellRange, int changeAttackPoint, int changeHealthPoint, SpellType... spellTypes){
        this.specialSpell = new Spell("",0,0,coolDown,affectPoisonTurnNumber,affectDisarmTurnNumber,affectStunTurnNumber,spellRange,changeAttackPoint,changeHealthPoint,spellTypes);
    }

}

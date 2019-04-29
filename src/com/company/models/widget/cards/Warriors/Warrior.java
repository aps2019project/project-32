package com.company.models.widget.cards.Warriors;

import com.company.models.widget.cards.Card;
import com.company.models.widget.cards.Spell;

public abstract class Warrior extends Card implements Movable, Attackable, Defendable
{
    protected int health;
    protected int power;
    protected AttackType attackType;
    protected int attackRadius;
    protected Spell specialSpell;
    protected boolean canMove;
    protected boolean canAttack;
    protected int numberOfTurnsDisarmed;
    protected int numberOfFieryTurn;
    protected int numberOfPoisonTurn;
    protected int numberOfHolyTurn;

    public Warrior(String name,int health,int power,int cash,int manaCost,AttackType attackType)
    {
        super(name,cash,manaCost);
        this.health = health;
        this.power = power;
        this.attackType = attackType;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getNumberOfTurnsDisarmed() {
        return numberOfTurnsDisarmed;
    }

    public void setNumberOfTurnsDisarmed(int numberOfTurnsDisarmed) {
        this.numberOfTurnsDisarmed = numberOfTurnsDisarmed;
    }

    @Override
    public abstract void attack(Card defender);

    @Override
    public abstract void defend(Card attacker);

    @Override
    public void move()
    {

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
    public void increasePower(int number){
        this.setPower(this.getPower()+number);
    }

    public void decreasePower(int number){
        this.power-=number;
    }
}

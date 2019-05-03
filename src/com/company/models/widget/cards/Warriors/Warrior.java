package com.company.models.widget.cards.Warriors;

import com.company.models.widget.cards.Card;
import com.company.models.widget.cards.spells.Spell;
import com.company.models.widget.cards.spells.SpellActiveTime;
import com.company.models.widget.cards.spells.SpellKind;
import com.company.models.widget.cards.spells.SpellType;
import com.company.models.widget.cards.spells.effects.Effectable;

import java.util.ArrayList;

public class Warrior extends Card implements Attackable
{
    protected int health;
    protected int power;
    protected AttackType attackType;
    protected int attackRadius;
    protected Spell specialSpell;
    protected boolean canMove;
    protected boolean canAttack;
    protected ArrayList<Effectable> effectsOnWarrior;

    public Warrior(String name, int price, int health, int power, AttackType attackType, int attackRadius, Spell specialSpell)
    {
        super(name, price);
        this.health = health;
        this.power = power;
        this.attackType = attackType;
        this.attackRadius = attackRadius;
        this.specialSpell = specialSpell;
    }


    @Override
    public String toShow()
    {
        return String.format("(Warrior) CardName : %s - CardID : %d - Health : %d - Power : %d\n",
                this.getName(), this.getID(), this.getHealth(), this.getPower());
    }

    @Override
    public void attack(Warrior defender)
    {
//        Position attackerPosition = BattleMenu.getInstance().getCurrentBattle().getBattleMap().getPosition(this);
//        Position defenderPosition = BattleMenu.getInstance().getCurrentBattle().getBattleMap().getPosition(defender);
//        defender.changeHealth(-this.getPower());
//        if (this instanceof Minion && ((Minion) this).getMinionSpellType() == MinionSpellType.OnAttack)
//        {
//            this.getSpecialSpell().doEffectAction(BattleMenu.getInstance().getCurrentBattle().getBattleMap(), defenderPosition);
//        }
//        else if (!defender.isDisarm() && defender instanceof Minion && ((Minion) this).getMinionSpellType() == MinionSpellType.OnDefense)
//        {
//            if (defender.getSpecialSpell().getSpellTypes().contains(SpellType.onEnemy))
//                defender.getSpecialSpell().doEffectAction(BattleMenu.getInstance().getCurrentBattle().getBattleMap(), attackerPosition);
//            if (defender.getSpecialSpell().getSpellTypes().contains(SpellType.onFriend))
//                defender.getSpecialSpell().doEffectAction(BattleMenu.getInstance().getCurrentBattle().getBattleMap(), defenderPosition);
//
//        }
//        if (!defender.isDisarm() && defender.getAttackType() == AttackType.Melee)
//        {
//            if (attackerPosition.col - defenderPosition.col == 1 || attackerPosition.row - defenderPosition.row == 1)
//            {
//                this.changeHealth(-defender.getPower());
//            }
//        }
//        else if (!defender.isDisarm() && defender.getAttackType() == AttackType.Hybrid)
//        {
//            this.changeHealth(-defender.getPower());
//        }
//        else if (!defender.isDisarm() && defender.getAttackType() == AttackType.Ranged)
//        {
//            if (attackerPosition.col - defenderPosition.col != 1 && attackerPosition.row - defenderPosition.row != 1)
//            {
//                this.changeHealth(-defender.getPower());
//            }
//        }
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

    public void setSpecialSpell(SpellActiveTime spellActiveTime, SpellKind spellKind, int manaCost, int coolDown, int affectPoisonTurnNumber, int affectDisarmTurnNumber, int affectStunTurnNumber, int spellRange, int changeAttackPoint, int changeHealthPoint, SpellType... spellTypes)
    {
        //TODO
    }

    public ArrayList<Effectable> getEffectsOnWarrior()
    {
        return effectsOnWarrior;
    }

    public void setEffectsOnWarrior(ArrayList<Effectable> effectsOnWarrior)
    {
        this.effectsOnWarrior = effectsOnWarrior;
    }

    public void doEffect()
    {
        for (Effectable effectable : this.getEffectsOnWarrior())
            effectable.doEffect(this,null);

        effectsOnWarrior.removeIf(effectable -> effectable.getTurnRemaining() == 0);
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

    public void setHealth(int health)
    {
        this.health = health;
    }
}
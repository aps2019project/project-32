package com.company.models.widget.cards.Warriors;

import com.company.controller.Menus.battlemenus.BattleMenu;
import com.company.models.Position;
import com.company.models.widget.cards.Card;
import com.company.models.widget.cards.spells.ActiveTime;
import com.company.models.widget.cards.spells.Spell;
import com.company.models.widget.cards.spells.effects.Effectable;

import java.util.ArrayList;

public class Warrior extends Card implements Attackable
{
    protected int health;
    protected int power;
    protected AttackType attackType;
    protected int attackRadius;

    protected Spell specialSpell;
    protected Spell passiveSpell;

    protected boolean canMove = true;
    protected boolean canAttack = true;
    protected ArrayList<Effectable> effectsOnWarrior = new ArrayList<>();

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
    protected Object clone() throws CloneNotSupportedException
    {
        return new Warrior(name, price, health, power, attackType, attackRadius, ((Spell) specialSpell.clone()));
    }

    @Override
    public String toShow()
    {
        return String.format("(Warrior) CardName : %s - CardID : %d - Health : %d - Power : %d - ID : %d\n",
                this.getName(), this.getID(), this.getHealth(), this.getPower(), this.ID);
    }

    public void doOnAttackSpells(Spell spell, Warrior defender) throws Exception
    {
        Position attackerPosition = BattleMenu.getInstance().getCurrentBattle().getBattleMap().getPosition(this);
        Position defenderPosition = BattleMenu.getInstance().getCurrentBattle().getBattleMap().getPosition(defender);

        if (spell.getActiveTime() == ActiveTime.onAttack)
        {
            switch (spell.getFoe())
            {
                case enemy:

                    switch (spell.getTargetType())
                    {
                        case onMinion:
                            if (defender instanceof Minion)
                                spell.generalDo(BattleMenu.getInstance().getCurrentBattle().getBattleMap(),
                                        defenderPosition);
                            break;
                        case onHero:
                            if (defender instanceof Hero)
                                spell.generalDo(BattleMenu.getInstance().getCurrentBattle().getBattleMap(),
                                        defenderPosition);

                        case onMinionOrHero:
                            spell.generalDo(BattleMenu.getInstance().getCurrentBattle().getBattleMap(),
                                    defenderPosition);
                    }

                case friend:

                    switch (spell.getTargetType())
                    {
                        case onMinion:
                            if (this instanceof Minion)
                                spell.generalDo(BattleMenu.getInstance().getCurrentBattle().getBattleMap(),
                                        attackerPosition);

                        case onHero:
                            spell.generalDo(BattleMenu.getInstance().getCurrentBattle().getBattleMap(),
                                    BattleMenu.getInstance().getCurrentBattle().getBattleMap().getHeroPosition
                                            (this.ownerPlayer));

                        case onMinionOrHero:
                            spell.generalDo(BattleMenu.getInstance().getCurrentBattle().getBattleMap(),
                                    attackerPosition);
                    }

            }
        }
    }

    public void doOnDefendSpells(Spell spell, Warrior defender) throws Exception
    {
        Position attackerPosition = BattleMenu.getInstance().getCurrentBattle().getBattleMap().getPosition(this);
        Position defenderPosition = BattleMenu.getInstance().getCurrentBattle().getBattleMap().getPosition(defender);

        if (spell.getActiveTime() == ActiveTime.onDefend)
        {
            switch (spell.getFoe())
            {
                case enemy:

                    switch (spell.getTargetType())
                    {
                        case onMinion:
                            if (this instanceof Minion)
                                spell.generalDo(BattleMenu.getInstance().getCurrentBattle().getBattleMap(),
                                        attackerPosition);
                            break;
                        case onHero:
                            if (this instanceof Hero)
                                spell.generalDo(BattleMenu.getInstance().getCurrentBattle().getBattleMap(),
                                        BattleMenu.getInstance().getCurrentBattle().getBattleMap().getHeroPosition
                                                (defender.ownerPlayer));

                            break;
                        case onMinionOrHero:
                            spell.generalDo(BattleMenu.getInstance().getCurrentBattle().getBattleMap(),
                                    attackerPosition);

                            break;
                    }

                case friend:

                    switch (spell.getTargetType())
                    {
                        case onMinion:
                            if (defender instanceof Minion)
                                spell.generalDo(BattleMenu.getInstance().getCurrentBattle().getBattleMap(),
                                        defenderPosition);

                        case onHero:
                            if (defender instanceof Hero)
                                spell.generalDo(BattleMenu.getInstance().getCurrentBattle().getBattleMap(),
                                        defenderPosition);

                        case onMinionOrHero:
                            spell.generalDo(BattleMenu.getInstance().getCurrentBattle().getBattleMap(),
                                    defenderPosition);

                    }

            }
        }
    }

    @Override
    public void attack(Warrior defender)
    {
        defender.health -= this.power;
        try
        {
            for (Effectable effectable : this.effectsOnWarrior)
                if (effectable.getActiveTime() == ActiveTime.onAttack)
                    effectable.doEffect(this);

            this.effectsOnWarrior.removeIf(effectable -> effectable.getTurnRemaining() == 0);

            doOnAttackSpells(specialSpell, defender);
            doOnAttackSpells(passiveSpell, defender);


            for (Effectable effectable : defender.effectsOnWarrior)
                if (effectable.getActiveTime() == ActiveTime.onDefend)
                    effectable.doEffect(defender);

            defender.effectsOnWarrior.removeIf(effectable -> effectable.getTurnRemaining() == 0);

            doOnDefendSpells(defender.specialSpell, defender);
            doOnDefendSpells(defender.passiveSpell, defender);
        }
        catch (Exception ignored)
        {
        }
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
            effectable.doEffect(this);

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

    public void setSpecialSpell(Spell specialSpell)
    {
        this.specialSpell = specialSpell;
    }

    public Spell getPassiveSpell()
    {
        return passiveSpell;
    }

    public void setPassiveSpell(Spell passiveSpell)
    {
        this.passiveSpell = passiveSpell;
    }
}
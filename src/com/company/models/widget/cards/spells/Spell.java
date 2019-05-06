package com.company.models.widget.cards.spells;

import com.company.controller.Exceptions.InvalidAttack;
import com.company.controller.Exceptions.InvalidPosition;
import com.company.controller.Menus.battlemenus.BattleMenu;
import com.company.models.Position;
import com.company.models.battle.Battle;
import com.company.models.widget.cards.Card;
import com.company.models.widget.cards.Warriors.Hero;
import com.company.models.widget.cards.Warriors.Minion;
import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.cards.spells.effects.Effectable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Spell extends Card
{
    protected int manaCost;
    protected int coolDown;
    protected int coolDownRemaining;

    private int spellRange;
    private Type type;
    private FOE foe;
    private TargetType targetType;
    private ActiveTime activeTime;
    private Area area;
    private ArrayList<Effectable> effects;

    public Spell(Area area, FOE foe, TargetType targetType, ActiveTime activeTime, Type type, String name, int price, int manaCost, int coolDown, int spellRange, Effectable... effectables)
    {
        super(name, price);
        this.area = area;
        this.foe = foe;
        this.targetType = targetType;
        this.activeTime = activeTime;
        this.manaCost = manaCost;
        this.coolDown = coolDown;
        this.spellRange = spellRange;
        this.type = type;
        Collections.addAll(effects, effectables);
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        ArrayList<Effectable> clonedEffects = new ArrayList<>();
        for (Effectable effect : effects)
            clonedEffects.add(((Effectable) effect.clone()));

        return new Spell(area, foe, targetType, activeTime, type, name, price, manaCost, coolDown, spellRange,
                clonedEffects.toArray(new Effectable[0]));
    }

    @Override
    public String toShow()
    {
        return String.format("(Spell) - Name : %s – " +
                "MP : %d - CoolDown : %d - " +
                "Sell Cost : %d - " +
                "Buy Cost : %d \n", this.name, this.manaCost, this.coolDown, this.price / 2, this.price);
    }

    public void generalDo(Battle.Map map, Position generalPosition)
    {
        doForArea(map, generalPosition);
    }

    private void doForArea(Battle.Map map, Position positionInserted) throws Exception
    {
        switch (this.area)
        {
            case onCol:

                for (int i = 0; i < 5; i++)
                    if (map.getWarriorsOnMap()[i][positionInserted.col] != null)
                        checkingWarriorType(map.getWarriorsOnMap()[i][positionInserted.col]);
                break;

            case onRow:

                for (int i = 0; i < 9; i++)
                    if (map.getWarriorsOnMap()[positionInserted.row][i] != null)
                        checkingWarriorType(map.getWarriorsOnMap()[positionInserted.row][i]);

                break;

            case onOneTarget:

                checkingWarriorType(map.getWarriorsOnMap()[positionInserted.row][positionInserted.col]);

                break;

            case NearHero:

                Position heroPosition = BattleMenu.getInstance().getCurrentBattle()
                        .getBattleMap().getHeroPosition(this.ownerPlayer);

                if (Math.pow((positionInserted.col - heroPosition.col), 2) + Math.pow((positionInserted.row - heroPosition.row), 2) <= spellRange * spellRange * 2)
                    checkingWarriorType(map.getWarriorsOnMap()[positionInserted.row][positionInserted.col]);
                else
                    throw new InvalidPosition();

                break;

            case allWarrior:

                for (int i = 0; i < 5; i++)
                    for (int j = 0; j < 9; j++)
                        if (map.getWarriorsOnMap()[i][j] != null)
                            checkingWarriorType(map.getWarriorsOnMap()[i][j]);

                break;

            case randomWarrior:

                boolean hasException = true;
                while (hasException)
                {
                    Position position;
                    try
                    {
                        position = Position.getRandomCaptured();
                        checkingWarriorType(BattleMenu.getInstance().getCurrentBattle()
                                .getBattleMap().getWarriorsOnMap()[position.row][position.col]);
                        hasException = false;
                    }
                    catch (Exception e)
                    {
                        hasException = true;
                    }
                }

                break;
            case onSquare:
                for (int i = positionInserted.row; i < positionInserted.row + spellRange; i++)
                    for (int j = positionInserted.col; j < positionInserted.col + spellRange; j++)
                        if (map.getWarriorsOnMap()[i][j] != null)
                            checkingWarriorType(map.getWarriorsOnMap()[i][j]);
                break;

            case onAround:

                break;
        }
    }

    private void checkingWarriorType(Warrior warrior) throws Exception
    {
        switch (targetType)
        {
            case onMinion:
                if (warrior instanceof Minion)
                    checkingFOE(warrior);
                else
                    throw new Exception();

                break;

            case onHero:
                if (warrior instanceof Hero)
                    checkingFOE(warrior);
                else
                    throw new Exception();

                break;

            case onMinionOrHero:
                checkingFOE(warrior);

                break;
        }
    }

    private void checkingFOE(Warrior warrior) throws Exception
    {
        switch (foe)
        {
            case friend:
                if (warrior.getOwnerPlayer().equals(this.ownerPlayer))
                    addAndDoOnce(warrior);
                else
                    throw new Exception();

                break;
            case enemy:
                if (!warrior.getOwnerPlayer().equals(this.ownerPlayer))
                    addAndDoOnce(warrior);
                else
                    throw new Exception();

                break;
            case enemyOrFriend:
                addAndDoOnce(warrior);
                break;
        }
    }

    private void addAndDoOnce(Warrior warrior)
    {
        addEffects(warrior);
        doOnce(warrior);
    }

    private void addEffects(Warrior warrior)
    {
        for (Effectable effect : this.effects)
            warrior.getEffectsOnWarrior().add(effect);
    }

    private void doOnce(Warrior warrior)
    {
        warrior.doEffect();
        effects.removeIf(effectable -> effectable.getTurnRemaining() == 0);
    }

//    private void doForArea(Battle.Map map, Warrior warrior)
//    {
//
//
//    }


//    public void doOnADP(Warrior warrior) // for onAttack onDefend Passive
//    {
//        addEffectsAndDoOnce(warrior);
//    }


//    public void initialSpell(Battle.Map map, Position position) throws InvalidAttack
//    {
//        Warrior warrior = map.getWarriorsOnMap()[position.row][position.col];
//        if (target == Target.CellEffect)
//        {
//            doForArea(map, position);
//            doForArea(map, position);
//        }
//        else if (target == Target.OnWarrior && warrior != null)
//        {
//            checkWarriorTarget(warrior);
//            doForArea(map, position);
//            addToWarrior(warrior);
//        }
//        else
//        {
//            // invalid attack
//        }
//    }

//    public void initialSpell()
//    {
//        if (type == Type.Usable)
//        {
//
//        }
//        else if (type == Type.Collectible)
//        {
//            checkKindOfCollectible();
//        }
//    }

//    private void checkKindOfCollectible()
//    {
//        if (spellTypes.contains(SpellType.randomWarrior))
//        {
//            checkEnemyOrFriend();
//        }
//        else if (spellTypes.contains(SpellType.changeMana))
//        {
//            effects.get(0).doEffect(Battle.getAIPlayer().getRandomWarrior(this.ownerPlayer, SpellType.onFriend));
//        }
//    }

//    private void checkEnemyOrFriend()
//    {
//        if (spellTypes.contains(SpellType.friend))
//        {
//            doEffectOnRandomWarrior(SpellType.friend);
//        }
//        else if (spellTypes.contains(SpellType.enemy))
//        {
//            doEffectOnRandomWarrior(SpellType.enemy);
//        }
//    }
//    private void doForArea(Battle.Map map, Position position)
//    {
//        for (int i = 0; i < spellRange; i++)
//            for (int j = 0; j < spellRange; j++)
//            {
//                Position newPosition = new Position(position.row + i, position.col + j);
//                map.getSpellsAndCollectibleOnMap()[newPosition.row][newPosition.col] = new Spell(this);
//            }
//    }

    private void addToWarrior(Warrior warrior)
    {
        for (Effectable effect : this.effects)
            warrior.getEffectsOnWarrior().add(effect.clone());
    }

    public int getManaCost()
    {
        return manaCost;
    }

    public int getCoolDown()
    {
        return coolDown;
    }

    public int getCoolDownRemaining()
    {
        return coolDownRemaining;
    }

    public void decreaseCoolDownRemaining()
    {
        coolDownRemaining--;
    }

    public void setCoolDownRemaining(int coolDownRemaining)
    {
        this.coolDownRemaining = coolDownRemaining;
    }


    public int getSpellRange()
    {
        return spellRange;
    }

    public void setSpellRange(int spellRange)
    {
        this.spellRange = spellRange;
    }

    public ActiveTime getActiveTime()
    {
        return activeTime;
    }

    public Type getType()
    {
        return type;
    }

    public ArrayList<Effectable> getEffects()
    {
        return effects;
    }

    public void setEffects(ArrayList<Effectable> effects)
    {
        this.effects = effects;
    }

    public FOE getFoe()
    {
        return foe;
    }

    public TargetType getTargetType()
    {
        return targetType;
    }

    public Area getArea()
    {
        return area;
    }

}

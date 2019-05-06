package com.company.models.widget.cards.spells;

import com.company.controller.Exceptions.InvalidAttack;
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


    // fucking deBuffHoly shit

    public void generalDo(Battle.Map map, Position generalPosition)
    {
        doForArea(map, generalPosition);
    }


    private void doForArea(Battle.Map map, Position positionInserted)
    {
        switch (this.area)
        {
            case onCol:
                for (int i = 0; i < 5; i++)
                    if (map.getWarriorsOnMap()[i][positionInserted.col] != null)
                        checkingWarriorType(map.getWarriorsOnMap()[i][positionInserted.col]);

            case onRow:
                for (int i = 0; i < 9; i++)
                    if (map.getWarriorsOnMap()[positionInserted.row][i] != null)
                        checkingWarriorType(map.getWarriorsOnMap()[positionInserted.row][i]);

            case onOneTarget:
                checkingWarriorType(map.getWarriorsOnMap()[positionInserted.row][positionInserted.col]);

            case NearHero:
                int heroRow = 0;
                int heroCol = 0;
                for (int i = 0; i < 5; i++)
                    for (int j = 0; j < 9; j++)
                        if (map.getWarriorsOnMap()[i][j] instanceof Hero && map.getWarriorsOnMap()[i][j].getOwnerPlayer() == this.getOwnerPlayer())
                        {
                            heroRow = i;
                            heroCol = j;
                            break;
                        }
                if (Math.pow((positionInserted.col - heroCol), 2) + Math.pow((positionInserted.row - heroRow), 2) <= 2)
                {
                    checkingWarriorType(map.getWarriorsOnMap()[positionInserted.row][positionInserted.col]);
                }

            case allWarrior:
                for (int i = 0; i < 5; i++)
                    for (int j = 0; j < 9; j++)
                        if (map.getWarriorsOnMap()[i][j] != null)
                            checkingWarriorType(map.getWarriorsOnMap()[i][j]);

            case randomWarrior:


            case onSquare:
                for (int i = positionInserted.row; i < positionInserted.row + spellRange; i++)
                    for (int j = positionInserted.col; j < positionInserted.col + spellRange; j++)
                        if (map.getWarriorsOnMap()[i][j] != null)
                            checkingWarriorType(map.getWarriorsOnMap()[i][j]);

            case onAround:
        }
    }

    public void checkingWarriorType(Warrior warrior)
    {
        switch (targetType)
        {
            case onMinion:
                if (warrior instanceof Minion)
                {
                    checkingFOE(warrior);
                }
            case onHero:
                if (warrior instanceof Hero)
                {
                    checkingFOE(warrior);
                }
            case onMinionOrHero:
                checkingFOE(warrior);
        }
    }

    private void checkingFOE(Warrior warrior)
    {
        switch (foe)
        {
            case friend:
                if (warrior.getOwnerPlayer().equals(this.ownerPlayer))
                    addAndDoOnce(warrior);

            case enemy:
                if (!warrior.getOwnerPlayer().equals(this.ownerPlayer))
                    addAndDoOnce(warrior);

            case enemyOrFriend:
                addAndDoOnce(warrior);
        }
    }

    public void addAndDoOnce(Warrior warrior)
    {
        addEffects(warrior);
        doOnce(warrior);
    }


    public void addEffects(Warrior warrior)
    {
        for (Effectable effect : this.effects)
            warrior.getEffectsOnWarrior().add(effect);
    }

    public void doOnce(Warrior warrior)
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

    public void initialSpell()
    {
        if (type == Type.Usable)
        {

        }
        else if (type == Type.Collectible)
        {
            checkKindOfCollectible();
        }
    }

    private void checkKindOfCollectible()
    {
        if (spellTypes.contains(SpellType.randomWarrior))
        {
            checkEnemyOrFriend();
        }
        else if (spellTypes.contains(SpellType.changeMana))
        {
            effects.get(0).doEffect(Battle.getInstance().getRandomWarrior(this.ownerPlayer, SpellType.onFriend));
        }
    }

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

    private void doEffectOnRandomWarrior(SpellType spellType)
    {
        Warrior warrior = Battle.getInstance().getRandomWarrior(this.ownerPlayer, spellType);
        addEffectsAndDoOnce(warrior);
        addToWarrior(warrior);

    }
//
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

    private void checkWarriorTarget(Warrior warrior) throws InvalidAttack
    {
        if (getSpellTypes().contains(SpellType.onMinion))
            if (!(warrior instanceof Minion))
                throw new InvalidAttack();

        if (getSpellTypes().contains(SpellType.onHero))
            if (!(warrior instanceof Hero))
                throw new InvalidAttack();
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

    public void setSpellTypes(ArrayList<SpellType> spellTypes)
    {
        this.spellTypes = spellTypes;
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
}

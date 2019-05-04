package com.company.models.widget.cards.spells;

import com.company.controller.Exceptions.InvalidAttack;
import com.company.models.Position;
import com.company.models.battle.Battle;
import com.company.models.widget.cards.Card;
import com.company.models.widget.cards.Warriors.Hero;
import com.company.models.widget.cards.Warriors.Minion;
import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.cards.spells.effects.Effectable;
import com.company.models.widget.cards.spells.effects.HolyEffect;

import java.util.ArrayList;
import java.util.Collections;

public class Spell extends Card
{
    protected int manaCost;
    protected int coolDown;
    protected int coolDownRemaining;

    private int spellRange;
    private ArrayList<SpellType> spellTypes = new ArrayList<>();
    private SpellKind spellKind;
    private SpellActiveTime spellActiveTime;
    private SpellTarget spellTarget;
    private ArrayList<Effectable> effects;

    public Spell(SpellTarget spellTarget, SpellActiveTime spellActiveTime, SpellKind spellKind, String name, int price, int manaCost, int coolDown, int spellRange, Effectable... effectables)
    {
        super(name, price);
        Collections.addAll(effects, effectables);
        this.spellTarget = spellTarget;
        this.spellActiveTime = spellActiveTime;
        this.manaCost = manaCost;
        this.coolDown = coolDown;
        this.spellRange = spellRange;
        this.spellKind = spellKind;
    }

    public Spell(Spell spell)
    {
        this(spell.spellTarget, spell.spellActiveTime, spell.spellKind, spell.name, spell.price, spell.manaCost, spell.coolDown, spell.spellRange, (Effectable) null);
        ArrayList<Effectable> copiedEffects = new ArrayList<>();
        for (Effectable effect : spell.effects)
            copiedEffects.add(effect.clone());
        this.coolDownRemaining = coolDown;

        this.effects = copiedEffects;
        this.spellTypes = spell.spellTypes;
    }

    public void addSpellTypes(SpellType... spellTypes)
    {
        for (SpellType spellType : spellTypes)
            this.getSpellTypes().add(spellType);
    }

    public ArrayList<SpellType> getSpellTypes()
    {
        return spellTypes;
    }

    @Override
    public String toShow()
    {
        return String.format
                ("(Spell) - Name : %s – MP : %d - CoolDown : %d - Sell Cost : %d - Buy Cost : %d \n", this.name, this.manaCost, this.coolDown, this.price / 2, this.price);
    }

    public void initialSpell(Battle.Map map, Position position) throws InvalidAttack
    {
        Warrior warrior = map.getWarriorsOnMap()[position.row][position.col];
        if (spellTarget == SpellTarget.CellEffect)
        {
            doEffectOnArea(map, position);
            addEffectsToMap(map, position);
        } else if (spellTarget == SpellTarget.OnWarrior && warrior != null)
        {
            checkWarriorTarget(warrior);
            doEffectOnArea(map, position);
            addToWarrior(warrior);
        } else
        {
            // invalid attack
        }
    }

    public void initialSpell()
    {
        if (spellKind == SpellKind.Usable)
        {

        } else if (spellKind == SpellKind.Collectible)
        {
            checkKindOfCollectible();
        }
    }

    private void checkKindOfCollectible(){
        if (spellTypes.contains(SpellType.randomWarrior))
        {
            checkEnemyOrFriend();
        }
        else if (spellTypes.contains(SpellType.changeMana)){
            effects.get(0).doEffect(Battle.getInstance().getRandomWarrior(this.ownerPlayer,SpellType.onFriend),null);
        }
    }

    private void checkEnemyOrFriend(){
        if (spellTypes.contains(SpellType.onFriend)){
            doEffectOnRandomWarrior(SpellType.onFriend);
        }
        else if (spellTypes.contains(SpellType.onEnemy)){
            doEffectOnRandomWarrior(SpellType.onEnemy);
        }
    }

    private void doEffectOnRandomWarrior(SpellType spellType){
        Warrior warrior = Battle.getInstance().getRandomWarrior(this.ownerPlayer,spellType);
        doEffectForFirst(warrior);
        addToWarrior(warrior);

    }

    private void addEffectsToMap(Battle.Map map, Position position)
    {
        for (int i = 0; i < spellRange; i++)
            for (int j = 0; j < spellRange; j++)
            {
                Position newPosition = new Position(position.row + i, position.col + j);
                map.getSpellsAndCollectibleOnMap()[newPosition.row][newPosition.col] = new Spell(this);
            }
    }

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

    private void doEffectOnArea(Battle.Map map, Position positionInserted)
    {

        if (this.spellTypes.contains(SpellType.onCol))
        {
            for (int i = 0; i < 5; i++)
            {
                Warrior warriorOnThisPosition = map.getWarriorsOnMap()[i][positionInserted.col];
                if (warriorOnThisPosition != null)
                    doEffectForFirst(warriorOnThisPosition);
            }
        } else if (this.spellTypes.contains(SpellType.onRow))
        {
            for (int i = 0; i < 9; i++)
            {
                Warrior warriorOnThisPosition = map.getWarriorsOnMap()[positionInserted.row][i];
                if (warriorOnThisPosition != null)
                    doEffectForFirst(warriorOnThisPosition);
            }
        } else if (this.spellTypes.contains(SpellType.onTarget))
        {
            doEffectForFirst(map.getWarriorsOnMap()[positionInserted.row][positionInserted.col]);
        } else if (this.spellTypes.contains(SpellType.onSquare))
        {
            for (int i = positionInserted.row; i < positionInserted.row + spellRange; i++)
            {
                for (int j = positionInserted.col; j < positionInserted.col + spellRange; j++)
                {
                    Warrior warrior = map.getWarriorsOnMap()[i][j];
                    doEffectForFirst(warrior);
                }
            }

        } else if (this.spellTypes.contains(SpellType.onAround))
        {


        } else if (this.spellTypes.contains(SpellType.allWarrior))
        {
            for (int i = 0; i < 5; i++)
                for (int j = 0; j < 9; j++)
                {
                    if (map.getWarriorsOnMap()[i][j] != null)
                        doEffectForFirst(map.getWarriorsOnMap()[i][j]);
                }


        } else if (this.spellTypes.contains(SpellType.NearHero))
        {
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
                doEffectForFirst(map.getWarriorsOnMap()[positionInserted.row][positionInserted.col]);
            }
        }

    }

    private void doEffectForFirst(Warrior warrior)
    {
        for (Effectable effect : this.effects)
        {
            if (!(effect instanceof HolyEffect))
            {
                if (this.spellTypes.contains(SpellType.onFriend) && this.ownerPlayer.equals(warrior.getOwnerPlayer()))
                    effect.doEffect(warrior, SpellType.onFriend);
                if (this.spellTypes.contains(SpellType.onEnemy) && !this.ownerPlayer.equals(warrior.getOwnerPlayer()))
                    effect.doEffect(warrior, SpellType.onEnemy);
            }
        }

        effects.removeIf(effectable -> effectable.getTurnRemaining() == 0);

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

    public SpellActiveTime getSpellActiveTime()
    {
        return spellActiveTime;
    }

    public SpellKind getSpellKind()
    {
        return spellKind;
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

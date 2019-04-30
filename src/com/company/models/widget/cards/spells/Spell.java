package com.company.models.widget.cards.spells;

import com.company.models.Position;
import com.company.models.battle.Battle;
import com.company.models.widget.cards.Card;

public class Spell extends Card
{
    protected int manaCost;
    protected int coolDown;
    protected int affectTurnNumber;
    protected int coolDownRemaining;
    protected int affectTurnNumberRemaining;

    private int spellRange;
    private int changeAttackPoint;
    private int changeHealthPoint;
    protected boolean isUsable;
    protected SpecialSpellKind SpecialSpellKind;

    public Spell(int manaCost, int coolDown, int affectTurnNumber, int spellRange, int changeAttackPoint, int changeHealthPoint, SpecialSpellKind spellKind)
    {
        this.manaCost = manaCost;
        this.coolDown = coolDown;
        this.coolDownRemaining = 0;
        this.affectTurnNumber = affectTurnNumber;
        this.affectTurnNumberRemaining = 0;
        this.spellRange = spellRange;
        this.changeAttackPoint = changeAttackPoint;
        this.changeHealthPoint = changeHealthPoint;
    }

    @Override
    public String toShow()
    {
        return String.format
                ("(Spell) - Name : %s – MP : %d - CoolDown : %d \n", this.name, this.manaCost, this.coolDown);
    }

    public void doEffectAction(Battle.Map map, Position position)
    {






    }

    public void doSpell(){

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

    public void setCoolDownRemaining(int coolDownRemaining)
    {
        this.coolDownRemaining = coolDownRemaining;
    }

    public void decreaseCoolDownRemaining()
    {
        if (coolDownRemaining >= 1)
            coolDownRemaining -= 1;
    }

    public void setManaCost(int manaCost)
    {
        this.manaCost = manaCost;
    }

    public int getAffectTurnNumber()
    {
        return affectTurnNumber;
    }

    public void setAffectTurnNumber(int affectTurnNumber)
    {
        this.affectTurnNumber = affectTurnNumber;
    }

    public int getAffectTurnNumberRemaining()
    {
        return affectTurnNumberRemaining;
    }

    public void setAffectTurnNumberRemaining(int affectTurnNumberRemaining)
    {
        this.affectTurnNumberRemaining = affectTurnNumberRemaining;
    }

    public int getSpellRange()
    {
        return spellRange;
    }

    public void setSpellRange(int spellRange)
    {
        this.spellRange = spellRange;
    }

    public int getChangeAttackPoint()
    {
        return changeAttackPoint;
    }

    public void setChangeAttackPoint(int changeAttackPoint)
    {
        this.changeAttackPoint = changeAttackPoint;
    }

    public int getChangeHealthPoint()
    {
        return changeHealthPoint;
    }

    public void setChangeHealthPoint(int changeHealthPoint)
    {
        this.changeHealthPoint = changeHealthPoint;
    }

    public void setCoolDown(int coolDown)
    {
        this.coolDown = coolDown;
    }

    public boolean isUsable()
    {
        return isUsable;
    }

    public void setUsable(boolean usable)
    {
        isUsable = usable;
    }

    public com.company.models.widget.cards.spells.SpecialSpellKind getSpecialSpellKind()
    {
        return SpecialSpellKind;
    }

    public void setSpecialSpellKind(com.company.models.widget.cards.spells.SpecialSpellKind specialSpellKind)
    {
        SpecialSpellKind = specialSpellKind;
    }
}

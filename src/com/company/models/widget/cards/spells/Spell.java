package com.company.models.widget.cards.spells;

import com.company.models.Position;
import com.company.models.battle.Battle;
import com.company.models.widget.Widget;
import com.company.models.widget.cards.Card;
import com.company.models.widget.cards.Warriors.Hero;
import com.company.models.widget.cards.Warriors.Minion;
import com.company.models.widget.cards.Warriors.Warrior;

import java.util.ArrayList;

public class Spell extends Card {
    protected int manaCost;
    protected int coolDown;
    protected int affectPoisonTurnNumber;
    protected int affectStunTurnNumber;
    protected int affectDisarmTurnNumber;
    protected int coolDownRemaining;
    protected int affectTurnNumberRemaining;

    private int spellRange;
    private int changeAttackPoint;
    private int changeHealthPoint;
    private ArrayList<SpellType> spellTypes = new ArrayList<>();


    public Spell(String name, int price, int manaCost, int coolDown, int affectPoisonTurnNumber, int affectDisarmTurnNumber, int affectStunTurnNumber, int spellRange, int changeAttackPoint, int changeHealthPoint, SpellType... spellTypes) {
        super(name, price);
        addSpellTypes(spellTypes);
        this.manaCost = manaCost;
        this.name = name;
        this.coolDown = coolDown;
        this.coolDownRemaining = 0;
        this.affectPoisonTurnNumber = affectPoisonTurnNumber;
        this.affectDisarmTurnNumber = affectDisarmTurnNumber;
        this.affectStunTurnNumber = affectStunTurnNumber;
        this.affectTurnNumberRemaining = 0;
        this.spellRange = spellRange;
        this.changeAttackPoint = changeAttackPoint;
        this.changeHealthPoint = changeHealthPoint;
    }

    private void addSpellTypes(SpellType... spellTypes) {
        for (SpellType spellType : spellTypes) {
            this.getSpellTypes().add(spellType);
        }
    }

    public ArrayList<SpellType> getSpellTypes() {
        return spellTypes;
    }


    @Override
    public String toShow() {
        return String.format
                ("(Spell) - Name : %s – MP : %d - CoolDown : %d \n", this.name, this.manaCost, this.coolDown);
    }

    public void doEffectAction(Battle.Map map, Position position) {
        Widget widget = map.getWarriorsOnMap()[position.row][position.col];
        if (getSpellTypes().contains(SpellType.onFriend)) {
            if (widget != null) {
                if (widget instanceof Warrior && widget.getOwnerPlayer() == this.getOwnerPlayer()) {

                    if (getSpellTypes().contains(SpellType.onHero)) {
                        if (widget instanceof Hero) {

                            if (getSpellTypes().contains(SpellType.AttackPoint)) {
                                doAttackPoint(widget);
                            }
                            if (getSpellTypes().contains(SpellType.HealthPoint)) {
                                doHealthPoint(widget);
                            }
                        } else {
                            // invalid attack
                        }
                    } else if (getSpellTypes().contains(SpellType.onMinion)) {
                        if (widget instanceof Minion) {
                            if (getSpellTypes().contains(SpellType.healHeroByMinion)) {
                                doHealHeroByMinion(widget);
                            }
                            if (getSpellTypes().contains(SpellType.AttackPoint)) {
                                doAttackPoint(widget);
                            }
                            if (getSpellTypes().contains(SpellType.HealthPoint)) {
                                doHealthPoint(widget);
                            }
                        } else {
                            //invalid attack
                        }
                    } else if (getSpellTypes().contains(SpellType.onMinionOrHero)) {

                        if (getSpellTypes().contains(SpellType.AttackPoint)) {
                            doAttackPoint(widget);
                        }
                        if (getSpellTypes().contains(SpellType.HealthPoint)) {
                            doHealthPoint(widget);
                        }
                    }
                }
            } else {
                // invalid attack
            }

        } else if (getSpellTypes().contains(SpellType.onEnemy)) {
            if (widget != null) {
                if (widget instanceof Warrior && widget.getOwnerPlayer() != this.getOwnerPlayer()) {

                    if (getSpellTypes().contains(SpellType.onHero)) {
                        if (widget instanceof Hero) {

                            if (getSpellTypes().contains(SpellType.HealthPoint)) {
                                doHealthPoint(widget);
                            }
                        } else {
                            // invalid attack
                        }
                    } else if (getSpellTypes().contains(SpellType.onMinion)) {
                        if (widget instanceof Minion) {
                            if (getSpellTypes().contains(SpellType.onMinionNearHero)) {

                            }
                            if (getSpellTypes().contains(SpellType.AttackPoint)) {
                                doAttackPoint(widget);
                            }
                            if (getSpellTypes().contains(SpellType.HealthPoint)) {
                                doHealthPoint(widget);
                            }
                        } else {
                            //invalid attack
                        }

                    } else if (getSpellTypes().contains(SpellType.onMinionOrHero)) {
                        if (getSpellTypes().contains(SpellType.HealthPoint)) {
                            doHealHeroByMinion(widget);
                        }
                        if (getSpellTypes().contains(SpellType.AttackPoint)) {
                            doAttackPoint(widget);
                        }
                        if (getSpellTypes().contains(SpellType.onEnemiesInCol)) {
                            for (int i = 0; i < 5; i++) {
                                if (map.getWarriorsOnMap()[i][position.col] instanceof Warrior && map.getWarriorsOnMap()[i][position.col].getOwnerPlayer() != this.getOwnerPlayer())
                                    doAttackPoint(map.getWarriorsOnMap()[i][position.col]);
                            }
                        }
                    }
                } else {
                    // invalid attack
                }
            }
            else {
                // invalid attack
            }
        } else if (getSpellTypes().contains(SpellType.onEnemyOrFriend)) {
            

        } else if (getSpellTypes().contains(SpellType.allEnemy)) {

        } else if (getSpellTypes().contains(SpellType.cellEffect)) {
            //TODO
        }

    }

    private void doHealHeroByMinion(Widget widget) {

        //TODO
    }

    private void doHealthPoint(Widget widget) {

        ((Warrior) widget).changeHealth(this.getChangeHealthPoint());
    }

    private void doAttackPoint(Widget widget) {

        ((Warrior) widget).changeHealth(this.getChangeAttackPoint());

    }

    public void doSpell() {

    }

    public int getManaCost() {
        return manaCost;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public int getCoolDownRemaining() {
        return coolDownRemaining;
    }

    public void setCoolDownRemaining(int coolDownRemaining) {
        this.coolDownRemaining = coolDownRemaining;
    }

    public void decreaseCoolDownRemaining() {
        if (coolDownRemaining >= 1)
            coolDownRemaining -= 1;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }


    public int getAffectTurnNumberRemaining() {
        return affectTurnNumberRemaining;
    }

    public void setAffectTurnNumberRemaining(int affectTurnNumberRemaining) {
        this.affectTurnNumberRemaining = affectTurnNumberRemaining;
    }

    public int getSpellRange() {
        return spellRange;
    }

    public void setSpellRange(int spellRange) {
        this.spellRange = spellRange;
    }

    public int getChangeAttackPoint() {
        return changeAttackPoint;
    }

    public void setChangeAttackPoint(int changeAttackPoint) {
        this.changeAttackPoint = changeAttackPoint;
    }

    public int getChangeHealthPoint() {
        return changeHealthPoint;
    }

    public void setChangeHealthPoint(int changeHealthPoint) {
        this.changeHealthPoint = changeHealthPoint;
    }
}

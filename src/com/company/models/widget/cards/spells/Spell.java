package com.company.models.widget.cards.spells;

import com.company.models.Position;
import com.company.models.battle.Battle;
import com.company.models.widget.Widget;
import com.company.models.widget.cards.Card;
import com.company.models.widget.cards.Warriors.Hero;
import com.company.models.widget.cards.Warriors.Minion;
import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.cards.spells.effects.Effectable;

import java.util.ArrayList;
import java.util.Collections;

public class Spell extends Card {
    protected int manaCost;
    protected int coolDown;
    protected int coolDownRemaining;

    private int spellRange;
    private ArrayList<SpellType> spellTypes = new ArrayList<>();
    private SpellKind spellKind;
    private SpellActiveTime spellActiveTime;
    private SpellTarget spellTarget;
    private ArrayList<Effectable> effects;

    public Spell(SpellTarget spellTarget, SpellActiveTime spellActiveTime, SpellKind spellKind, String name, int price, int manaCost, int coolDown, int spellRange, Effectable... effectables) {
        super(name, price);
        Collections.addAll(effects, effectables);
        this.spellTarget = spellTarget;
        this.spellActiveTime = spellActiveTime;
        this.manaCost = manaCost;
        this.coolDown = coolDown;
        this.spellRange = spellRange;
        this.spellKind = spellKind;
    }

    public Spell(Spell spell) {

        this(spell.spellTarget, spell.spellActiveTime, spell.spellKind, spell.name, spell.price, spell.manaCost, spell.coolDown, spell.spellRange, null);
        ArrayList<Effectable> copiedEffects = new ArrayList<>();
        for (Effectable effect : spell.effects) {
            copiedEffects.add(effect.clone());
        }
        this.effects = copiedEffects;
        this.spellTypes = spell.spellTypes;
    }

    public void addSpellTypes(SpellType... spellTypes) {
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
                ("(Spell) - Name : %s – MP : %d - CoolDown : %d - Sell Cost : %d - Buy Cost : %d \n", this.name, this.manaCost, this.coolDown, this.price / 2, this.price);
    }

    public void initialSpell(Battle.Map map, Position position) {
        Warrior warrior = map.getWarriorsOnMap()[position.row][position.col];
        if (spellTarget == SpellTarget.CellEffect) {

            if (getSpellTypes().contains(SpellType.aruondAffect)) {


            } else {
                addEffectsToMap(map,position);
            }

        } else if (spellTarget == SpellTarget.OnWarrior) {
            if (warrior != null) {

                if (getSpellTypes().contains(SpellType.onFriend)) {
                    if (warrior.getOwnerPlayer() == this.getOwnerPlayer()) {
                        checkWarriorTarget(warrior);
                        doEffectOnArea(map,position);
                    }

                    else
                    //invalid attack
                } else if (getSpellTypes().contains(SpellType.onEnemy)) {
                    if (warrior.getOwnerPlayer() != this.getOwnerPlayer()) {
                        checkWarriorTarget(warrior);
                        doEffectOnArea(map,position);
                    }

                    else
                    //invalid attack
                } else if (getSpellTypes().contains(SpellType.onEnemyOrFriend)) {
                    checkWarriorTarget(warrior);
                    doEffectOnArea(map,position);
                }
            } else {
                // invalid attack
            }
        }


    }

    private void addEffectsToMap(Battle.Map map, Position position) {
        for (int i = 0; i < spellRange; i++) {
            for (int j = 0; j < spellRange; j++) {
                Position newPosition = new Position(position.row + i, position.col + j);
                map.getSpellsAndCollectibleOnMap()[newPosition.row][newPosition.col] = new Spell(this);
            }
        }
    }

    private void addToWarrior(Warrior warrior) {
        for (Effectable effect : this.effects) {
            warrior.getEffectsOnWarrior().add(effect.clone());
        }
    }

    private void checkWarriorTarget(Warrior warrior) {
        if (getSpellTypes().contains(SpellType.onMinion)) {
            if (!(warrior instanceof Minion)) {
            }
            // invalid attack
        } else if (getSpellTypes().contains(SpellType.onHero)) {
            if (!(warrior instanceof Hero)) {
            }
            //invalid attack
        }
    }

    public void doEffectOnArea(Battle.Map map,Position positionInserted) {
        if (this.spellTypes.contains(SpellType.onCol)) {

            // doForFirst
        } else if (this.spellTypes.contains(SpellType.onRow)) {


        } else if (this.spellTypes.contains(SpellType.onTarget)) {

            doEffectForFirst(map.getWarriorsOnMap()[positionInserted.row][positionInserted.col]);
        }
    }

    public void doEffectForFirst(Warrior warrior) {
        for (Effectable effect : this.effects) {
            effect.doEffect(warrior);
        }
        addToWarrior(warrior);
    }

    public void inActiveBuffs(Battle.Map map, Position position) {
        for (int i = position.row; i < position.row + this.getSpellRange(); i++) {
            for (int j = position.col; i < position.col + this.getSpellRange(); j++) {
                Warrior warrior = map.getWarriorsOnMap()[i][j];
                {
                    if (warrior.getOwnerPlayer() == this.getOwnerPlayer()) {
                        warrior.removeNegativeBuffs();
                    }
                    if (warrior.getOwnerPlayer() != this.getOwnerPlayer()) {
                        warrior.removePositiveBuffs();
                    }
                }
            }
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

    public void setSpellTypes(ArrayList<SpellType> spellTypes) {
        this.spellTypes = spellTypes;
    }

    public int getSpellRange() {
        return spellRange;
    }

    public void setSpellRange(int spellRange) {
        this.spellRange = spellRange;
    }
    public SpellActiveTime getSpellActiveTime() {
        return spellActiveTime;
    }

    public SpellKind getSpellKind() {
        return spellKind;
    }

    public ArrayList<Effectable> getEffects() {
        return effects;
    }

    public void setEffects(ArrayList<Effectable> effects) {
        this.effects = effects;
    }
}

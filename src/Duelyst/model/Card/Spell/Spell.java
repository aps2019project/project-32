package Duelyst.model.Card.Spell;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Card.Card;
import Duelyst.model.Cell;
import Duelyst.model.ErrorType;
import javafx.scene.image.Image;

import java.util.ArrayList;

public abstract class Spell extends Card {
    private static ArrayList<Spell> spells = new ArrayList<>();
    private int costToUse;

    static {
        new AllAttack();
        new AllDisarm();
        new AllPoison();
        new AllPower();
        new AreaDispel();
        new Dispel();
        new Empower();
        new FireBall();
        new GodStrength();
        new HealthWithProfit();
        new HellFire();
        new KingsGuard();
        new LightingBolt();
        new Madness();
        new PoisonLake();
        new PowerUp();
        new Sacrifice();
        new Shock();
        new TotalDisarm();
        new Weakening();
    }

    public Spell(String name, int costToUse, int costOfBuy) {
        this.setName(name);
        this.setCostOfBuy(costOfBuy);
        this.costToUse = costToUse;
        spells.add(this);
    }

    public Spell(Spell spell) {
        this.setName(spell.getName());
        this.setCostOfBuy(spell.getCostOfBuy());
        this.costToUse = spell.costToUse;
    }

    public abstract ErrorType castSpell(Battle battle, Cell cell, Player player);

    public static ArrayList<Spell> getSpells() {
        return spells;
    }

    public int getCostToUse() {
        return costToUse;
    }

    public abstract String getDesc();

    public String getType() {
        return "Spell";
    }

    public Spell duplicate() {
        return null;
    }

    @Override
    public Image getImage() {
        return cardImage;
    }
}


package client.models.widget.cards.Warriors;

//import client.controller.Menus.battlemenus.BattleMenuGUI;
//import client.models.Position;

import client.controller.AnimationGIFSPack;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import client.controller.battlemenus.BattleController;
import client.models.Position;
import client.models.widget.cards.Card;
import client.models.widget.cards.spells.ActiveTime;
import client.models.widget.cards.spells.Spell;
import client.models.widget.cards.spells.effects.Effectable;

import java.io.Serializable;
import java.util.ArrayList;

public class Warrior extends Card implements Serializable
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

    public Warrior(String name, int price, int health, int power, AttackType attackType, int attackRadius, Spell specialSpell, AnimationGIFSPack gifsPack)
    {
        super(name, price, gifsPack);
        this.health = health;
        this.power = power;
        this.attackType = attackType;
        this.attackRadius = attackRadius;
        this.specialSpell = specialSpell;
    }

    public Warrior()
    {
    }

    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        return new Warrior(name, price, health, power, attackType, attackRadius, ((Spell) specialSpell.clone()), gifsPack);
    }

    @Override
    public Pane toShow()
    {
        Pane pane = new Pane();
        ImageView imageView = getBackGround("file:Css\\pictures\\New folder (3)\\card_backgrounds\\neutral_prismatic_unit@2x.png");
        pane.getChildren().add(imageView);

        ImageView imageView1 = new ImageView(new Image(getCharacterImage()));
        imageView1.setPreserveRatio(true);
        imageView1.setX(20);
        imageView1.setFitHeight(130);
        pane.getChildren().add(imageView1);

        ArrayList<Label> labels = new ArrayList<>();

        labels.add(getLabel("power", 18, 28, 106, Color.YELLOW));

        labels.add(getLabel("health", 18, 108, 106, Color.RED));

        labels.add(getLabel("buy", 12, 40, 153, Color.WHITE));

        labels.add(getLabel("sell", 13, 90, 153, Color.WHITE));

        labels.add(getLabel("name", 15, 40, 137, Color.WHITE));

        if (this instanceof Minion)
            labels.add(getLabel("mana", 30, 126, 4, Color.LIGHTCYAN));

        labels.add(getLabel("attackType", 13, 15, 166, Color.WHITE));


        pane.getChildren().addAll(labels);


        return pane;

    }

    public String getCharacterImage()
    {
        return null;
    }

    public String getImageAddress()
    {
        return null;
    }

    private Label getLabel(String name, int fontSize, int x, int y, Color color)
    {
        String labelText = "";
        switch (name)
        {
            case "power":
            {
                labelText = String.valueOf(this.getPower());
                break;
            }
            case "health":
            {
                labelText = String.valueOf(this.getHealth());
                break;
            }
            case "name":
            {
                labelText = this.getName();
                break;
            }
            case "mana":
            {
                labelText = String.valueOf(((Minion) this).getManaCost());
                break;
            }
        }
        Label label = new Label(labelText);
        label.setFont(Font.font("Leelawadee UI", fontSize));
        label.setTextFill(color);
        label.relocate(x, y);

        return label;
    }

    public void doOnAttackSpells(Spell spell, Warrior defender) throws Exception
    {
        Position attackerPosition = BattleController.getInstance().getCurrentBattle().getBattleMap().getPosition(this);
        Position defenderPosition = BattleController.getInstance().getCurrentBattle().getBattleMap().getPosition(defender);

        if (spell.getActiveTime() == ActiveTime.onAttack)
        {
            switch (spell.getFoe())
            {
                case enemy:

                    switch (spell.getTargetType())
                    {
                        case onMinion:
                            if (defender instanceof Minion)
                                spell.generalDo(BattleController.getInstance().getCurrentBattle().getBattleMap(),
                                        defenderPosition);
                            break;
                        case onHero:
                            if (defender instanceof Hero)
                                spell.generalDo(BattleController.getInstance().getCurrentBattle().getBattleMap(),
                                        defenderPosition);

                        case onMinionOrHero:
                            spell.generalDo(BattleController.getInstance().getCurrentBattle().getBattleMap(),
                                    defenderPosition);
                    }

                case friend:

                    switch (spell.getTargetType())
                    {
                        case onMinion:
                            if (this instanceof Minion)
                                spell.generalDo(BattleController.getInstance().getCurrentBattle().getBattleMap(),
                                        attackerPosition);

                        case onHero:
                            spell.generalDo(BattleController.getInstance().getCurrentBattle().getBattleMap(),
                                    BattleController.getInstance().getCurrentBattle().getBattleMap().getHeroPosition
                                            (this.ownerPlayer));

                        case onMinionOrHero:
                            spell.generalDo(BattleController.getInstance().getCurrentBattle().getBattleMap(),
                                    attackerPosition);
                    }

            }
        }
    }

    public void doOnDefendSpells(Spell spell, Warrior defender) throws Exception
    {
        Position attackerPosition = BattleController.getInstance().getCurrentBattle().getBattleMap().getPosition(this);
        Position defenderPosition = BattleController.getInstance().getCurrentBattle().getBattleMap().getPosition(defender);

        if (spell.getActiveTime() == ActiveTime.onDefend)
        {
            switch (spell.getFoe())
            {
                case enemy:

                    switch (spell.getTargetType())
                    {
                        case onMinion:
                            if (this instanceof Minion)
                                spell.generalDo(BattleController.getInstance().getCurrentBattle().getBattleMap(),
                                        attackerPosition);
                            break;
                        case onHero:
                            if (this instanceof Hero)
                                spell.generalDo(BattleController.getInstance().getCurrentBattle().getBattleMap(),
                                        BattleController.getInstance().getCurrentBattle().getBattleMap().getHeroPosition
                                                (defender.ownerPlayer));

                            break;
                        case onMinionOrHero:
                            spell.generalDo(BattleController.getInstance().getCurrentBattle().getBattleMap(),
                                    attackerPosition);

                            break;
                    }

                case friend:

                    switch (spell.getTargetType())
                    {
                        case onMinion:
                            if (defender instanceof Minion)
                                spell.generalDo(BattleController.getInstance().getCurrentBattle().getBattleMap(),
                                        defenderPosition);

                        case onHero:
                            if (defender instanceof Hero)
                                spell.generalDo(BattleController.getInstance().getCurrentBattle().getBattleMap(),
                                        defenderPosition);

                        case onMinionOrHero:
                            spell.generalDo(BattleController.getInstance().getCurrentBattle().getBattleMap(),
                                    defenderPosition);

                    }

            }
        }
    }

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
        } catch (Exception ignored)
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
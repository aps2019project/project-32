package client.models.widget.cards.spells;

//import client.controller.Exceptions.InvalidPosition;
//import client.controller.Menus.battlemenus.BattleMenuGUI;
//import client.models.Position;
//import client.models.battle.Battle;

import client.controller.AnimationGIFSPack;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import client.controller.Exceptions.InvalidPosition;
import client.controller.battlemenus.BattleController;
import client.models.Position;
import client.models.battle.Battle;
import client.models.widget.cards.Card;
import client.models.widget.cards.Warriors.Hero;
import client.models.widget.cards.Warriors.Minion;
import client.models.widget.cards.Warriors.Warrior;
import client.models.widget.cards.spells.effects.Effectable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Spell extends Card implements Serializable
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
    private ArrayList<Effectable> effects = new ArrayList<>();

    public Spell(Area area, FOE foe, TargetType targetType, ActiveTime activeTime, Type type, String name, int price, int manaCost, int coolDown, int spellRange, AnimationGIFSPack gifsPack, Effectable... effectables)
    {
        super(name, price, gifsPack);
        this.area = area;
        this.foe = foe;
        this.targetType = targetType;
        this.activeTime = activeTime;
        this.manaCost = manaCost;
        this.coolDown = coolDown;
        this.spellRange = spellRange;
        this.type = type;
        if (effectables != null)
            effects.addAll(Arrays.asList(effectables));
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        ArrayList<Effectable> clonedEffects = new ArrayList<>();
        for (Effectable effect : effects)
            clonedEffects.add(((Effectable) effect.clone()));

        Spell spell = new Spell(area, foe, targetType, activeTime, type, name, price, manaCost, coolDown, spellRange, gifsPack,
                clonedEffects.toArray(new Effectable[0]));

        spell.ownerPlayer = this.ownerPlayer;

        return spell;
    }

    @Override
    public Pane toShow()
    {
        Pane pane = new Pane();
        ImageView imageView = getBackGround(getImageAddress());
        pane.getChildren().add(imageView);

        ImageView imageView1 = new ImageView(new Image(getCharacterImage()));
        imageView1.setPreserveRatio(true);
        imageView1.setX(22);
        imageView1.setY(10);
        imageView1.setFitHeight(110);
        pane.getChildren().add(imageView1);

        ArrayList<Label> labels = new ArrayList<>();

        labels.add(getLabel("buy", 13, 15, 153, Color.WHITE));

        labels.add(getLabel("sell", 13, 90, 153, Color.WHITE));

        labels.add(getLabel("name", 15, 15, 137, Color.WHITE));

        labels.add(getLabel("mana", 30, 126, 4, Color.LIGHTCYAN));


        pane.getChildren().addAll(labels);
        pane.setStyle("-fx-background-color: transparent");

        return pane;

    }

    private Label getLabel(String name, int fontSize, int x, int y, Color color)
    {
        String labelText = "";
        Label label = new Label();
        switch (name)
        {
            case "name":
            {
                labelText = this.getName();
                break;
            }
            case "mana":
            {
                labelText = String.valueOf(getManaCost());
                break;
            }
        }
        label.setText(labelText);
        label.setFont(Font.font(fontSize));
        label.setTextFill(color);
        label.relocate(x, y);
        return label;
    }

    private String getCharacterImage()
    {
        return "file:Css/pictures/New folder (3)/booster_pack_opening/booster_orb_set_4.png";
    }

    private String getImageAddress()
    {
        return "file:Css/pictures/New folder (3)/card_backgrounds/neutral_prismatic_spell.png";


    }

    public String getFeathers()
    {
        StringBuilder string = new StringBuilder(
                String.format(
                        "    Active Time : %s    \n" +
                                "    Area : %s    \n" +
                                "    Target : %s    \n" +
                                "    Target Type : %s    \n" +
                                "    Effects : \n"
                        , activeTime
                        , area
                        , foe
                        , targetType));

        for (Effectable effect : effects)
            string.append("      ").append(effect.toShow()).append("      ");

        return string.toString();
    }

    public void generalDo(Battle.Map map, Position generalPosition) throws Exception
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

                Position heroPosition = BattleController.getInstance().getCurrentBattle()
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
                        checkingWarriorType(BattleController.getInstance().getCurrentBattle()
                                .getBattleMap().getWarriorsOnMap()[position.row][position.col]);
                        hasException = false;
                    } catch (Exception e)
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
                {}

                break;
            case enemy:
                if (!warrior.getOwnerPlayer().equals(this.ownerPlayer))
                    addAndDoOnce(warrior);
                else
                {}

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

    private void addToWarrior(Warrior warrior) throws CloneNotSupportedException
    {
        for (Effectable effect : this.effects)
            warrior.getEffectsOnWarrior().add(((Effectable) effect.clone()));
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
        if (coolDownRemaining != 0)
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

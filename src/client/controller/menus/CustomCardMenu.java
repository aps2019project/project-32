package client.controller.menus;

import client.Client;
import client.controller.AnimationGIFSPack;
import client.controller.Controller;
import client.models.widget.cards.Warriors.AttackType;
import client.models.widget.cards.Warriors.Hero;
import client.models.widget.cards.Warriors.Minion;
import client.models.widget.cards.spells.*;
import client.models.widget.cards.spells.effects.AttackPoint;
import client.models.widget.cards.spells.effects.Effectable;
import client.models.widget.cards.spells.effects.HealthPoint;
import client.models.widget.cards.spells.effects.PON;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class CustomCardMenu extends AbstractMenu
{
    private static CustomCardMenu ourInstance = new CustomCardMenu();

    public static CustomCardMenu getInstance()
    {
        return ourInstance;
    }

    private Pane createBt = getGreenButton("Create Card");
    private Pane shopBt = getRedButton("Back");

    private TextField nameText = new TextField();
    private TextField priceText = new TextField();
    private TextField manaText = new TextField();
    private TextField healthText = new TextField();
    private TextField powerText = new TextField();
    private TextField RangeText = new TextField();
    private TextField turnNumberText = new TextField();
    private TextField numberOfChangesText = new TextField();
    private TextField numberOfCoolDownText = new TextField();
    private TextField numberOfSpecialSpellManaCost = new TextField();

    private ObservableList<String> cardKind = FXCollections.observableArrayList("Hero", "Minion", "Spell");
    private ObservableList<AttackType> attackType = FXCollections.observableArrayList(AttackType.Melee, AttackType.Ranged, AttackType.Hybrid);
    private ObservableList<Area> area = FXCollections.observableArrayList(Area.onOneTarget, Area.allWarrior, Area.onSquare, Area.randomWarrior, Area.noArea, Area.onAround, Area.onCol, Area.onRow);
    private ObservableList<FOE> foe = FXCollections.observableArrayList(FOE.friend, FOE.enemy, FOE.enemyOrFriend);
    private ObservableList<TargetType> target = FXCollections.observableArrayList(TargetType.onMinion, TargetType.onHero, TargetType.onMinionOrHero, TargetType.onPlayer);
    private ObservableList<ActiveTime> activeTime = FXCollections.observableArrayList(ActiveTime.onAttack, ActiveTime.onDefend, ActiveTime.onDeath);
    private ObservableList<String> effect = FXCollections.observableArrayList("Change Health", "Change Power");

    private ChoiceBox<String> cardKindBox = new ChoiceBox<>(cardKind);
    private ChoiceBox<AttackType> attackTypeBox = new ChoiceBox<>(attackType);
    private ChoiceBox<Area> areaBox = new ChoiceBox<>(area);
    private ChoiceBox<FOE> foeBox = new ChoiceBox<>(foe);
    private ChoiceBox<TargetType> targetBox = new ChoiceBox<>(target);
    private ChoiceBox<ActiveTime> activeTimeBox = new ChoiceBox<>(activeTime);
    private ChoiceBox<String> effectBox = new ChoiceBox<>(effect);

    VBox topVbox;

    BorderPane borderPane;

    private CustomCardMenu()
    {
        nameText.setMaxSize(150, 20);
        nameText.setPromptText("Enter Card Name");

        priceText.setMaxSize(150, 20);
        priceText.setPromptText("Enter Card Price");

        manaText.setMaxSize(150, 20);
        manaText.setPromptText("Enter Card Mana Cost");

        healthText.setMaxSize(150, 20);
        healthText.setPromptText("Enter Card Health");

        powerText.setMaxSize(150, 20);
        powerText.setPromptText("Enter Card Power");

        RangeText.setMaxSize(150, 20);
        RangeText.setPromptText("Enter Card Attack Range");

        turnNumberText.setMaxSize(150, 20);
        turnNumberText.setPromptText("Enter Card Turn Number Effect");

        numberOfChangesText.setMaxSize(200, 20);
        numberOfChangesText.setPromptText("Enter Card number Of Change Effects");

        numberOfCoolDownText.setMaxSize(200, 30);
        numberOfCoolDownText.setPromptText("Enter number Of Spell CoolDown");

        numberOfSpecialSpellManaCost.setMaxSize(200, 30);
        numberOfSpecialSpellManaCost.setPromptText("Enter number Of Spell Mana Cost");
        initializeMenuGUI();
    }

    @Override
    public void initializeMenuGUI()
    {
        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(50));
        borderPane.getChildren().add(getBackGround("bg5.jpg"));

        borderPane.setTop(getTop());
        borderPane.setCenter(null);
        borderPane.setBottom(getRight());
        BorderPane.setAlignment(borderPane.getTop(), Pos.CENTER);
        BorderPane.setAlignment(borderPane.getBottom(), Pos.CENTER);

        this.scene = new Scene(borderPane, Controller.getInstance().getWeight(), Controller.getInstance().getHeight());
        changeCursorImage("file:Css/pictures/New folder (3)/ui/mouse_auto.png");
    }

    private Parent getRight()
    {
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);

        setGlowEffects(createBt, shopBt);
        vBox.getChildren().addAll(createBt, shopBt);
        shopBt.setOnMouseClicked(mouseEvent ->
        {
            Controller.getInstance().getStage().setScene(ShopMenu.getInstance().getScene());
            ShopMenu.getInstance().getBorderPane().setLeft(ShopMenu.getInstance().getLeft());

            try
            {
                Client.getOutputStream().writeObject("outcustomcardmenu");
                Client.getOutputStream().flush();
                Client.getOutputStream().reset();

                Client.addCustomCardsToShop();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            resetAll();
        });
        return vBox;
    }

    private Parent getTop()
    {
        topVbox = new VBox(20);
        topVbox.setAlignment(Pos.CENTER);

        topVbox.getChildren().addAll(cardKindBox, nameText, priceText, manaText);
        cardKindBox.setOnAction(event ->
        {
            if (cardKindBox.getValue().equals("Minion"))
            {
                borderPane.setCenter(getMinionInfo());
                BorderPane.setAlignment(borderPane.getCenter(), Pos.CENTER);
                if (!topVbox.getChildren().contains(manaText))
                    topVbox.getChildren().add(manaText);
            }
            else if (cardKindBox.getValue().equals("Hero"))
            {
                borderPane.setCenter(getHeroInfo());
                BorderPane.setAlignment(borderPane.getCenter(), Pos.CENTER);
                if (topVbox.getChildren().contains(manaText))
                    topVbox.getChildren().remove(manaText);
            }
            else if (cardKindBox.getValue().equals("Spell"))
            {
                borderPane.setCenter(getSpellInfo());
                BorderPane.setAlignment(borderPane.getCenter(), Pos.CENTER);
                if (!topVbox.getChildren().contains(manaText))
                    topVbox.getChildren().add(manaText);
            }
        });

        return topVbox;
    }

    private Parent getSpellInfo()
    {
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(areaBox, foeBox, targetBox, effectBox, numberOfChangesText, turnNumberText);
        createBt.setOnMouseClicked(mouseEvent ->
        {
            if (spellSectionsAreFill() && ShopMenu.getInstance().searchCard(nameText.getText()) == null)
            {
                createSpell();
            }
        });
        return vBox;
    }

    private void createSpell()
    {
        Effectable effect;
        if (effectBox.getValue().equals("Change Health"))
            effect = new HealthPoint(Integer.parseInt(numberOfChangesText.getText()), Integer.parseInt(turnNumberText.getText()),
                    Integer.parseInt(numberOfChangesText.getText()) < 0 ? PON.Negative : PON.Positive, ActiveTime.perTurn);
        else
            effect = new AttackPoint(Integer.parseInt(numberOfChangesText.getText()), Integer.parseInt(turnNumberText.getText()),
                    Integer.parseInt(numberOfChangesText.getText()) < 0 ? PON.Negative : PON.Positive, ActiveTime.perTurn);

        Spell newSpell = new Spell(areaBox.getValue(), foeBox.getValue(), targetBox.getValue(), ActiveTime.onInsert, Type.NormalSpell,
                nameText.getText(), Integer.parseInt(priceText.getText()), Integer.parseInt(manaText.getText()), 0, 10, new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), effect
        );

        newSpell.setCardsNumber(3);

        try
        {
            Client.getOutputStream().writeObject(newSpell);
            Client.getOutputStream().flush();
            Client.getOutputStream().reset();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
//        ShopMenu.getInstance().getShopCards().add(newSpell);
        resetAll();
    }

    private boolean spellSectionsAreFill()
    {
        return !nameText.getText().equals("") && !priceText.getText().equals("") && !manaText.getText().equals("") &&
                areaBox.getValue() != null && foeBox.getValue() != null && targetBox.getValue() != null &&
                effectBox.getValue() != null && !numberOfChangesText.getText().equals("") && !turnNumberText.getText().equals("");
    }

    private boolean minionSectionsAreFill()
    {
        return !nameText.getText().equals("") && !priceText.getText().equals("") && !manaText.getText().equals("") &&
                !powerText.getText().equals("") && !healthText.getText().equals("") && !RangeText.getText().equals("") &&
                activeTimeBox.getValue() != null && attackTypeBox.getValue() != null && areaBox.getValue() != null && foeBox.getValue() != null && targetBox.getValue() != null &&
                effectBox.getValue() != null && !numberOfChangesText.getText().equals("") && !turnNumberText.getText().equals("");
    }

    private Parent getMinionInfo()
    {
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(powerText, healthText, attackTypeBox, RangeText, areaBox, foeBox, targetBox, activeTimeBox,
                effectBox, numberOfChangesText, turnNumberText);
        createBt.setOnMouseClicked(mouseEvent ->
        {
            if (minionSectionsAreFill() && ShopMenu.getInstance().searchCard(nameText.getText()) == null)
                createMinion();

        });
        return vBox;

    }

    private void createMinion()
    {
        Effectable effect;
        if (effectBox.getValue().equals("Change Health"))
            effect = new HealthPoint(Integer.parseInt(numberOfChangesText.getText()), Integer.parseInt(turnNumberText.getText()),
                    Integer.parseInt(numberOfChangesText.getText()) < 0 ? PON.Negative : PON.Positive, ActiveTime.perTurn);
        else
            effect = new AttackPoint(Integer.parseInt(numberOfChangesText.getText()), Integer.parseInt(turnNumberText.getText()),
                    Integer.parseInt(numberOfChangesText.getText()) < 0 ? PON.Negative : PON.Positive, ActiveTime.perTurn);
        Minion newMinion = new Minion(nameText.getText(), Integer.parseInt(manaText.getText()), Integer.parseInt(powerText.getText()),
                Integer.parseInt(healthText.getText()), Integer.parseInt(priceText.getText()), attackTypeBox.getValue(), Integer.parseInt(RangeText.getText()),
                new Spell(areaBox.getValue(), foeBox.getValue(), targetBox.getValue(), activeTimeBox.getValue(), Type.SpecialSpell,
                        nameText.getText(), 0, 0, 0, 1, new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), effect)
                , new AnimationGIFSPack("file:Css/pictures/New folder (3)/unit_gifs/boss_decepticlesword_breathing.gif"));

        newMinion.setCardsNumber(3);
        try
        {
            Client.getOutputStream().writeObject(newMinion);
            Client.getOutputStream().flush();
            Client.getOutputStream().reset();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
//        ShopMenu.getInstance().getShopCards().add(newMinion);
        resetAll();
    }

    private boolean heroSectionsAreFill()
    {

        return !nameText.getText().equals("") && !priceText.getText().equals("") &&
                !powerText.getText().equals("") && !healthText.getText().equals("") && !RangeText.getText().equals("") &&
                attackTypeBox.getValue() != null && areaBox.getValue() != null && foeBox.getValue() != null && targetBox.getValue() != null &&
                !numberOfCoolDownText.getText().equals("") && !numberOfSpecialSpellManaCost.getText().equals("") &&
                effectBox.getValue() != null && !numberOfChangesText.getText().equals("") && !turnNumberText.getText().equals("");
    }

    private Parent getHeroInfo()
    {
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(powerText, healthText, attackTypeBox, RangeText, areaBox, foeBox, targetBox, effectBox, numberOfChangesText, turnNumberText, numberOfCoolDownText, numberOfSpecialSpellManaCost);
        createBt.setOnMouseClicked(mouseEvent ->
        {
            if (heroSectionsAreFill() && ShopMenu.getInstance().searchCard(nameText.getText()) == null)
            {
                System.out.println("fd1");
                createHero();
            }
        });
        return vBox;
    }

    private void createHero()
    {
        Effectable effect;
        if (effectBox.getValue().equals("Change Health"))
            effect = new HealthPoint(Integer.parseInt(numberOfChangesText.getText()), Integer.parseInt(turnNumberText.getText()),
                    Integer.parseInt(numberOfChangesText.getText()) < 0 ? PON.Negative : PON.Positive, ActiveTime.perTurn);
        else
            effect = new AttackPoint(Integer.parseInt(numberOfChangesText.getText()), Integer.parseInt(turnNumberText.getText()),
                    Integer.parseInt(numberOfChangesText.getText()) < 0 ? PON.Negative : PON.Positive, ActiveTime.perTurn);
        Hero newHero = new Hero(nameText.getText(), Integer.parseInt(powerText.getText()),
                Integer.parseInt(healthText.getText()), Integer.parseInt(priceText.getText()), attackTypeBox.getValue(),
                Integer.parseInt(RangeText.getText()),
                new Spell(areaBox.getValue(), foeBox.getValue(), targetBox.getValue(), activeTimeBox.getValue(), Type.SpecialSpell,
                        nameText.getText(), 0, Integer.parseInt(numberOfSpecialSpellManaCost.getText()), Integer.parseInt(numberOfCoolDownText.getText()), 1, new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), effect)
                , new AnimationGIFSPack("file:Css/pictures/New folder (3)/unit_gifs/boss_orias_breathing.gif"));

        newHero.setCardsNumber(3);
        try
        {
            Client.getOutputStream().writeObject(newHero);
            Client.getOutputStream().flush();
            Client.getOutputStream().reset();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

//        ShopMenu.getInstance().getShopCards().add(newHero);
        resetAll();
    }

    public void resetAll()
    {
        nameText.setText("");
        priceText.setText("");
        manaText.setText("");
        powerText.setText("");
        healthText.setText("");
        RangeText.setText("");
        turnNumberText.setText("");
        numberOfCoolDownText.setText("");
        numberOfChangesText.setText("");
        numberOfSpecialSpellManaCost.setText("");
    }
}

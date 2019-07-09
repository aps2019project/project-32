package client.controller.menus;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import client.controller.Controller;
import client.controller.battlemenus.BattleController;
import client.models.widget.cards.Card;
import client.models.widget.cards.Warriors.Warrior;
import javafx.util.Duration;

import java.io.Serializable;

public class BattleMenuGUI extends AbstractMenu implements Serializable
{

    private final int SPECIAL_SPELL_HEIGHT = 240;
    private final int PLAYERS_AVATAR_HEIGHT = 190;
    private final int MANA_HEALTH_HEIGHT = 150;

    private static BattleMenuGUI ourInstance = new BattleMenuGUI();

    public static BattleMenuGUI getInstance()
    {
        return ourInstance;
    }

    private Pane mainPane = new Pane();
    private HBox mainHBox = new HBox(72);

    private VBox leftVBox = new VBox();
    private Pane firstPlayerPicture = new Pane();
    private Pane firstPlayerUserName;
    private StackPane firstPlayerHealth = new StackPane();
    private StackPane firstPlayerMana = new StackPane();
    private Pane specialSpellPane = new StackPane();
    private VBox specialSpellVBox = new VBox();

    private VBox centerVBox = new VBox();
    private GridPane mapGridPane = new GridPane();
    private HBox handCardsHbox = new HBox();
    private Pane nextCardPane = new Pane();
    private Pane selectedCard;
    private Pane selectedUnitPane;

    private VBox rightVBox = new VBox(10);
    private Pane secondPlayerPicture = new Pane();
    private Pane secondPlayerUserName;
    private Pane cardsInfoPane = new StackPane();
    private Pane blankPane = new Pane();
    private VBox rightButtonVbox = new VBox();
    private Pane endTurnPane = getBlueButton("Change Turn");
    private Pane determentPane = getRedButton("Determent");

    private BattleMenuGUI()
    {
        initializeMenuGUI();
    }

    @Override
    public void initializeMenuGUI()
    {
        initializePlayerGUI();

        setMyUserNameGUI(Controller.getInstance().getCurrentPlayer().getName());
        setOpponentUserNameGUI(BattleController.getInstance().getCurrentBattle().getOtherPlayer(Controller.getInstance().getCurrentPlayer()).getName());

        HBox healthManaVBox = initializeHealthMana();

        initializeSpecialSpellGUI();
        initializeNextCardGUI();

        leftVBox.setAlignment(Pos.CENTER);
        leftVBox.getChildren().addAll(firstPlayerPicture, firstPlayerUserName, healthManaVBox, specialSpellPane);

        initializeMapGridPane();

        initializeHandCards();

//        ashkBoos.getBattlePane().setOnMouseClicked(mouseEvent -> {
//
//            selectedCard = ashkBoos.getBattlePane();
//
//        });
//        ((Pane) handCardsHbox.getChildren().get(0)).getChildren().add(ashkBoos.getBattlePane());

        handCardsHbox.getChildren().add(nextCardPane);

        handCardsHbox.setPadding(new Insets(50, 0, 0, 0));
        handCardsHbox.setAlignment(Pos.CENTER);

        centerVBox.setPadding(new Insets(215, 0, 40, 15));

        mapGridPane.setStyle("-fx-vgap: 4;-fx-hgap: 4");

        centerVBox.getChildren().addAll(mapGridPane, handCardsHbox);

        initializeOpponentPic();

        blankPane.setMinSize(10, 100);
        blankPane.resize(10, 100);

        endTurnPane.setOnMouseClicked(event ->
        {
            try
            {
                BattleController.getInstance().getCurrentBattle().getTurnHandler().changeTurn();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });

        determentPane.setOnMouseClicked(event ->
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "You Lose Game");
            alert.show();

            Controller.getInstance().changeCurrentMenuTo(MainMenu.getInstance());
        });

        rightButtonVbox.getChildren().addAll(endTurnPane, determentPane);

        rightVBox.setAlignment(Pos.CENTER);
        cardsInfoPane.setMinHeight(120);
        cardsInfoPane.setPadding(new Insets(0, 0, 0, 40));
        rightVBox.getChildren().addAll(secondPlayerPicture, secondPlayerUserName, cardsInfoPane, blankPane, rightButtonVbox);

        endTurnPane.setPadding(new Insets(0, 30, 0, 0));
        determentPane.setPadding(new Insets(0, 30, 0, 0));
        mainHBox.getChildren().addAll(leftVBox, centerVBox, rightVBox);

        mainPane.setStyle("-fx-background-color: #914c00");
        mainPane.getChildren().add(getBackGround("file:Css\\pictures\\New folder (3)\\maps\\battlemap2_middleground.png"));
        mainPane.getChildren().add(mainHBox);

        addActionsToCards();
        setHeroPositions();

//        Hero diveSepid = new Hero("Blue Demon", 4, 50, 8000, AttackType.Melee, 1,
//                new Spell(Area.onOneTarget, FOE.friend, TargetType.onHero, ActiveTime.onClick,
//                        Type.SpecialSpell, "SP", 0, 1, 2, 10,
//                        "file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png", new AttackPoint(+4, 1, PON.Positive, ActiveTime.perTurn))
//                , "file:Css/pictures/New folder (3)/unit_gifs/boss_legion_breathing.gif");
//
//        //
//
//        mapGridPane.add(diveSepid.getBattlePane(), 2, 3);
//
//        diveSepid.getBattlePane().setOnMouseEntered(mouseEvent ->
//        {
//
//            diveSepid.getBattlePane().setEffect(new Glow(30));
//            cardsInfoPane.getChildren().add(diveSepid.toShow());
//
//        });
//
//        diveSepid.getBattlePane().setOnMouseExited(mouseEvent ->
//        {
//
//            diveSepid.getBattlePane().setEffect(new Glow(0));
//            cardsInfoPane.getChildren().remove(0);
//
//        });
//
//        diveSepid.getBattlePane().setOnMouseClicked(mouseEvent ->
//        {
//            selectedCard = diveSepid.getBattlePane();
//        });

        this.scene = new Scene(mainPane, Controller.getInstance().getWeight(), Controller.getInstance().getHeight());
    }

    private void addActionsToCards()
    {
        for (Card card : BattleController.getInstance().getCurrentBattle().getFirstPlayer().getMainDeck().getCards())
            addActionToCard(card);

        for (Card card : BattleController.getInstance().getCurrentBattle().getSecondPlayer().getMainDeck().getCards())
            addActionToCard(card);

        Warrior firstPlayerHero = BattleController.getInstance().getCurrentBattle().getFirstPlayer().getMainDeck().getHero();
        Warrior secondPlayerHero = BattleController.getInstance().getCurrentBattle().getSecondPlayer().getMainDeck().getHero();

        addActionToCard(firstPlayerHero);
        addActionToCard(secondPlayerHero);

    }

    private void addActionToCard(Card card)
    {

        card.getBattlePane().setOnMouseEntered(event ->
        {
            card.getBattlePane().setEffect(new Glow(30));
            cardsInfoPane.getChildren().add(card.toShow());
        });
        card.getBattlePane().setOnMouseExited(event ->
        {
            card.getBattlePane().setEffect(new Glow(0));
            cardsInfoPane.getChildren().remove(0);
        });
        card.getBattlePane().setOnMouseClicked(event ->
        {
            selectedCard = card.getBattlePane();
        });
    }

    private void setHeroPositions()
    {
        mapGridPane.add(BattleController.getInstance().getCurrentBattle().getFirstPlayer().getMainDeck().getHero().getBattlePane(), 0, 2);
        mapGridPane.add(BattleController.getInstance().getCurrentBattle().getSecondPlayer().getMainDeck().getHero().getBattlePane(), 8, 2);
    }

    private void initializeOpponentPic()
    {
        ImageView opponentImage = new ImageView(new Image("file:Css\\pictures\\New folder (3)\\ribbons\\f3_champion@2x.png"));
        opponentImage.setPreserveRatio(true);
        opponentImage.setFitHeight(PLAYERS_AVATAR_HEIGHT);
        secondPlayerPicture.getChildren().addAll(opponentImage);
    }

    private void initializeMapGridPane()
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                Pane unitPane = new Pane();

                setGlowEffects(.9, unitPane);

                unitPane.setStyle("-fx-border-style: dashed;-fx-border-color: #824000;-fx-border-width: 1.2");

                unitPane.setMinHeight(120);
                unitPane.setMaxWidth(120);
                unitPane.setMaxHeight(120);
                unitPane.setMinWidth(120);

                int finalI = i;
                int finalJ = j;
                unitPane.setOnMouseClicked(mouseEvent ->
                {
                    if (selectedCard != null)
                    {

                        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), selectedCard);
                        fadeTransition.setFromValue(0);
                        fadeTransition.setToValue(100);

                        fadeTransition.play();

                        mapGridPane.getChildren().remove(selectedCard);
                        mapGridPane.add(selectedCard, finalJ, finalI);

                        selectedCard = null;
                    }
                });

                mapGridPane.add(unitPane, j, i);
            }
        }

    }

    private void setMyUserNameGUI(String userName)
    {
        firstPlayerUserName = getImagePane("file:Css\\pictures\\New folder (3)\\ui\\gold_main_menu_container@2x.png", 60);

        Label userNameLabel = getLabel(userName, Color.GOLD, Font.font("Leelawadee UI", 18));
        userNameLabel.setAlignment(Pos.CENTER);
        firstPlayerUserName.getChildren().add(userNameLabel);
    }

    private void setOpponentUserNameGUI(String userName)
    {
        secondPlayerUserName = getImagePane("file:Css\\pictures\\New folder (3)\\ui\\gold_main_menu_container@2x.png", 60);

        Label userNameLabel = getLabel(userName, Color.GOLD, Font.font("Leelawadee UI", 18));
        userNameLabel.setAlignment(Pos.CENTER);
        secondPlayerUserName.getChildren().add(userNameLabel);
    }

    private void initializePlayerGUI()
    {
        firstPlayerPicture.getChildren().add(getImagePane("file:Css\\pictures\\New folder (3)\\ribbons\\f1_champion@2x.png", PLAYERS_AVATAR_HEIGHT));
    }

    private HBox initializeHealthMana()
    {
        firstPlayerHealth.getChildren().addAll(getImagePane("file:Css\\pictures\\New folder (3)\\ui\\bracket_enemy@2x.png", MANA_HEALTH_HEIGHT),
                getLabel(String.valueOf(Controller.getInstance().getCurrentPlayer().getMainDeck().getHero().getHealth()),
                        Color.RED, Font.font("Leelawadee UI", 18)));

        firstPlayerMana.getChildren().addAll(getImagePane("file:Css\\pictures\\New folder (3)\\ui\\bracket_friendly@2x.png", MANA_HEALTH_HEIGHT),
                getLabel("2", Color.ALICEBLUE, Font.font("Leelawadee UI", 18)));

        HBox manaHealthHBox = new HBox();
        manaHealthHBox.setAlignment(Pos.CENTER);
        manaHealthHBox.getChildren().addAll(firstPlayerHealth, firstPlayerMana);
        manaHealthHBox.setPadding(new Insets(40, 0, 0, 0));
        return manaHealthHBox;
    }

    private void initializeSpecialSpellGUI()
    {
        specialSpellPane.getChildren().add(Controller.getInstance().getCurrentPlayer().getMainDeck().getHero().getSpecialSpell().getBattlePane());
        specialSpellPane.setPadding(new Insets(100, 0, 0, 70));
    }

    private void initializeHandCards()
    {
        for (int i = 0; i < 5; i++)
        {
            Pane handCardPane = new Pane();
            handCardPane.setMinHeight(100);
            handCardPane.setMaxHeight(100);
            handCardPane.setMinWidth(100);
            handCardPane.setMaxWidth(100);
            handCardPane.setStyle("-fx-border-style : dashed;-fx-border-color: #824000");

            handCardsHbox.getChildren().add(handCardPane);
        }
    }

    private void initializeNextCardGUI()
    {
        nextCardPane.setMinHeight(100);
        nextCardPane.setMaxHeight(100);
        nextCardPane.setMinWidth(100);
        nextCardPane.setMaxWidth(100);
        nextCardPane.setStyle("-fx-border-style : solid; -fx-border-width : 4;-fx-border-color: #824000");
    }



}

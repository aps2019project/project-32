package client.controller;

import client.Client;
import client.controller.battlemenus.StartBattleMenu;
import client.controller.menus.AbstractMenu;
import client.controller.menus.GraveYardMenu;
import client.models.Position;
import client.models.widget.cards.Card;
import client.models.widget.cards.Warriors.Warrior;
import client.models.widget.cards.spells.Spell;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BattleMenu extends AbstractMenu
{
    private boolean handCardAndSpecialSelected;
    private boolean cardInGameSelected;

    private Pane mainPane = new Pane();
    private HBox mainHBox = new HBox(90);

    private VBox leftVbox = new VBox();
    private Pane myHeroPic = new Pane();
    private Label userNameLabel = new Label();
    private StackPane healthPane = new StackPane();
    Pane gameInfoPane = getImagePane("file:Css\\pictures\\New folder (3)\\ui\\gold_main_menu_container@2x.png", 60);

    public Pane getGameInfoPane()
    {
        return gameInfoPane;
    }

    private StackPane manaPane = new StackPane();
    private Label healthLabel = new Label();
    private Label manaLabel = new Label();
    private Pane specialSpellPane = new Pane();
    private Pane cardsInfoPane = new Pane();

    private VBox centerVBox = new VBox();
    private GridPane mapGridPane = new GridPane();
    private HBox handCardsHbox = new HBox();
    private Pane nextCardPane = new Pane();
    private Card selectedCard;

    private VBox rightVBox = new VBox(10);
    private Pane opponentHeroPic = new Pane();
    private Label opponentUsernameLabel = new Label();

    private VBox rightButtonVbox = new VBox();
    private Pane endTurnPane = new Pane();
    private Pane graveYardBTN;
    private Pane backBTN = getGreenButton("Save");
    private Pane determentPane = new Pane();

    private static int turnTimeLimit;

    public static void setTurnTimeLimit(int turnTimeLimit)
    {
        BattleMenu.turnTimeLimit = turnTimeLimit;
    }

    public static int getTurnTimeLimit()
    {
        return turnTimeLimit;
    }

    public BattleMenu()
    {
    }

    @Override
    public void initializeMenuGUI()
    {

        initializePlayerGUI();
        initializePlayerUserNameGUI();

        HBox manaHealthHBox = initializeHealthMana();

        initializeSpecialSpellGUI();
        initializeNextCardGUI();

        leftVbox.setStyle("-fx-background-color: rgba(105,52,5,0.25);-fx-background-radius: 15");
        rightVBox.setStyle("-fx-background-color: rgba(105,52,5,0.25);-fx-background-radius: 15");
        leftVbox.setSpacing(15);
        leftVbox.setAlignment(Pos.CENTER);
        leftVbox.getChildren().addAll(myHeroPic, userNameLabel, manaHealthHBox, cardsInfoPane, gameInfoPane);

        initializeMap();
        initializeHandCards();

        handCardsHbox.getChildren().add(nextCardPane);

        handCardsHbox.setPadding(new Insets(50, 0, 0, 0));
        handCardsHbox.setAlignment(Pos.CENTER);

        centerVBox.setPadding(new Insets(215, 0, 40, 15));

        centerVBox.getChildren().addAll(mapGridPane, handCardsHbox);

        ImageView opponentImage = new ImageView(new Image("file:Css\\pictures\\New folder (3)\\generals\\general_portrait_image_hex_f1-third.png"));
        opponentImage.setPreserveRatio(true);
        opponentImage.setFitHeight(270);
        opponentHeroPic.getChildren().addAll(opponentImage);

        endTurnPane = getYellowButton("End Turn");
        determentPane = getRedButton("Determent");
        graveYardBTN = getBlueButton("GraveYard");

        backBTN.setOnMouseClicked(event ->
        {
            Controller.getInstance().setBattleSaved(true);
            Controller.getInstance().setBattleMenu(this);
            Controller.getInstance().changeCurrentMenuTo(StartBattleMenu.getInstance());
        });

        graveYardBTN.setOnMouseClicked(event ->
        {
            GraveYardMenu graveYardMenu = GraveYardMenu.getInstance();
            try
            {
                graveYardMenu.initializeMenuGUI();
            }
            catch (IOException | InterruptedException e)
            {
                e.printStackTrace();
            }
            graveYardMenu.setBattleMenu(this);
            Controller.getInstance().getStage().setScene(graveYardMenu.getScene());

        });

        setGlowEffects(endTurnPane, determentPane, graveYardBTN, backBTN, gameInfoPane);
        endTurnPane.setOnMouseClicked(mouseEvent ->
        {
            try
            {
                BattleMenuLogic.getInstance().getCurrentBattle().getTurnHandler().changeTurn();

                initializeMap();
                initializeHandCards();
                initializeNextCardGUI();

                handCardsHbox.getChildren().add(nextCardPane);
                manaLabel.setText(String.valueOf(Controller.getInstance().getCurrentPlayer().getPlayerCurrentMana()));
                healthLabel.setText(String.valueOf(Controller.getInstance().getCurrentPlayer().getCopiedMainDeck().getHero().getHealth()));

                Timer timer = new Timer();

                TimerTask timerTask = new TimerTask()
                {
                    int i = turnTimeLimit;

                    @Override
                    public void run()
                    {
                        if (i == 0)
                        {
                            try
                            {
                                BattleMenuLogic.getInstance().getCurrentBattle().getTurnHandler().changeTurn();
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                            Platform.runLater(() ->
                            {
                                initializeMap();
                                initializeHandCards();
                                initializeNextCardGUI();
                                handCardsHbox.getChildren().add(nextCardPane);
                                manaLabel.setText(String.valueOf(Controller.getInstance().getCurrentPlayer().getPlayerCurrentMana()));
                                healthLabel.setText(String.valueOf(Controller.getInstance().getCurrentPlayer().getCopiedMainDeck().getHero().getHealth()));
                            });
                        }
                        i--;
                    }
                };

                timer.schedule(timerTask, 0, 1000);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                showWarningAlert(e.getMessage());
            }
        });
        determentPane.setOnMouseClicked(mouseEvent ->
        {
            BattleMenuLogic.getInstance().determent();
        });
        rightButtonVbox.getChildren().addAll(endTurnPane, graveYardBTN, determentPane, backBTN);

        rightVBox.setAlignment(Pos.CENTER);
        rightVBox.getChildren().addAll(opponentHeroPic, opponentUsernameLabel, rightButtonVbox);
        rightVBox.setSpacing(20);

        mainHBox.getChildren().addAll(leftVbox, centerVBox, rightVBox);
        mainHBox.setPadding(new Insets(0, 0, 0, 80));

        mainPane.getChildren().add(getBackGround("file:Css\\pictures\\New folder (3)\\maps\\battlemap2_middleground.png"));
        mainPane.setStyle("-fx-background-color: #c2771e");
        mainPane.getChildren().add(mainHBox);

        this.scene = new Scene(mainPane, Controller.getInstance().getWeight(), Controller.getInstance().getHeight());
        changeCursorImage("file:Css/pictures/New folder (3)/ui/mouse_auto.png");
        scene.setOnKeyPressed(key ->
        {
            if (key.getCode() == KeyCode.ESCAPE)
            {
                handCardAndSpecialSelected = false;
                cardInGameSelected = false;
                selectedCard = null;
            }
            else if (key.getCode() == KeyCode.F1)
            {
                for (Warrior[] warriors : BattleMenuLogic.getInstance().getCurrentBattle().getBattleMap().getWarriorsOnMap())
                    for (Warrior warrior : warriors)
                        if (warrior != null)
                            if (warrior.getOwnerPlayer().equals(Controller.getInstance().getCurrentPlayer()))
                                warrior.setHealth(warrior.getHealth() + 1);

            }
            else if (key.getCode() == KeyCode.F2)
            {
                for (Warrior[] warriors : BattleMenuLogic.getInstance().getCurrentBattle().getBattleMap().getWarriorsOnMap())
                    for (Warrior warrior : warriors)
                        if (warrior != null)
                            if (warrior.getOwnerPlayer().equals(Controller.getInstance().getCurrentPlayer()))
                                warrior.setPower(warrior.getPower() + 1);

            }
            else if (key.getCode() == KeyCode.F3)
                for (Warrior[] warriors : BattleMenuLogic.getInstance().getCurrentBattle().getBattleMap().getWarriorsOnMap())
                    for (Warrior warrior : warriors)
                        if (warrior != null)
                            if (!warrior.getOwnerPlayer().equals(Controller.getInstance().getCurrentPlayer()))
                                warrior.setPower(warrior.getPower() - 1);

        });
    }

    private void initializeHandCards()
    {
        handCardsHbox.getChildren().clear();
        for (Card handCard : Controller.getInstance().getCurrentPlayer().getPlayerHand().getHandCards())
        {

            Pane handCardPane = new Pane();
            handCardPane.setMinHeight(122);
            handCardPane.setMaxHeight(122);
            handCardPane.setMinWidth(122);
            handCardPane.setMaxWidth(122);
            handCardPane.setStyle("-fx-border-style : dashed;-fx-border-color: #371a00;-fx-border-width: 1.5");

            Pane battlePane = handCard.getBattlePane();
            handCardPane.getChildren().add(battlePane);
            handCardsHbox.getChildren().add(handCardPane);
            setMouseEnteredAndExited(handCard);
            battlePane.setOnMouseClicked(mouseEvent ->
            {
                if (!handCardAndSpecialSelected && !cardInGameSelected)
                {
                    handCardAndSpecialSelected = true;
                    selectedCard = handCard;
                }
            });
        }

    }

    private void initializePlayerUserNameGUI()
    {
        userNameLabel.setText(" " + Controller.getInstance().getCurrentPlayer().getName() + " ");
        userNameLabel.setStyle("-fx-border-style: solid;-fx-border-width: 2;-fx-border-color: #c8c800;-fx-background-color: rgba(0,0,0,0.6)");
        userNameLabel.setTextFill(Color.YELLOW);
        userNameLabel.setAlignment(Pos.CENTER);
        userNameLabel.setTextFill(Color.WHITE);
        userNameLabel.setFont(Font.font(20));
        opponentUsernameLabel.setText(" " + BattleMenuLogic.getInstance().getCurrentBattle().getSecondPlayer().getName() + " ");
        opponentUsernameLabel.setAlignment(Pos.CENTER);
        opponentUsernameLabel.setTextFill(Color.YELLOW);
        opponentUsernameLabel.setFont(Font.font(20));
        opponentUsernameLabel.setStyle("-fx-border-style: solid;-fx-border-width: 2;-fx-border-color: rgb(200,200,0);-fx-background-color: rgba(0,0,0,0.6)");
    }

    private void initializePlayerGUI()
    {
        ImageView myHeroPicture = new ImageView(new Image("file:Css\\pictures\\New folder (3)\\generals\\general_portrait_image_hex_f1-third.png"));
        myHeroPicture.setPreserveRatio(true);
        myHeroPicture.setFitHeight(270);
        myHeroPic.getChildren().add(myHeroPicture);
    }

    private HBox initializeHealthMana()
    {
        ImageView healthImage = new ImageView(new Image("file:Css\\pictures\\New folder (3)\\ui\\bracket_enemy@2x.png"));
        healthImage.setPreserveRatio(true);
        healthImage.setFitHeight(200);
        healthImage.setRotate(healthImage.getRotate() + 180);

        healthLabel.setText(String.valueOf(Controller.getInstance().getCurrentPlayer().getCopiedMainDeck().getHero().getHealth()));
        healthLabel.setFont(Font.font(20));
        healthLabel.setTextFill(Color.RED);
        healthPane.getChildren().addAll(healthImage, healthLabel);

        ImageView manaImage = new ImageView(new Image("file:Css\\pictures\\New folder (3)\\ui\\bracket_friendly@2x.png"));
        manaImage.setPreserveRatio(true);
        manaImage.setFitHeight(200);
        manaLabel.setText(String.valueOf(Controller.getInstance().getCurrentPlayer().getPlayerCurrentMana()));
        manaLabel.setFont(Font.font(20));
        manaLabel.setTextFill(Color.DODGERBLUE);
        manaPane.getChildren().addAll(manaImage, manaLabel);

        HBox manaHealthHBox = new HBox();
        manaHealthHBox.setAlignment(Pos.CENTER);
        manaHealthHBox.getChildren().addAll(healthPane, manaPane);
        return manaHealthHBox;
    }

    private void initializeSpecialSpellGUI()
    {
        ImageView specialSpellImage = new ImageView(new Image("file:Css\\pictures\\New folder (3)\\ui\\dialogue_border.png"));
        specialSpellImage.setPreserveRatio(true);
        specialSpellImage.setFitHeight(100);
        Pane battlePane = Controller.getInstance().getCurrentPlayer().getCopiedMainDeck().getHero().getSpecialSpell().getBattlePane();
        StackPane stackPane = new StackPane(specialSpellImage, battlePane);
        setMouseEnteredAndExited(Controller.getInstance().getCurrentPlayer().getCopiedMainDeck().getHero().getSpecialSpell());
        specialSpellPane.getChildren().add(stackPane);
    }

    private void initializeNextCardGUI()
    {
        nextCardPane.getChildren().clear();
        nextCardPane.setMinHeight(122);
        nextCardPane.setMaxHeight(122);
        nextCardPane.setMinWidth(122);
        nextCardPane.setMaxWidth(122);
        nextCardPane.setStyle("-fx-border-style : solid; -fx-border-width : 2;-fx-border-color: #9b3300");
        Card card = Controller.getInstance().getCurrentPlayer().getPlayerHand().getNextCard();
        Pane battlePane = card.getBattlePane();
        nextCardPane.getChildren().add(battlePane);
        setMouseEnteredAndExited(card);

    }

    private void initializeMap()
    {
        mapGridPane.getChildren().clear();
        initializeBase();
        Warrior[][] warriorsOnMap = BattleMenuLogic.getInstance().getCurrentBattle().getBattleMap().getWarriorsOnMap();
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (warriorsOnMap[i][j] != null)
                {
                    Pane battlePane = warriorsOnMap[i][j].getBattlePane();
                    battlePane.setStyle("-fx-border-style: solid;-fx-border-width: 1;-fx-border-color: #371a00");

                    mapGridPane.add(battlePane, j, i);

                    int finalI = i;
                    int finalJ = j;
                    battlePane.setOnMouseClicked(mouseEvent ->
                    {
                        if (warriorsOnMap[finalI][finalJ].getOwnerPlayer().equals(Controller.getInstance().getCurrentPlayer()))
                        {
                            if (!handCardAndSpecialSelected && !cardInGameSelected)
                            {
                                cardInGameSelected = true;
                                selectedCard = warriorsOnMap[finalI][finalJ];
                            }
                        }
                        if (handCardAndSpecialSelected && selectedCard instanceof Spell)
                        {
                            try
                            {
                                BattleMenuLogic.getInstance().insertCard(selectedCard, new Position(finalI, finalJ));
                                String voice = "C:/Users/hp/Desktop/my own project/Css/pictures/New folder (3)/sfx/sfx_f1_oserix_attack_swing.m4a";
                                Media media = new Media(new File(voice).toURI().toString());
                                MediaPlayer mediaPlayer = new MediaPlayer(media);
                                System.out.println("aasfafasfa");
                                mediaPlayer.setAutoPlay(true);
                                mediaPlayer.play();
                                initializeMap();
                                handCardAndSpecialSelected = false;

                                handCardsHbox.getChildren().remove(selectedCard.getBattlePane());

                                selectedCard = null;
                                manaLabel.setText(String.valueOf(Controller.getInstance().getCurrentPlayer().getPlayerCurrentMana()));
                                healthLabel.setText(String.valueOf(Controller.getInstance().getCurrentPlayer().getCopiedMainDeck().getHero().getHealth()));
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                                showWarningAlert(e.getMessage());
                            }
                        }
                        if (cardInGameSelected && !warriorsOnMap[finalI][finalJ].getOwnerPlayer().equals(selectedCard.getOwnerPlayer()))
                        {
                            try
                            {
                                BattleMenuLogic.getInstance().attack(((Warrior) selectedCard), new Position(finalI, finalJ));

                                String voice = "C:/Users/hp/Desktop/my own project/Css/pictures/New folder (3)/sfx/sfx_f1_general_attack_swing.m4a";
                                Media media = new Media(new File(voice).toURI().toString());
                                MediaPlayer mediaPlayer = new MediaPlayer(media);
                                mediaPlayer.setAutoPlay(true);
                                mediaPlayer.play();

                                initializeMap();
                                cardInGameSelected = false;
                                selectedCard = null;
                                healthLabel.setText(String.valueOf(Controller.getInstance().getCurrentPlayer().getCopiedMainDeck().getHero().getHealth()));
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                                showWarningAlert(e.getMessage());
                            }
                        }
                    });
                    setMouseEnteredAndExited(warriorsOnMap[i][j]);
                }
            }
        }
    }

    private void initializeBase()
    {
        Spell[][] widgetOnMap = BattleMenuLogic.getInstance().getCurrentBattle().getBattleMap().getSpellsAndCollectibleOnMap();
        for (int i = 0; i < 5; i++)
        {
            for (int i1 = 0; i1 < 9; i1++)
            {
                Pane unitPane = new Pane();

                unitPane.setMinHeight(122);
                unitPane.setMaxWidth(122);
                unitPane.setMaxHeight(122);
                unitPane.setMinWidth(122);
                int finalI = i;
                int finalI1 = i1;
                if (widgetOnMap[i][i1] != null)
                {
                    Pane pane = widgetOnMap[i][i1].getBattlePane();
                    unitPane.getChildren().add(pane);
                    pane.setOnMouseClicked(mouseEvent ->
                    {
                        if (cardInGameSelected)
                        {
                            try
                            {
                                BattleMenuLogic.getInstance().moveCard(selectedCard, finalI, finalI1);

                                String voice = "C:/Users/hp/Desktop/my own project/Css/pictures/New folder (3)/sfx/sfx_f2_mage4winds_attack_swing.m4a";
                                Media media = new Media(new File(voice).toURI().toString());
                                MediaPlayer mediaPlayer = new MediaPlayer(media);
                                mediaPlayer.setAutoPlay(true);
                                mediaPlayer.play();
                                initializeMap();
                                selectedCard = null;
                                cardInGameSelected = false;
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                                showWarningAlert(e.getMessage());
                            }
                        }
                    });
                }
                unitPane.setOnMouseClicked(mouseEvent ->
                {
                    if (handCardAndSpecialSelected)
                    {
                        try
                        {
                            BattleMenuLogic.getInstance().insertCard(selectedCard, new Position(finalI, finalI1));

                            String voice = "C:/Users/hp/Desktop/my own project/Css/pictures/New folder (3)/sfx/sfx_f2_mage4winds_attack_swing.m4a";
                            Media media = new Media(new File(voice).toURI().toString());
                            MediaPlayer mediaPlayer = new MediaPlayer(media);
                            mediaPlayer.setAutoPlay(true);
                            mediaPlayer.play();

                            for (Node child : handCardsHbox.getChildren())
                                if (((Pane) child).getChildren().contains(selectedCard.getBattlePane()))
                                {
                                    ((Pane) child).getChildren().remove(selectedCard.getBattlePane());
                                    break;
                                }

                            initializeMap();
                            handCardAndSpecialSelected = false;

                            selectedCard = null;
                            manaLabel.setText(String.valueOf(Controller.getInstance().getCurrentPlayer().getPlayerCurrentMana()));
                            healthLabel.setText(String.valueOf(Controller.getInstance().getCurrentPlayer().getCopiedMainDeck().getHero().getHealth()));

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            showWarningAlert(e.getMessage());
                        }
                    }
                    else if (cardInGameSelected)
                    {
                        try
                        {
                            BattleMenuLogic.getInstance().moveCard(selectedCard, finalI, finalI1);

                            String voice = "C:/Users/hp/Desktop/my own project/Css/pictures/New folder (3)/sfx/sfx_f6_ancientgrove_death.m4a";
                            Media media = new Media(new File(voice).toURI().toString());
                            MediaPlayer mediaPlayer = new MediaPlayer(media);
                            mediaPlayer.setAutoPlay(true);
                            mediaPlayer.play();

                            initializeMap();
                            selectedCard = null;
                            cardInGameSelected = false;
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            showWarningAlert(e.getMessage());
                        }
                    }
                });
                mapGridPane.add(unitPane, i1, i);
            }
        }

    }

    private void setMouseEnteredAndExited(Card card)
    {
        if (!(card instanceof Spell) && !card.getOwnerPlayer().equals(Controller.getInstance().getCurrentPlayer()))
        {
            card.getBattlePane().setEffect(new Glow(60));
            card.getBattlePane().setOnMouseEntered(mouseEvent ->
            {
                cardsInfoPane = card.toShow();
                leftVbox.getChildren().add(4, cardsInfoPane);
                leftVbox.setAlignment(Pos.TOP_CENTER);
                cardsInfoPane.setStyle("-fx-background-color: #a6601c");

            });
            card.getBattlePane().setOnMouseExited(mouseEvent ->
            {
                leftVbox.getChildren().remove(cardsInfoPane);
            });
        }
        else
        {
            card.getBattlePane().setOnMouseEntered(mouseEvent ->
            {
                card.getBattlePane().setEffect(new Glow(30));
                cardsInfoPane = card.toShow();
                leftVbox.getChildren().add(4, cardsInfoPane);
                cardsInfoPane.setStyle("-fx-background-color: #a6601c");

            });
            card.getBattlePane().setOnMouseExited(mouseEvent ->
            {
                card.getBattlePane().setEffect(new Glow(0));
                leftVbox.getChildren().remove(cardsInfoPane);
            });
        }
    }

    private void showWarningAlert(String content)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(content);

        alert.showAndWait();
    }
}

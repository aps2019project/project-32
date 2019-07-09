package client.controller.battlemenus;

import client.controller.BattleMenu;
import client.controller.BattleMenuLogic;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import client.controller.Controller;
import client.controller.Exceptions.InvalidDeck;
import client.controller.menus.AbstractMenu;
import client.controller.menus.BattleMenuGUI;
import client.models.AIPlayer;
import client.models.battle.CollectFlagBattle;
import client.models.battle.DeadBattle;
import client.models.battle.KeepFlagBattle;

public class StoryModeMenu extends AbstractMenu
{
    private static StoryModeMenu ourInstance = new StoryModeMenu();

    public static StoryModeMenu getInstance()
    {
        return ourInstance;
    }

    private StoryModeMenu()
    {
        initializeMenuGUI();
    }

    private Pane deadMatchBTN = getBlueButton("Dead Match");
    private Pane collectFlagBTN = getBlueButton("Collect Flag");
    private Pane keepFlagBTN = getBlueButton("Keep Flag");
    private Pane backButton = getRedButton("Back");

    public String toShowMenu()
    {
        return "1.Step <1>(Dead Battle)\n2.Step <2>(Collect Flag Battle)\n3.Step <3>(Keep Flag Battle)\n4.Help\n5.Exit";
    }

    public void startStoryModeGame(int stepNumber) throws CloneNotSupportedException, InvalidDeck
    {

        switch (stepNumber)
        {
            case 1:
                AIPlayer.getInstance().setAI();
                DeadBattle.setBattle(Controller.getInstance().getCurrentPlayer(),
                        AIPlayer.getInstance().getAIPlayer(),
                        500);

                BattleController.getInstance().setCurrentBattle(DeadBattle.getInstance());
                Controller.getInstance().changeCurrentMenuTo(BattleMenuGUI.getInstance());
                break;
            case 2:
                AIPlayer.getInstance().setAI();
                CollectFlagBattle.setBattle(Controller.getInstance().getCurrentPlayer(),
                        AIPlayer.getInstance().getAIPlayer(),
                        1000, 7);

                BattleController.getInstance().setCurrentBattle(CollectFlagBattle.getInstance());
                Controller.getInstance().changeCurrentMenuTo(BattleMenuGUI.getInstance());
                break;
            case 3:
                AIPlayer.getInstance().setAI();
                KeepFlagBattle.setBattle(Controller.getInstance().getCurrentPlayer(),
                        AIPlayer.getInstance().getAIPlayer(),
                        1500);

                BattleController.getInstance().setCurrentBattle(KeepFlagBattle.getInstance());
                Controller.getInstance().changeCurrentMenuTo(BattleMenuGUI.getInstance());
                break;
        }
    }

    @Override
    public void initializeMenuGUI()
    {

        BorderPane borderPane = new BorderPane();

        borderPane.setStyle("-fx-background-color: #00abaa");

        borderPane.setPadding(new Insets(100));
        borderPane.getChildren().add(getBackGround("file:Css/pictures/New folder (3)/scenes/shimzar/midground.png"));

        borderPane.setRight(getRight());
        BorderPane.setAlignment(borderPane.getRight(), Pos.CENTER);

        this.scene = new Scene(borderPane, Controller.getInstance().getWeight(), Controller.getInstance().getHeight());
        changeCursorImage("file:Css/pictures/New folder (3)/ui/mouse_auto.png");

    }

    private Node getRight()
    {

        VBox vBox = new VBox(15);
        vBox.setAlignment(Pos.TOP_LEFT);

        deadMatchBTN.setOnMouseClicked(event ->
        {
            //            Controller.getInstance().changeCurrentMenuTo(StartBattleMenu.getInstance());

            try
            {
                AIPlayer.getInstance().setAI();
                DeadBattle.setBattle(Controller.getInstance().getCurrentPlayer(), AIPlayer.getInstance().getAIPlayer(), 1000);
                BattleMenuLogic.getInstance().setCurrentBattle(DeadBattle.getInstance());

                BattleMenu battleMenu = new BattleMenu();
                battleMenu.initializeMenuGUI();
                ((Label) battleMenu.getGameInfoPane().getChildren().get(1)).setText("dead battle");

                Controller.getInstance().getStage().setScene(battleMenu.getScene());

            }
            catch (CloneNotSupportedException e)
            {
                e.printStackTrace();
            }
            catch (InvalidDeck invalidDeck)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,"Invalid Deck");
                alert.show();
            }
        });

        collectFlagBTN.setOnMouseClicked(event ->
        {
            try
            {
                AIPlayer.getInstance().setAI();
                CollectFlagBattle.setBattle(Controller.getInstance().getCurrentPlayer(), AIPlayer.getInstance().getAIPlayer(),1000,7);
                BattleMenuLogic.getInstance().setCurrentBattle(CollectFlagBattle.getInstance());
                CollectFlagBattle.getInstance().addFlagsToMap();

                BattleMenu battleMenu = new BattleMenu();
                battleMenu.initializeMenuGUI();
                ((Label) battleMenu.getGameInfoPane().getChildren().get(1)).setText("collect flag");

                Controller.getInstance().getStage().setScene(battleMenu.getScene());
            }
            catch (CloneNotSupportedException e)
            {
                e.printStackTrace();
            }
            catch (InvalidDeck invalidDeck)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,"Invalid Deck");
                alert.show();
            }
        });

        keepFlagBTN.setOnMouseClicked(event ->
        {
            try
            {
                AIPlayer.getInstance().setAI();
                KeepFlagBattle.setBattle(Controller.getInstance().getCurrentPlayer(), AIPlayer.getInstance().getAIPlayer(),1000);
                BattleMenuLogic.getInstance().setCurrentBattle(KeepFlagBattle.getInstance());

                BattleMenu battleMenu = new BattleMenu();
                battleMenu.initializeMenuGUI();
                ((Label) battleMenu.getGameInfoPane().getChildren().get(1)).setText("keep flag");


                Controller.getInstance().getStage().setScene(battleMenu.getScene());
            }
            catch (CloneNotSupportedException e)
            {
                e.printStackTrace();
            }
            catch (InvalidDeck invalidDeck)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,"Invalid Deck");
                alert.show();
            }
        });

        backButton.setOnMouseClicked(event ->
        {
            Controller.getInstance().changeCurrentMenuTo(StartBattleMenu.getInstance());
        });

        setGlowEffects(deadMatchBTN, collectFlagBTN, keepFlagBTN, backButton);

        vBox.getChildren().addAll(deadMatchBTN, collectFlagBTN, keepFlagBTN, backButton);

        return vBox;
    }
}


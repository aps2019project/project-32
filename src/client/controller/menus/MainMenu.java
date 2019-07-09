package client.controller.menus;

import client.Client;
import client.controller.BattleMenu;
import client.controller.BattleMenuLogic;
import client.controller.Exceptions.InvalidDeck;
import client.models.AIPlayer;
import client.models.battle.CollectFlagBattle;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import client.controller.Controller;
import client.controller.Exceptions.RepeatedDeckName;
import client.controller.battlemenus.StartBattleMenu;
import client.models.Player;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;

public class MainMenu extends AbstractMenu implements Serializable
{
    private static MainMenu ourInstance = new MainMenu();

    public static MainMenu getInstance()
    {
        return ourInstance;
    }

    private Pane battle = getBlueButton("Play");
    private Pane shop = getBlueButton("Shop");
    private Pane save = getBlueButton("Save");
    private Pane recordedMatch = getBlueButton("Recorded Match");
    private Pane scoreBoard = getYellowButton("Score Board");
    private VBox scoreBoardPane = new VBox(10);
    private Pane exit = getRedButton("Log out");

    private VBox messageArea = new VBox();
    private TextField textField = new TextField();

    private MainMenu()
    {
        try
        {
            initializeMenuGUI();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void initializeMenuGUI() throws IOException, InterruptedException
    {
        BorderPane borderPane = new BorderPane();

        borderPane.setStyle("-fx-background-color: #00abaa");

        borderPane.setPadding(new Insets(100));
        borderPane.getChildren().add(getBackGround("file:bg9.png"));

        borderPane.setRight(getRight());
        BorderPane.setAlignment(borderPane.getRight(), Pos.TOP_CENTER);
        borderPane.setLeft(getLeft());
        BorderPane.setAlignment(borderPane.getLeft(), Pos.TOP_CENTER);
        borderPane.setCenter(scoreBoardPane);
        borderPane.setAlignment(borderPane.getCenter(), Pos.TOP_CENTER);

        this.scene = new Scene(borderPane, Controller.getInstance().getWeight(), Controller.getInstance().getHeight());
        changeCursorImage("file:Css/pictures/New folder (3)/ui/mouse_auto.png");
    }

    private Node getLeft()
    {
        VBox vBox = new VBox(10);
        messageArea.setMinHeight(700);
        messageArea.minWidth(630);

        textField.setPromptText("Type Message To Send ... ");
        textField.setMinWidth(650);
        textField.setMinHeight(48);
        textField.setStyle("-fx-opacity: .5;-fx-background-color: #ab002d");

        textField.setOnKeyPressed(event ->
        {
            if (event.getCode() == KeyCode.ENTER)
            {
                String messenger = Controller.getInstance().getCurrentPlayer().getName();
                String messageBody = textField.getText();

                try
                {
                    Client.getOutputStream().writeObject("message " + messenger + " " + messageBody);
                    Client.getOutputStream().flush();
                    Client.getOutputStream().reset();

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                textField.clear();
            }
        });

        vBox.getChildren().addAll(messageArea, textField);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private Parent getRight() throws IOException, InterruptedException
    {
        VBox vBox = new VBox(45);
        vBox.setAlignment(Pos.CENTER);

        setGlowEffects(battle, shop, save, recordedMatch, exit);

        battle.setOnMouseClicked(event ->
        {
//            Controller.getInstance().changeCurrentMenuTo(StartBattleMenu.getInstance());

//            try
//            {
//                AIPlayer.getInstance().setAI();
//                CollectFlagBattle.setBattle(Controller.getInstance().getCurrentPlayer(), AIPlayer.getInstance().getAIPlayer(),1000,7);
//                BattleMenuLogic.getInstance().setCurrentBattle(CollectFlagBattle.getInstance());
//                CollectFlagBattle.getInstance().addFlagsToMap();
//
//                BattleMenu battleMenu = new BattleMenu();
//                battleMenu.initializeMenuGUI();
//                Controller.getInstance().getStage().setScene(battleMenu.getScene());

//            }
//            catch (CloneNotSupportedException e)
//            {
//                e.printStackTrace();
//            }
//            catch (InvalidDeck invalidDeck)
//            {
//                invalidDeck.printStackTrace();
//            }

            Controller.getInstance().changeCurrentMenuTo(StartBattleMenu.getInstance());
        });

        shop.setOnMouseClicked(mouseEvent ->
        {
            ShopMenu.getInstance().initializeMenuGUI();
            Controller.getInstance().changeCurrentMenuTo(ShopMenu.getInstance());
        });

        save.setOnMouseClicked(event ->
        {
            save();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Saved");
            alert.show();
        });

        recordedMatch.setOnMouseClicked(event -> Controller.getInstance().changeCurrentMenuTo(RecordedMenu.getInstance()));

        scoreBoardPane.setPadding(new Insets(100, 0, 0, 290));

        scoreBoard.setOnMouseEntered(event ->
        {
            FadeTransition transition = new FadeTransition(Duration.seconds(1), scoreBoardPane);
            transition.setFromValue(0);
            transition.setToValue(1);
            transition.play();
            try
            {
                Client.getOutputStream().writeObject("sync players");
                Client.getOutputStream().flush();
                Client.getOutputStream().reset();
                Client.getOutputStream().writeObject("onlineusers");
                Client.getOutputStream().flush();
                Client.getOutputStream().reset();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });

        scoreBoard.setOnMouseExited(event ->
        {
            scoreBoardPane.getChildren().clear();
        });

        exit.setOnMouseClicked(mouseEvent ->
        {
            try
            {
                Client.getOutputStream().writeObject("logout " + Controller.getInstance().getCurrentPlayer().getName());
                Client.getOutputStream().flush();
                Client.getOutputStream().reset();

                Controller.getInstance().setCurrentPlayer(null);
                Controller.getInstance().changeCurrentMenuTo(EntryMenu.getInstance());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });

        vBox.getChildren().addAll(battle, shop, save, recordedMatch, scoreBoard, exit);
        vBox.setPadding(new Insets(0., 0, 75, 0));

        return vBox;
    }

    public void save()
    {
        try
        {
            ArrayList<Player> playerArrayList = Player.getPlayers();

            Client.getOutputStream().writeObject(playerArrayList);
            Client.getOutputStream().flush();
            Client.getOutputStream().reset();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public VBox getMessageArea()
    {
        return messageArea;
    }

    public TextField getTextField()
    {
        return textField;
    }

    public VBox getScoreBoardPane()
    {
        return scoreBoardPane;
    }
}

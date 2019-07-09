package client.controller.battlemenus;

import client.controller.BattleMenu;
import client.controller.BattleMenuLogic;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import client.controller.Controller;
import client.controller.Exceptions.InvalidBattleName;
import client.controller.Exceptions.InvalidDeck;
import client.controller.menus.AbstractMenu;
import client.controller.menus.BattleMenuGUI;
import client.models.AIPlayer;
import client.models.battle.CollectFlagBattle;
import client.models.battle.DeadBattle;
import client.models.battle.KeepFlagBattle;

public class CustomGameMenu extends AbstractMenu
{
    private static CustomGameMenu ourInstance = new CustomGameMenu();

    public static CustomGameMenu getInstance()
    {
        return ourInstance;
    }

    private CustomGameMenu()
    {
        initializeMenuGUI();
    }

    private Pane deadBattleBTN = getBlueButton("DeadMatch");
    private Pane collectFlagBTN = getBlueButton("CollectFlag");
    private TextField flagNumberTF = new TextField();
    private Pane keepFlagBTN = getBlueButton("KeepFlag");
    private Pane backBTN = getRedButton("Back");

    public void setCollectFlagBattle(String command) throws InvalidBattleName, InvalidDeck, CloneNotSupportedException
    {
        String battleName = command.split(" ")[2];
        int numberOfFlag = Integer.parseInt(command.split(" ")[3]);
        if (battleName.equals("CollectFlag"))
        {
            BattleController.getInstance().setCurrentBattle(new CollectFlagBattle
                    (Controller.getInstance().getCurrentPlayer(), AIPlayer.getInstance().getAIPlayer(), 1000, numberOfFlag));
            Controller.getInstance().changeCurrentMenuTo(BattleMenuGUI.getInstance());
        }
        else
            throw new InvalidBattleName();
    }

    public void setBattle(String command) throws InvalidBattleName, InvalidDeck, CloneNotSupportedException
    {
        String battleName = command.split(" ")[2];
        if (battleName.equals("DeadBattle"))
        {
            BattleController.getInstance().setCurrentBattle(new DeadBattle
                    (Controller.getInstance().getCurrentPlayer(), AIPlayer.getInstance().getAIPlayer(), 500));
            Controller.getInstance().changeCurrentMenuTo(BattleMenuGUI.getInstance());
        }
        else if (battleName.equals("KeepFlag"))
        {
            BattleController.getInstance().setCurrentBattle(new KeepFlagBattle
                    (Controller.getInstance().getCurrentPlayer(), AIPlayer.getInstance().getAIPlayer(), 1500));
            Controller.getInstance().changeCurrentMenuTo(BattleMenuGUI.getInstance());
        }
        else
            throw new InvalidBattleName();
    }

    public String toShowMenu()
    {
        return "StartGame\n";
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

        deadBattleBTN.setOnMouseClicked(event ->
        {
            try
            {
                AIPlayer.getInstance().setAI();
                DeadBattle.setBattle(Controller.getInstance().getCurrentPlayer(), AIPlayer.getInstance().getAIPlayer(), 1000);
                BattleMenuLogic.getInstance().setCurrentBattle(DeadBattle.getInstance());

                BattleMenu battleMenu = new BattleMenu();
                battleMenu.initializeMenuGUI();
                ((Label) battleMenu.getGameInfoPane().getChildren().get(1)).setText("dead match");
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
                int flagNumber = Integer.parseInt(flagNumberTF.getText());
                CollectFlagBattle.setBattle(Controller.getInstance().getCurrentPlayer(), AIPlayer.getInstance().getAIPlayer(), 1000, flagNumber);
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
            catch (InvalidDeck e)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,"Invalid Deck");
                alert.show();
            }
        });

        VBox collectFlagHBox = new VBox(5);
        flagNumberTF.setStyle(("-fx-background-color:#0e7391;-fx-border-style: Solid; -fx-border-color: #00e6ff;-fx-border-width: 4"));
        flagNumberTF.setMaxWidth(40);
        collectFlagHBox.setAlignment(Pos.CENTER);
        collectFlagHBox.getChildren().addAll(collectFlagBTN, flagNumberTF);

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
            catch (InvalidDeck e)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,"Invalid Deck");
                alert.show();
            }
        });

        backBTN.setOnMouseClicked(event ->
        {
            Controller.getInstance().changeCurrentMenuTo(StartBattleMenu.getInstance());
        });

        setGlowEffects(deadBattleBTN, collectFlagBTN, keepFlagBTN, backBTN);

        vBox.getChildren().addAll(deadBattleBTN, collectFlagHBox, keepFlagBTN, backBTN);

        return vBox;
    }
}

package client.controller.battlemenus;

import client.controller.BattleMenu;
import client.controller.menus.RecordedMenu;
import com.teamdev.jxcapture.Codec;
import com.teamdev.jxcapture.EncodingParameters;
import com.teamdev.jxcapture.VideoCapture;
import com.teamdev.jxcapture.video.Desktop;
import com.teamdev.jxcapture.video.Framerate;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import client.controller.Controller;
import client.controller.menus.AbstractMenu;
import client.controller.menus.MainMenu;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class StartBattleMenu extends AbstractMenu
{
    private static StartBattleMenu startBattleMenuInstance = new StartBattleMenu();

    private Pane storyModeBTN = getBlueButton("Story Mode");
    private Pane customGameBTN = getBlueButton("Custom Game");
    private Pane multyPlayerBTN = getBlueButton("Multy Player");
    private TextField timeLimitTF = new TextField();

    private Pane continueBTN = getGreenButton("Continue");
    private Pane recordBTN = getYellowButton("Start Record");
    private VideoCapture videoCapture;
    private int videoAddressCounter = 0;
    private boolean inRecord = false;

    private Pane backBTN = getRedButton("Back");

    public static StartBattleMenu getInstance()
    {
        return startBattleMenuInstance;
    }

    private StartBattleMenu()
    {
        initializeMenuGUI();
    }

    public void selectOptionByCommand(String command)
    {
        if (command.matches("Story Mode"))
            Controller.getInstance().changeCurrentMenuTo(StoryModeMenu.getInstance());

        else if (command.matches("Custom Game"))
            Controller.getInstance().changeCurrentMenuTo(StoryModeMenu.getInstance());

        else if (command.matches("Multy Player"))
        {}

        else if (command.matches("Exit"))
            Controller.getInstance().changeCurrentMenuTo(MainMenu.getInstance());
    }

    public String toShowMenu()
    {
        return "1.Story Mode\n2.Custom Game\n3.NetWork Game\n4.Exit\n";
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
        vBox.setOnKeyPressed(event ->
        {
            if (event.getCode() == KeyCode.ESCAPE)
            {
                Controller.getInstance().changeCurrentMenuTo(MainMenu.getInstance());
            }
        });

        storyModeBTN.setOnMouseClicked(event ->
        {
            Controller.getInstance().changeCurrentMenuTo(StoryModeMenu.getInstance());
        });

        customGameBTN.setOnMouseClicked(event ->
        {
            Controller.getInstance().changeCurrentMenuTo(CustomGameMenu.getInstance());
        });

        multyPlayerBTN.setOnMouseClicked(event ->
        {

        });

        recordBTN.setOnMouseClicked(event ->
        {
            if (!inRecord)
            {
                inRecord = true;
                ((Label) recordBTN.getChildren().get(1)).setText("Stop Record");

                videoCapture = VideoCapture.create();
                videoCapture.setVideoSource(new Desktop());

                java.util.List<Codec> videoCodecs = videoCapture.getVideoCodecs();
                Codec videoCodec = videoCodecs.get(0);

                EncodingParameters encodingParameters = new EncodingParameters(new File("Record" + videoAddressCounter + "." + videoCapture.getVideoFormat().getId()));
                videoAddressCounter++;

                Pane recordPane = getBlueButton("Record" + videoAddressCounter);
                recordPane.setOnMouseClicked(theEvent ->
                {
                    try
                    {
                        Desktop.getDesktop().open(new File("Record" + (videoAddressCounter - 1) + ".WMV"));
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                });
                setGlowEffects(recordPane);
                RecordedMenu.getInstance().getRecordsVBox().getChildren().add(recordPane);

                encodingParameters.setSize(new Dimension(1600, 900));
                encodingParameters.setBitrate(800000);
                encodingParameters.setFramerate(30);
                encodingParameters.setCodec(videoCodec);

                videoCapture.setEncodingParameters(encodingParameters);
                videoCapture.start();
            }
            else
            {
                inRecord = false;
                ((Label) recordBTN.getChildren().get(1)).setText("Start Record");
                videoCapture.stop();
            }
        });

        backBTN.setOnMouseClicked(event ->
        {
            Controller.getInstance().changeCurrentMenuTo(MainMenu.getInstance());
        });

        timeLimitTF.setMaxWidth(100);
        timeLimitTF.setMinWidth(100);
        timeLimitTF.setPromptText("Turn Time Limit");
        timeLimitTF.setAlignment(Pos.CENTER);

        timeLimitTF.setOnKeyPressed(event ->
        {
            if (event.getCode() == KeyCode.ENTER)
            {
                int timeTurnLimit = Integer.parseInt(timeLimitTF.getText());
                BattleMenu.setTurnTimeLimit(timeTurnLimit);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Limit Time Changed To " + timeTurnLimit);
                alert.show();
            }
        });

        setGlowEffects(storyModeBTN, customGameBTN, multyPlayerBTN, timeLimitTF, backBTN);

        continueBTN.setOnMouseClicked(event ->
        {
            if (Controller.getInstance().isBattleSaved())
            {
                Controller.getInstance().getStage().setScene(Controller.getInstance().getBattleMenu().getScene());
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR,"You Didnt Start a Match");
                alert.show();
            }
        });

        vBox.getChildren().addAll(continueBTN, storyModeBTN, customGameBTN, multyPlayerBTN, timeLimitTF, recordBTN, backBTN); //TODO add Multy PLayer

        vBox.setAlignment(Pos.TOP_CENTER);

        return vBox;
    }
}

package client.controller.menus;

import client.controller.Controller;
import client.controller.battlemenus.StartBattleMenu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RecordedMenu extends AbstractMenu
{
    private static RecordedMenu instance = new RecordedMenu();

    private VBox recordsVBox = new VBox(20);

    private RecordedMenu()
    {
        try
        {
            initializeMenuGUI();
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public static RecordedMenu getInstance()
    {
        return instance;
    }

    @Override
    public void initializeMenuGUI() throws IOException, InterruptedException
    {
        BorderPane borderPane = new BorderPane();

        borderPane.setStyle("-fx-background-color: #00abaa");

        borderPane.setPadding(new Insets(100));
        borderPane.getChildren().add(getBackGround("file:bg9.png"));

        borderPane.setCenter(getCenter());
        BorderPane.setAlignment(borderPane.getCenter(), Pos.TOP_CENTER);
        borderPane.setBottom(getButtom());
        BorderPane.setAlignment(borderPane.getBottom(), Pos.CENTER);

        this.scene = new Scene(borderPane, Controller.getInstance().getWeight(), Controller.getInstance().getHeight());
        changeCursorImage("file:Css/pictures/New folder (3)/ui/mouse_auto.png");
    }

    private Node getButtom()
    {
        Pane backBTN = getRedButton("Back");
        backBTN.setOnMouseClicked(event -> Controller.getInstance().changeCurrentMenuTo(MainMenu.getInstance()));
        setGlowEffects(backBTN);
        return backBTN;
    }

    private Node getCenter()
    {
        return recordsVBox;
    }

    public VBox getRecordsVBox()
    {
        return recordsVBox;
    }
}

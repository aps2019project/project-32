package client.controller.menus;

import client.Client;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import client.controller.Controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;


public class EntryMenu extends AbstractMenu implements Serializable
{

    private static EntryMenu ourInstance = new EntryMenu();

    public static EntryMenu getInstance()
    {
        return ourInstance;
    }

    private Pane login = getBlueButton("Login");
    private Pane signIn = getBlueButton("Register");
    private Pane exit = getBlueButton("Exit");

    private EntryMenu()
    {
        initializeMenuGUI();
    }

    @Override
    public void initializeMenuGUI()
    {
        BorderPane borderPane = new BorderPane();
        borderPane.getChildren().add(getBackGround("file:Css/pictures/New folder (3)/scenes/obsidian_woods/obsidian_woods_background.jpg"));
        borderPane.setCenter(setCenter());


        this.scene = new Scene(borderPane, Controller.getInstance().getWeight(), Controller.getInstance().getHeight());
        changeCursorImage("file:Css/pictures/New folder (3)/ui/mouse_auto.png");
    }

    private Parent setCenter()
    {
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPadding(new Insets(100));
        setGlowEffects(login, signIn, exit);

        ImageView imageView = new ImageView(new Image("duelystlogo.png"));

        setGlowEffects(imageView);

        login.setOnMouseClicked(event ->
        {
            try
            {
                Client.getOutputStream().writeObject("sync players");
                Client.getOutputStream().flush();
                Client.getOutputStream().reset();
                Controller.getInstance().changeCurrentMenuTo(LoginMenu.getInstance());
                LoginMenu.getInstance().resetAll();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
        signIn.setOnMouseClicked(event ->
        {
            Controller.getInstance().changeCurrentMenuTo(RegisterMenu.getInstance());
            RegisterMenu.getInstance().resetAll();
        });
        exit.setOnMouseClicked(mouseEvent ->
        {
            System.exit(0);
        });


        vBox.getChildren().addAll(imageView, login, signIn, exit);
        return vBox;
    }

    public Scene getScene()
    {
        return scene;
    }
}

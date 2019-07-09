package client.controller.menus;

import client.Client;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import client.controller.Controller;
import client.models.Player;

import java.awt.*;
import java.io.IOException;
import java.io.Serializable;

public class LoginMenu extends AbstractMenu implements Serializable
{
    private static LoginMenu ourInstance = new LoginMenu();
    private TextField userText = new TextField();
    private PasswordField passText = new PasswordField();
    private Pane login = getBlueButton("Login");

    public static LoginMenu getInstance()
    {
        return ourInstance;
    }

    private LoginMenu()
    {
        initializeMenuGUI();
    }

    @Override
    public void initializeMenuGUI()
    {
        BorderPane borderPane = new BorderPane();

        borderPane.getChildren().add(getBackGround("file:Css/pictures/New folder (3)/scenes/load/scene_load_background@2x.jpg"));
        borderPane.setLeft(getLeft());

        borderPane.setPadding(new Insets(150));

        this.scene = new Scene(borderPane, Controller.getInstance().getWeight(), Controller.getInstance().getHeight());
        changeCursorImage("file:Css/pictures/New folder (3)/ui/mouse_auto.png");
    }

    private Parent getLeft()
    {
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);

        vBox.setOnKeyPressed(event ->
        {
            if (event.getCode() == KeyCode.ESCAPE)
            {
                Controller.getInstance().changeCurrentMenuTo(EntryMenu.getInstance());
            }
        });

        userText.setMaxWidth(170);
        userText.setStyle("-fx-opacity: .2;-fx-border-style: Solid; -fx-border-color: #00e6ff;-fx-border-radius: 15;-fx-background-radius: 15");
        userText.setPromptText("Username");
        userText.setAlignment(Pos.CENTER);
        userText.setFont(Font.font("Leelawadee UI", 20));

        passText.setMaxWidth(170);
        passText.setPromptText("Password");
        passText.setAlignment(Pos.CENTER);
        passText.setStyle("-fx-opacity: .2;-fx-border-style: Solid; -fx-border-color: #00e6ff;-fx-border-radius: 15;-fx-background-radius: 15");
        passText.setFont(Font.font("Leelawadee UI", 20));

        setGlowEffects(login);
        login.setOnMouseClicked(keyEvent ->
        {
            String name = userText.getText();
            String password = passText.getText();
            Login(name, password);
        });

        vBox.getChildren().addAll(userText, passText, login);
        return vBox;
    }

    private void Login(String name, String password)
    {
        boolean playerFoundWithThisName = false;
        for (Player player : Player.getPlayers())
            if (player.getName().equals(name))
            {
                playerFoundWithThisName = true;
                if (player.getPassWord().equals(password))
                {
                    Controller.getInstance().setCurrentPlayer(player);
                    Controller.getInstance().changeCurrentMenuTo(MainMenu.getInstance());

                    break;
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Password is Wrong");
                    alert.show();
                    resetTexts();
                }
            }
        if (!playerFoundWithThisName)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "User Did not Exist");
            alert.show();
            resetTexts();
        }
    }

    private void resetTexts()
    {
        userText.setText("");
        passText.setText("");
    }

    public void resetAll()
    {
        userText.setText("");
        passText.setText("");
    }

}

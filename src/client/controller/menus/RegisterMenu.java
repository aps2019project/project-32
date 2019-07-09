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

import java.io.IOException;
import java.io.Serializable;

public class RegisterMenu extends AbstractMenu implements Serializable
{
    private static RegisterMenu ourInstance = new RegisterMenu();
    private TextField userText = new TextField();
    private PasswordField passText = new PasswordField();
    private Pane register = getBlueButton("Register");

    public static RegisterMenu getInstance()
    {
        return ourInstance;
    }

    private RegisterMenu()
    {
        initializeMenuGUI();
    }

    @Override
    public void initializeMenuGUI()
    {
        BorderPane borderPane = new BorderPane();

        borderPane.getChildren().add(getBackGround("file:Css/pictures/New folder (3)/scenes/load/scene_load_background@2x.jpg"));
        borderPane.setLeft(getCenter());

        borderPane.setPadding(new Insets(150));

        this.scene = new Scene(borderPane, Controller.getInstance().getWeight(), Controller.getInstance().getHeight());
        changeCursorImage("file:Css/pictures/New folder (3)/ui/mouse_auto.png");
    }

    private Parent getCenter()
    {
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setOnKeyPressed(event ->
        {
            if (event.getCode() == KeyCode.ESCAPE)
                Controller.getInstance().changeCurrentMenuTo(EntryMenu.getInstance());
        });

        userText.setMaxWidth(170);
        userText.setPromptText("Username");
        userText.setStyle("-fx-opacity: .2;-fx-border-style: Solid; -fx-border-color: #00e6ff;-fx-border-radius: 15;-fx-background-radius: 15");
        userText.setAlignment(Pos.CENTER);
        userText.setFont(Font.font("Leelawadee UI", 18));

        passText.setMaxWidth(170);
        passText.setPromptText("Password");
        passText.setAlignment(Pos.CENTER);
        passText.setStyle("-fx-opacity: .2;-fx-border-style: Solid; -fx-border-color: #00e6ff;-fx-border-radius: 15;-fx-background-radius: 15");
        passText.setFont(Font.font("Leelawadee UI", 18));

        setGlowEffects(register);
        register.setOnMouseClicked(keyEvent ->
        {
            String name = userText.getText();
            String password = passText.getText();
            register(name, password);
        });

        vBox.getChildren().addAll(userText, passText, register);
        return vBox;
    }

    private void register(String userName, String password)
    {
        for (Player player : Player.getPlayers())
            if (player.getName().equals(userName))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "This User is Exist Already");
                alert.show();
                resetTexts();
                return;
            }
        if (true)
        {
            Player registeredPlayer = new Player(userName, password);
            Player.getPlayers().add(registeredPlayer);
            try
            {
                Client.getOutputStream().writeObject(Player.getPlayers());
                Client.getOutputStream().flush();
                Client.getOutputStream().reset();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Account Created");
            alert.show();
            resetTexts();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Weak Password");
            alert.show();
            resetTexts();
        }
    }

    private boolean checkPassWordSafety(String passWord)
    {
        boolean passWordHas8Character = false;
        boolean passWordHasDigit = false;
        boolean passWordHasLowerCase = false;
        boolean passWordHasUpperCase = false;

        if (passWord.length() >= 8)
            passWordHas8Character = true;

        for (int i = 0; i < passWord.length(); i++)
        {
            char ch = passWord.charAt(i);
            if (Character.isDigit(ch))
                passWordHasDigit = true;
            else if (Character.isLowerCase(ch))
                passWordHasLowerCase = true;
            else if (Character.isUpperCase(ch))
                passWordHasUpperCase = true;
        }

        return passWordHas8Character && passWordHasDigit && passWordHasLowerCase && passWordHasUpperCase;
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

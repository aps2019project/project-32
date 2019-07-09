package client.controller;

import client.Client;
import client.controller.Exceptions.RepeatedDeckName;
import client.controller.menus.AuctionMenu;
import client.controller.menus.MainMenu;
import client.controller.menus.ShopMenu;
import com.jniwrapper.Str;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import client.controller.menus.AbstractMenu;
import client.models.Player;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.security.SecureRandom;

public class

Controller implements Serializable
{
    boolean battleSaved = false;
    BattleMenu battleMenu;

    private static Controller instance = new Controller();
    private AbstractMenu currentMenu;
    private Player currentPlayer;
    private Stage stage;
    private final int Height = 1080;
    private final int Weight = 1920;
    private MediaPlayer mediaPlayer;

    public static Controller getInstance()
    {
        return instance;
    }

    private Controller()
    {

        String address = "Css/pictures/New folder (3)/music/mainmenu_v2c_looping.m4a";
        Media media = new Media(new File(address).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setAutoPlay(false);
        mediaPlayer.play();
    }

    public void changeCurrentMenuTo(AbstractMenu menu)
    {
        this.currentMenu = menu;

        if (menu instanceof MainMenu)
        {
            try
            {
                Client.getOutputStream().writeObject(Controller.getInstance().getCurrentPlayer());
                Client.getOutputStream().flush();
                Client.getOutputStream().reset();
                ShopMenu.getInstance().addPrimitiveCardsToPlayerCollection(Controller.getInstance().getCurrentPlayer());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (RepeatedDeckName repeatedDeckName)
            {
                repeatedDeckName.printStackTrace();
            }
        }
        else if (menu instanceof ShopMenu)
        {
            Client.addCustomCardsToShop();
        }

        else if (menu instanceof AuctionMenu)
        {
            AuctionMenu.getInstance().getBorderPane().setLeft(AuctionMenu.getInstance().getLeft());
        }

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1500));
        fadeTransition.setNode(menu.getScene().getRoot());
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1000);
        fadeTransition.play();

        mediaPlayer.play();

        stage.setScene(menu.getScene());
    }

    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer)
    {
        this.currentPlayer = currentPlayer;
    }

    public Stage getStage()
    {
        return stage;
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    public int getWeight()
    {
        return Weight;
    }

    public int getHeight()
    {
        return Height;
    }

    public void setCurrentMenu(AbstractMenu currentMenu)
    {
        this.currentMenu = currentMenu;
    }

    public MediaPlayer getMediaPlayer()
    {
        return mediaPlayer;
    }

    public AbstractMenu getCurrentMenu()
    {
        return currentMenu;
    }

    public boolean isBattleSaved()
    {
        return battleSaved;
    }

    public void setBattleSaved(boolean battleSaved)
    {
        this.battleSaved = battleSaved;
    }

    public void setBattleMenu(BattleMenu battleMenu)
    {
        this.battleMenu = battleMenu;
    }

    public BattleMenu getBattleMenu()
    {
        return battleMenu;
    }
}

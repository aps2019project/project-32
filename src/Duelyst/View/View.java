package Duelyst.View;

import Duelyst.controller.LoginHandler;
import Duelyst.controller.MainMenuHandler;
import Duelyst.Main;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.IOException;

import static Duelyst.Main.basicStage;

public class View {
    public static void makeLoginScene() {
        Platform.runLater(() -> {
            GridPane parent = null;
            try {
                parent = FXMLLoader.load(LoginHandler.class.getResource("loginMenu.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene loginPage = new Scene(parent);
            loginPage.setCursor(new ImageCursor(new Image("Duelyst/css/OzFOdVG.png")));
            loginPage.getStylesheets().add(Main.class.getResource("css/css.css").toExternalForm());
            basicStage.setScene(loginPage);
            basicStage.setFullScreen(true);
            basicStage.setResizable(false);

        });

    }

    public static void makeMainMenu() {
        Platform.runLater(() -> {
            GridPane root = null;
            try {
                root = FXMLLoader.load(MainMenuHandler.class.getResource("mainMenu.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene mainMenuPage = new Scene(root);

            mainMenuPage.setCursor(new ImageCursor(new Image("Duelyst/css/OzFOdVG.png")));
            mainMenuPage.getStylesheets().add(Main.class.getResource("css/css2.css").toExternalForm());
            basicStage.setScene(mainMenuPage);
            basicStage.setFullScreen(true);
            basicStage.setResizable(false);

        });

    }

    public static void makeShopMenu() {
        Platform.runLater(() -> {
            StackPane root = null;
            try {
                root = FXMLLoader.load(MainMenuHandler.class.getResource("shopMenu.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene mainMenuScene = new Scene(root);

            mainMenuScene.setCursor(new ImageCursor(new Image("Duelyst/css/OzFOdVG.png")));
            mainMenuScene.getStylesheets().add(Main.class.getResource("css/css3.css").toExternalForm());
            basicStage.setScene(mainMenuScene);
            basicStage.setFullScreen(true);
            basicStage.setResizable(false);

        });

    }

    public static void makeCollectionMenu() {

                StackPane root = null;
                try {
                    root = FXMLLoader.load(MainMenuHandler.class.getResource("collectionMenu.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene collectionScene = new Scene(root);

                collectionScene.setCursor(new ImageCursor(new Image("Duelyst/css/OzFOdVG.png")));
                collectionScene.getStylesheets().add(Main.class.getResource("css/css4.css").toExternalForm());
                basicStage.setScene(collectionScene);
                basicStage.setFullScreen(true);
                basicStage.setResizable(false);



    }
    public static void makeSingleOrMultiMenu() {

        GridPane root = null;
        try {
            root = FXMLLoader.load(MainMenuHandler.class.getResource("singleOrMultiMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene singleOrMultiScene = new Scene(root);

        singleOrMultiScene.setCursor(new ImageCursor(new Image("Duelyst/css/OzFOdVG.png")));
        singleOrMultiScene.getStylesheets().add(Main.class.getResource("css/css5.css").toExternalForm());
        basicStage.setScene(singleOrMultiScene);
        basicStage.setFullScreen(true);
        basicStage.setResizable(false);



    }
    public static void makeModeMenuMenu() {

        GridPane root = null;
        try {
            root = FXMLLoader.load(MainMenuHandler.class.getResource("gameModeMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene modeMenuScene = new Scene(root);

        modeMenuScene.setCursor(new ImageCursor(new Image("Duelyst/css/OzFOdVG.png")));
        modeMenuScene.getStylesheets().add(Main.class.getResource("css/css5.css").toExternalForm());
        basicStage.setScene(modeMenuScene);
        basicStage.setFullScreen(true);
        basicStage.setResizable(false);



    }
    public static void makeBattle() {

        StackPane root = null;
        try {
            root = FXMLLoader.load(MainMenuHandler.class.getResource("battle.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene modeMenuScene = new Scene(root);

        modeMenuScene.setCursor(new ImageCursor(new Image("Duelyst/css/OzFOdVG.png")));
        modeMenuScene.getStylesheets().add(Main.class.getResource("css/css6.css").toExternalForm());
        basicStage.setScene(modeMenuScene);
        basicStage.setFullScreen(true);
        basicStage.setResizable(false);



    }
}

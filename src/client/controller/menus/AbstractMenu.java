package client.controller.menus;

import com.jniwrapper.Str;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public abstract class AbstractMenu implements Serializable
{
    protected Scene scene;

    abstract public void initializeMenuGUI() throws IOException, InterruptedException;

    public Pane getBlueButton(String name)
    {
        Image image = new Image("file:Css\\pictures\\New folder (3)\\ui\\button_secondary.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        Label label = new Label(name);
        label.setFont(Font.font("Leelawadee UI", 20));
        label.setTextFill(Color.WHITE);

        imageView.setOnMouseClicked(event ->
        {
            String voice = "C:/Users/hp/Desktop/my own project/Css/pictures/New folder (3)/sfx/sfx_ui_booster_huming_tail.m4a";
            Media media = new Media(new File(voice).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        });

        StackPane stackPane = new StackPane(imageView, label);
        return stackPane;
    }

    public Pane getRedButton(String name)
    {
        Image image = new Image("file:Css/pictures/New folder (3)/ui/button_cancel@2x.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        Label label = new Label(name);
        label.setFont(Font.font("Leelawadee UI", 20));
        label.setTextFill(Color.WHITE);

        imageView.setOnMouseClicked(event ->
        {
            String voice = "C:/Users/hp/Desktop/my own project/Css/pictures/New folder (3)/sfx/sfx_ui_booster_huming_tail.m4a";
            Media media = new Media(new File(voice).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        });

        StackPane stackPane = new StackPane(imageView, label);
        return stackPane;
    }

    public Pane getRedButton(String name, int width)
    {
        Image image = new Image("file:Css/pictures/New folder (3)/ui/button_cancel@2x.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setPreserveRatio(true);
        Label label = new Label(name);
        label.setFont(Font.font("Leelawadee UI", 20));
        label.setTextFill(Color.WHITE);

        imageView.setOnMouseClicked(event ->
        {
            String voice = "C:/Users/hp/Desktop/my own project/Css/pictures/New folder (3)/sfx/sfx_ui_booster_huming_tail.m4a";
            Media media = new Media(new File(voice).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        });

        StackPane stackPane = new StackPane(imageView, label);
        return stackPane;
    }

    public Pane getYellowButton(String name)
    {
        Image image = new Image("file:Css/pictures/New folder (3)/ui/button_end_turn_mine@2x.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        Label label = new Label(name);
        label.setFont(Font.font("Leelawadee UI", 20));
        label.setTextFill(Color.WHITE);

        imageView.setOnMouseClicked(event ->
        {
            String voice = "C:/Users/hp/Desktop/my own project/Css/pictures/New folder (3)/sfx/sfx_ui_booster_huming_tail.m4a";
            Media media = new Media(new File(voice).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        });

        StackPane stackPane = new StackPane(imageView, label);
        return stackPane;
    }

    public Pane getYellowButton(String name, int width)
    {
        Image image = new Image("file:Css/pictures/New folder (3)/ui/button_end_turn_mine@2x.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setPreserveRatio(true);
        Label label = new Label(name);
        label.setFont(Font.font("Leelawadee UI", 20));
        label.setTextFill(Color.WHITE);

        imageView.setOnMouseClicked(event ->
        {
            String voice = "C:/Users/hp/Desktop/my own project/Css/pictures/New folder (3)/sfx/sfx_ui_booster_huming_tail.m4a";
            Media media = new Media(new File(voice).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.play();
        });

        StackPane stackPane = new StackPane(imageView, label);
        return stackPane;
    }

    public Pane getGreenButton(String buttonName)
    {
        Image image = new Image("file:Css/pictures/New folder (3)/ui/button_confirm@2x.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        Label label = new Label(buttonName);
        label.setFont(Font.font("Leelawadee UI", 20));
        label.setTextFill(Color.WHITE);

        imageView.setOnMouseClicked(event ->
        {
            String voice = "C:/Users/hp/Desktop/my own project/Css/pictures/New folder (3)/sfx/sfx_ui_booster_huming_tail.m4a";
            Media media = new Media(new File(voice).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.play();
        });

        StackPane stackPane = new StackPane(imageView, label);
        return stackPane;
    }

    public Pane getNotification(String message)
    {
        Image image = new Image("file:Css/pictures/New folder (3)/tutorial/tooltip_up@2x.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        Label label = new Label(message);
        label.setFont(Font.font("Leelawadee UI", 20));
        label.setTextFill(Color.WHITE);

        imageView.setOnMouseClicked(event ->
        {
            String voice = "C:/Users/hp/Desktop/my own project/Css/pictures/New folder (3)/sfx/sfx_ui_booster_huming_tail.m4a";
            Media media = new Media(new File(voice).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        });

        StackPane stackPane = new StackPane(imageView, label);
        return stackPane;
    }

    public Pane getGreenButton(String buttonName, int width)
    {
        Image image = new Image("file:Css/pictures/New folder (3)/ui/button_confirm@2x.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setPreserveRatio(true);
        Label label = new Label(buttonName);
        label.setFont(Font.font("Leelawadee UI", 20));
        label.setTextFill(Color.WHITE);

        imageView.setOnMouseClicked(event ->
        {
            String voice = "C:/Users/hp/Desktop/my own project/Css/pictures/New folder (3)/sfx/sfx_ui_booster_huming_tail.m4a";
            Media media = new Media(new File(voice).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.play();
        });

        StackPane stackPane = new StackPane(imageView, label);
        return stackPane;
    }

    public TextField getGUITextField()
    {
        return null;
    }

    protected Pane getPaneInAddress(String address, String buttonName, int buttonWidth)
    {
        Image image = new Image(address);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(buttonWidth);
        imageView.setPreserveRatio(true);
        Label label = new Label(buttonName);
        label.setFont(Font.font("Leelawadee UI", 20));
        label.setTextFill(Color.WHITE);
        StackPane stackPane = new StackPane(imageView, label);

        imageView.setOnMouseClicked(event ->
        {
            String voice = "C:/Users/hp/Desktop/my own project/Css/pictures/New folder (3)/sfx/sfx_ui_booster_huming_tail.m4a";
            Media media = new Media(new File(voice).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.play();
        });

        return stackPane;
    }

    protected void setGlowEffects(Node... nodes)
    {
        for (Node node : nodes)
        {
            node.setOnMouseEntered(event -> node.setEffect(new Glow(30)));
            node.setOnMouseExited(event -> node.setEffect(new Glow(0)));
        }
    }

    protected void setGlowEffects(double glowLevel, Node... nodes)
    {
        for (Node node : nodes)
        {
            node.setOnMouseEntered(event -> node.setEffect(new Glow(glowLevel)));
            node.setOnMouseExited(event -> node.setEffect(new Glow(0)));
        }
    }

    protected ImageView getBackGround(String address)
    {
        Image image = new Image(address);
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(1080);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    protected ImageView getBackGround(String address, int height)
    {
        Image image = new Image(address);
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    public StackPane getImagePane(String imageAddress, int imageHeight)
    {
        ImageView image = new ImageView(new Image(imageAddress));
        image.setPreserveRatio(true);
        image.setFitHeight(imageHeight);

        Label label = new Label();
        label.setFont(Font.font("Tahoma", 12));
        label.setTextFill(Color.YELLOW);
        StackPane pane = new StackPane();
        pane.getChildren().addAll(image,label);
        return pane;
    }

    protected Label getLabel(String string, Color color, Font font)
    {
        Label label = new Label();
        label.setText(string);
        label.setTextFill(color);
        label.setFont(font);

        return label;
    }

    public Scene getScene()
    {
        return scene;
    }

    protected void changeCursorImage(String address)
    {
        scene.setCursor(Cursor.cursor(address));
    }
}

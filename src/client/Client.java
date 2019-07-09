package client;

import client.controller.menus.*;
import client.models.Player;
import client.models.widget.Widget;
import client.models.widget.cards.Card;
import com.jniwrapper.Str;
import com.teamdev.jxcapture.VideoCapture;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import client.controller.Controller;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import sun.net.ConnectionResetException;

import java.io.*;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

public class Client extends Application
{
    private static Socket socket;
    private static ObjectInputStream inputStream;
    private static ObjectOutputStream outputStream;
    private static boolean isActive;
    private static Card auctionCard;
    private static ArrayList<Card> customCards = new ArrayList<>();
    private static ArrayList<Card> shopCards = new ArrayList<>();

    public static void addCustomCardsToShop()
    {
        ShopMenu.getInstance().getShopCards().addAll(customCards);
        ShopMenu.getInstance().getBorderPane().setLeft(ShopMenu.getInstance().getLeft());
    }

    @Override
    public void start(Stage primaryStage)
    {
        try
        {
            Scene scene = EntryMenu.getInstance().getScene();

            Controller.getInstance().setStage(primaryStage);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (Exception ignored)
        {
        }
    }

    public static Properties getProperties() throws IOException
    {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/config.properties"));
        return properties;
    }

    public static void main(String[] args)
    {
        new Thread(() ->
        {
            try
            {
                try
                {
                    Properties property = getProperties();
                    int port = Integer.parseInt(property.getProperty("port"));
                    socket = new Socket("localhost", 8000);
                }
                catch (IOException ex)
                {
                    socket = new Socket("localhost", 8000);
                }

                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            while (true)
            {
                try
                {
                    Object inputObject = inputStream.readObject();
                    System.out.println("Object Received From Server ... ");
                    System.out.println(inputObject);

                    if (inputObject instanceof ArrayList)
                    {
                        ArrayList<Player> players = ((ArrayList) inputObject);
                        Player.setPlayers(players);
                    }
                    else if (inputObject instanceof Card)
                    {
                        Card inputCard = ((Card) inputObject);
                        if (isActive)
                        {
                            auctionCard = inputCard;
                            System.out.println("Going Auction");
                            Platform.runLater(() -> AuctionMenu.getInstance().getCardInAuction().getChildren().add(auctionCard.toShow()));
                        }
                        else
                        {
                            customCards.add(inputCard);
                        }
                    }
                    else if (inputObject instanceof String)
                    {
                        String inputMessage = ((String) inputObject);
                        System.out.println("Message Received From ... " + inputMessage);

                        if (inputMessage.matches("number .*"))
                        {
                            int cardNumber = Integer.parseInt(inputMessage.split(" ")[1]);
                            Label feathers = ShopMenu.getInstance().getFeathers();
                            ShopMenu.getInstance().setCardNumber(cardNumber);
                            Platform.runLater(() -> feathers.setText(feathers.getText() + "   Card Number : " + cardNumber + "   \n "));
                        }
                        else if (inputMessage.matches(".* is .*"))
                        {
                            String userName = inputMessage.split(" ")[0];

                            Platform.runLater(() -> Notifications.create()
                                    .position(Pos.TOP_CENTER)
                                    .darkStyle()
                                    .title(userName + "'s State :")
                                    .text("\n      " + inputMessage)
                                    .hideAfter(Duration.seconds(5))
                                    .show());

                        }
                        else if (inputMessage.matches("message .*"))
                        {
                            String[] messageParts = inputMessage.split(" ");
                            String messenger = messageParts[1];
                            String messageBody = "";
                            for (int i = 2; i < messageParts.length; i++)
                                messageBody = messageBody.concat(messageParts[i] + " ");

                            if (!(Controller.getInstance().getCurrentMenu() instanceof MainMenu))
                            {
                                String finalMessageBody = messageBody;
                                Platform.runLater(() -> Notifications.create()
                                        .position(Pos.TOP_CENTER)
                                        .darkStyle()
                                        .title(messenger + " Say :")
                                        .text("\n      " + finalMessageBody)
                                        .hideAfter(Duration.seconds(5))
                                        .show());
                            }

                            MainMenu mainMenu = MainMenu.getInstance();
                            String finalMessageBody1 = messageBody;
                            Platform.runLater(() -> mainMenu.getMessageArea().getChildren().add(new Message(messenger, finalMessageBody1)));

                        }
                        else if (inputMessage.matches("onlineusers .+"))
                        {
                            if (Controller.getInstance().getCurrentMenu() instanceof MainMenu)
                            {
                                ArrayList<String> onlineUsers = new ArrayList<>();
                                MainMenu mainMenu = ((MainMenu) Controller.getInstance().getCurrentMenu());

                                String[] stringParts = inputMessage.split(" ");
                                for (int i = 1; i < stringParts.length; i++)
                                    onlineUsers.add(stringParts[i]);

                                Player.getPlayers().sort((o1, o2) -> Integer.compare(o2.getWinNumber(), o1.getWinNumber()));

                                for (Player player : Player.getPlayers())
                                {
                                    VBox vBox = new ScoreBox(player);
                                    vBox.setSpacing(20);
                                    Label label = new Label();
                                    label.setMinHeight(50);
                                    if (onlineUsers.contains(player.getName()))
                                    {
                                        label.setText("      Online");
                                        label.setTextFill(Color.GREEN);
                                    }
                                    else
                                    {
                                        label.setText("      Offline");
                                        label.setTextFill(Color.RED);
                                    }
                                    label.setFont(Font.font("Leelawadee UI", 20));
                                    ((HBox) vBox.getChildren().get(1)).getChildren().add(label);
                                    Platform.runLater(() -> mainMenu.getScoreBoardPane().getChildren().add(vBox));
                                }
                            }
                        }
                        else if (inputMessage.matches("time .*"))
                        {
                            int i = Integer.parseInt(inputMessage.split(" ")[1]);
                            AuctionMenu auctionMenu = AuctionMenu.getInstance();

                            Platform.runLater(() ->
                            {
                                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), auctionMenu.getTimeRemaining().getChildren().get(1));

                                fadeTransition.setFromValue(0);
                                fadeTransition.setToValue(1);
                                fadeTransition.play();

                                if (i == 0)
                                    ((Label) auctionMenu.getTimeRemaining().getChildren().get(1)).setText("~Finished");
                                else
                                    ((Label) auctionMenu.getTimeRemaining().getChildren().get(1)).setText(" " + i + " seconds ");
                            });
                        }
                        else if (inputMessage.matches("another"))
                        {
                            Platform.runLater(() ->
                                    Notifications.create()
                                            .darkStyle()
                                            .title("Auction")
                                            .text("      Another One Suggested More Price")
                                            .position(Pos.TOP_CENTER)
                                            .hideAfter(Duration.seconds(5))
                                            .show());

                        }
                        else if (inputMessage.matches("sync max .*"))
                        {
                            String newMaxValue = inputMessage.split(" ")[2];
                            Platform.runLater(() ->
                            {
                                Label suggestValueLabel = ((Label) AuctionMenu.getInstance().getLastSuggest().getChildren().get(1));
                                suggestValueLabel.setText(newMaxValue);
                                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), suggestValueLabel);
                                fadeTransition.setFromValue(0);
                                fadeTransition.setToValue(1);
                                fadeTransition.play();
                            });
                        }
                        else if (inputMessage.matches(".* Won With Suggest .*"))
                        {
                            String[] messageParts = inputMessage.split(" ");

                            String playerWonName = messageParts[0];

                            if (Controller.getInstance().getCurrentPlayer().getName().equals(playerWonName))
                            {
                                String wonCardName = "";
                                int suggestValue = Integer.parseInt(messageParts[4]);
                                for (int i = 5; i < messageParts.length; i++)
                                    wonCardName = wonCardName.concat(messageParts[i] + " ");

                                wonCardName = wonCardName.trim();

                                ShopMenu shopMenu = ShopMenu.getInstance();
                                for (Card shopCard : shopMenu.getShopCards())
                                    if (shopCard.getName().equals(wonCardName))
                                    {
                                        Card clonedCard = null;
                                        try
                                        {
                                            clonedCard = shopMenu.cloneCard(shopCard);
                                        }
                                        catch (CloneNotSupportedException e)
                                        {
                                            e.printStackTrace();
                                        }

                                        Platform.runLater(() ->
                                        {
                                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "You Won Auction");
                                            alert.show();
                                        });

                                        Player playerWon = Controller.getInstance().getCurrentPlayer();

                                        clonedCard.setOwnerPlayer(playerWon);
                                        playerWon.getCollection().getCards().add(clonedCard);
                                        playerWon.decreaseCash(suggestValue);

                                        Client.getOutputStream().writeObject(Player.getPlayers());
                                        Client.getOutputStream().flush();
                                        Client.getOutputStream().reset();

                                        ShopMenu.getInstance().getMoney().setText(String.valueOf(Controller.getInstance().getCurrentPlayer().getCash()));
                                        break;
                                    }

                            }

                            Platform.runLater(() ->
                                    Notifications.create()
                                            .position(Pos.TOP_CENTER)
                                            .title("Auction")
                                            .text("      " + inputMessage)
                                            .hideAfter(Duration.seconds(5))
                                            .show());
                        }
                        else if (inputMessage.matches("increase auction .*"))
                        {
//               socketHandler.outputStream.writeObject("increase auction " + playerStartedAuction + maxSuggest + " " + auctionCard.getName());
                            String[] messageParts = inputMessage.split(" ");
                            String playerName = messageParts[2];
                            int cash = Integer.parseInt(messageParts[3]);
                            String cardName = "";
                            for (int i = 4; i < messageParts.length; i++)
                                cardName = cardName.concat(messageParts[i] + " ");

                            cardName = cardName.trim();

                            if (Controller.getInstance().getCurrentPlayer().getName().equals(playerName))
                            {
                                Player player = Controller.getInstance().getCurrentPlayer();

                                player.increaseCash(cash);
                                player.getCollection().removeFromCollection(cardName);

                                Platform.runLater(() ->
                                {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Add " + cash + " To Your Cash");
                                    alert.show();
                                });

                                AuctionMenu.getInstance().getNewSuggestOnAuctionTF().setVisible(true);
                                AuctionMenu.getInstance().getSuggestAuctionBTN().setVisible(true);

                                CollectionMenu.getInstance().initializeGridPanes();
                                ShopMenu.getInstance().getMoney().setText(String.valueOf(player.getCash()));

                                Client.getOutputStream().writeObject(Player.getPlayers());
                                Client.getOutputStream().flush();
                                Client.getOutputStream().reset();
                            }
                        }
                        else if (inputMessage.matches("auction .*"))
                        {
                            String auctionActivity = inputMessage.split(" ")[1];
                            AuctionMenu auctionMenu = AuctionMenu.getInstance();

                            switch (auctionActivity)
                            {
                                case "false":
                                    Platform.runLater(() ->
                                    {
                                        auctionMenu.getActiveAuctionPane().getChildren().clear();
                                        auctionMenu.getActiveAuctionPane().getChildren().add(auctionMenu.getGreenButton("Auction Available", 250));
                                        auctionMenu.getActiveAuctionPane().setPadding(new Insets(0, 0, 20, 0));
                                        ((Label) ((Pane) auctionMenu.getActiveAuctionPane().getChildren().get(0)).getChildren().get(1)).setTextFill(Color.LIGHTGREEN);

                                        if (isActive)
                                            Notifications.create()
                                                    .position(Pos.TOP_CENTER)
                                                    .title("Auction")
                                                    .text("\n      auction ended")
                                                    .hideAfter(Duration.seconds(5))
                                                    .show();

                                        isActive = false;
                                        auctionMenu.getFindCardTF().setDisable(false);
                                        auctionMenu.getFoundedCardPane().setDisable(false);
                                        auctionMenu.getAddCardToAuctionBTN().setDisable(false);
                                        auctionMenu.getFindInCollectionBTN().setDisable(false);

                                        auctionMenu.getTimeRemaining().setDisable(true);
                                        auctionMenu.getCardInAuction().setDisable(true);
                                        auctionMenu.getLastSuggest().setDisable(true);
                                        auctionMenu.getNewSuggestOnAuctionTF().setDisable(true);
                                        auctionMenu.getSuggestAuctionBTN().setDisable(true);
                                    });
                                    break;
                                case "true":
                                    Platform.runLater(() ->
                                    {
                                        auctionMenu.getActiveAuctionPane().getChildren().clear();
                                        auctionMenu.getActiveAuctionPane().getChildren().add(auctionMenu.getRedButton("Auction In Progress", 250));
                                        auctionMenu.getActiveAuctionPane().setPadding(new Insets(0, 0, 20, 0));
                                        ((Label) ((Pane) auctionMenu.getActiveAuctionPane().getChildren().get(0)).getChildren().get(1)).setTextFill(Color.RED);

                                        if (!isActive)
                                            Notifications.create()
                                                    .position(Pos.TOP_CENTER)
                                                    .title("Auction")
                                                    .text("\n      auction started")
                                                    .hideAfter(Duration.seconds(5))
                                                    .show();

                                        isActive = true;

                                        auctionMenu.getFindCardTF().setDisable(true);
                                        auctionMenu.getFoundedCardPane().setDisable(true);
                                        auctionMenu.getAddCardToAuctionBTN().setDisable(true);
                                        auctionMenu.getFindInCollectionBTN().setDisable(true);

                                        auctionMenu.getTimeRemaining().setDisable(false);
                                        auctionMenu.getCardInAuction().setDisable(false);
                                        auctionMenu.getLastSuggest().setDisable(false);
                                        auctionMenu.getNewSuggestOnAuctionTF().setDisable(false);
                                        auctionMenu.getSuggestAuctionBTN().setDisable(false);
                                    });
                                    break;
                            }

                        }
                    }
                }
                catch (Exception e)
                {
                    if (e instanceof ConnectionResetException)
                    {
                        Platform.runLater(() ->
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Connection Lost ...");
                            alert.show();
                        });
                        System.exit(0);
                    }
                    else
                    {}

                }
            }
        }).start();

        Platform.setImplicitExit(false);
        launch(args);
    }

//    private static VBox getScoreHBox(Player player)
//    {
//        VBox vBox = new VBox();
//
//
//        return vBox;
//    }

    static class ScoreBox extends VBox
    {
        ScoreBox(Player player)
        {
            Label userName = new Label();
            userName.setText("- " + player.getName());
            userName.setTextFill(Color.WHITE);

            this.getChildren().add(userName);

            Label winNumber = new Label();
            winNumber.setText("      Win : " + player.getWinNumber());
            Label loseNumber = new Label();
            loseNumber.setText("      Lose : " + player.getLoseNumber());

            userName.setMinHeight(50);
            userName.setMinWidth(300);

            winNumber.setMinHeight(50);
            winNumber.setMinWidth(100);
            winNumber.setTextFill(Color.CORNFLOWERBLUE);

            loseNumber.setMinHeight(50);
            loseNumber.setMinWidth(100);
            loseNumber.setTextFill(Color.GRAY);

            userName.setFont(Font.font("Leelawadee UI", 30));
            winNumber.setFont(Font.font("Leelawadee UI", 20));
            loseNumber.setFont(Font.font("Leelawadee UI", 20));

            HBox hBox = new HBox(5, winNumber, loseNumber);

            this.setStyle("-fx-background-color: rgba(0,0,0,0.20);-fx-background-radius: 40");
            this.setMaxWidth(350);
            this.setAlignment(Pos.CENTER);

            this.getChildren().add(hBox);
        }
    }

    static class Message extends HBox
    {
        Label messageLabel = new Label();
        Label messengerLabel = new Label();

        Message(String messenger, String messageBody)
        {
            super(5);

            messengerLabel.setText(messenger);
            messengerLabel.setTextFill(Color.WHITE);
            messengerLabel.setMinWidth(78);
            messengerLabel.setMinHeight(50);
            messengerLabel.setTextAlignment(TextAlignment.CENTER);
            messengerLabel.setAlignment(Pos.CENTER);
            messengerLabel.setFont(Font.font("Leelawadee UI", 20));
            messengerLabel.setStyle("-fx-background-color: Black;-fx-opacity: .8;-fx-background-radius: 15");
            this.getChildren().add(messengerLabel);

            messageLabel.setText("  " + messageBody);
            messageLabel.setTextAlignment(TextAlignment.CENTER);
            messageLabel.setAlignment(Pos.BASELINE_LEFT);
            messageLabel.setMinWidth(560);
            messageLabel.setMinHeight(50);
            messageLabel.setTextFill(Color.WHITE);
            messageLabel.setFont(Font.font("Leelawadee UI", 18));
            messageLabel.setStyle("-fx-background-color: Black;-fx-opacity: .8;-fx-background-radius: 15");

            this.setStyle("-fx-background-color: rgb(171,0,45);-fx-background-radius: 15;-fx-opacity: .5");
            this.setMaxWidth(650);
            this.setMinWidth(650);
            this.setMinHeight(60);
            this.setMaxHeight(60);

            this.getChildren().add(messageLabel);

            this.setAlignment(Pos.CENTER);

            messageLabel.setOnMouseEntered(event ->
            {
                this.setOnMouseEntered(mouseEvent -> this.setEffect(new Glow(30)));
                this.setOnMouseExited(mouseEvent -> this.setEffect(new Glow(0)));
            });

        }

        public void playAnimation()
        {
            FadeTransition fadeMessage = new FadeTransition(Duration.seconds(.5), this);
            fadeMessage.setFromValue(0);
            fadeMessage.setToValue(1);
            fadeMessage.play();
        }
    }

    public static ObjectInputStream getInputStream()
    {
        return inputStream;
    }

    public static ObjectOutputStream getOutputStream()
    {
        return outputStream;
    }

    public static ArrayList<Card> getShopCards()
    {
        return shopCards;
    }
}

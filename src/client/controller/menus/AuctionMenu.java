package client.controller.menus;

import client.Client;
import client.controller.Controller;
import client.models.Player;
import client.models.widget.cards.Card;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class AuctionMenu extends AbstractMenu
{
    private static AuctionMenu instance = new AuctionMenu();

    private AuctionMenu()
    {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask()
        {
            int i = Integer.MAX_VALUE;

            @Override
            public void run()
            {
                if (i % 2 == 0)
                {
                    try
                    {
                        Client.getOutputStream().writeObject("auction isactive");
                        Client.getOutputStream().flush();
                        Client.getOutputStream().reset();

                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                i--;
            }
        };

        timer.schedule(timerTask, 0, 1000);

        initializeMenuGUI();
    }

    public static AuctionMenu getInstance()
    {
        return instance;
    }

    private Pane timeRemaining = getYellowButton("", 250);
    private Pane lastSuggest = getPaneInAddress("file:Css/pictures/New folder (3)/ui/notification_quest_small@2x.png", "", 250);
    private Pane cardInAuction = new Pane();
    private Label cardInActionLabel = new Label();
    private TextField newSuggestOnAuctionTF = new TextField();
    private Pane suggestAuctionBTN =
            getGreenButton("Submit Suggest");

    private Pane activeAuctionPane = new StackPane();
    private TextField findCardTF = new TextField();
    private Pane findInCollectionBTN = getBlueButton("Find Card");
    private Pane foundedCardPane = new StackPane();
    private Card foundedCard;
    private Pane addCardToAuctionBTN = getGreenButton("Auction Card");

    private Pane backBTN = getRedButton("Back");

    public TextField getFindCardTF()
    {
        return findCardTF;
    }

    public Pane getFindInCollectionBTN()
    {
        return findInCollectionBTN;
    }

    public Card getFoundedCard()
    {
        return foundedCard;
    }

    public Pane getAddCardToAuctionBTN()
    {
        return addCardToAuctionBTN;
    }

    BorderPane borderPane;

    public BorderPane getBorderPane()
    {
        return borderPane;
    }


    @Override
    public void initializeMenuGUI()
    {
        borderPane = new BorderPane();
        borderPane.setStyle("");
        borderPane.getChildren().add(getBackGround("file:bg7.jpg"));

        borderPane.setLeft(getLeft());
        BorderPane.setAlignment(borderPane.getLeft(), Pos.CENTER);

        borderPane.setRight(getRight());
        BorderPane.setAlignment(borderPane.getRight(), Pos.CENTER);

        setGlowEffects(backBTN);
        backBTN.setPadding(new Insets(0,0,100,0));
        backBTN.setOnMouseClicked(event -> Controller.getInstance().changeCurrentMenuTo(ShopMenu.getInstance()));

        borderPane.setBottom(backBTN);
        BorderPane.setAlignment(borderPane.getBottom(), Pos.CENTER);

        this.scene = new Scene(borderPane, Controller.getInstance().getWeight(), Controller.getInstance().getHeight());
        changeCursorImage("file:Css/pictures/New folder (3)/ui/mouse_auto.png");
    }

    private Node getRight()
    {
        VBox vBox = new VBox(10);

        ((Label) timeRemaining.getChildren().get(1)).setFont(Font.font("Leelawadee UI", 24));
        ((Label) timeRemaining.getChildren().get(1)).setTextFill(Color.YELLOW);

        vBox.getChildren().addAll(timeRemaining, cardInAuction, lastSuggest, newSuggestOnAuctionTF, suggestAuctionBTN);

        suggestAuctionBTN.setOnMouseClicked(event ->
        {
            if (newSuggestOnAuctionTF.getText().matches("\\d+"))
            {
                int suggestedValue = Integer.parseInt(newSuggestOnAuctionTF.getText());
                try
                {
                    Player currentPlayer = Controller.getInstance().getCurrentPlayer();
                    Client.getOutputStream().writeObject("suggest " + currentPlayer.getName() + " " + suggestedValue);
                    Client.getOutputStream().flush();
                    Client.getOutputStream().reset();
                    newSuggestOnAuctionTF.clear();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Suggest");
                alert.show();
            }
        });

        vBox.setPadding(new Insets(0, 100, 0, 0));
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    public Node getLeft()
    {
        VBox vBox = new VBox(10);

        foundedCardPane.setMinWidth(150);
        foundedCardPane.setMaxWidth(150);
        foundedCardPane.setMinHeight(30);

        try
        {
            Client.getOutputStream().writeObject("auction isactive");
            Client.getOutputStream().flush();
            Client.getOutputStream().reset();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        findCardTF.setMinWidth(170);
        findCardTF.setMaxWidth(170);
        findCardTF.setMinHeight(40);
        findCardTF.setStyle("-fx-border-radius: 20;-fx-opacity: .4");

        findInCollectionBTN.setOnMouseClicked(event ->
        {
            foundedCardPane.getChildren().clear();

            String cardName = findCardTF.getText();
            Card card = Controller.getInstance().getCurrentPlayer().getCollection().findCardInCollection(cardName);
            findCardTF.clear();
            if (card == null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Card Not Found");
                alert.show();
                return;
            }

            foundedCardPane.getChildren().add(card.toShow());
            foundedCard = card;
        });

        addCardToAuctionBTN.setOnMouseClicked(event ->
        {
            if (foundedCard != null)
            {
                try
                {
                    Client.getOutputStream().writeObject(foundedCard);
                    Client.getOutputStream().flush();
                    Client.getOutputStream().reset();
                    Client.getOutputStream().writeObject("player start auction " + Controller.getInstance().getCurrentPlayer().getName());
                    Client.getOutputStream().flush();
                    Client.getOutputStream().reset();

                    newSuggestOnAuctionTF.setVisible(false);
                    suggestAuctionBTN.setVisible(false);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please Set Card To Auction");
                alert.show();
            }
        });

        activeAuctionPane.setMinWidth(215);
        activeAuctionPane.setMaxWidth(215);

        setGlowEffects(activeAuctionPane, findCardTF, findInCollectionBTN, foundedCardPane, addCardToAuctionBTN);

        Label syncInformation = new Label();
        syncInformation.setText("... will sync every 5 seconds");
        syncInformation.setFont(Font.font("Tahoma", 18));

        vBox.getChildren().addAll(syncInformation, activeAuctionPane, findCardTF, findInCollectionBTN, foundedCardPane, addCardToAuctionBTN);
        vBox.setPadding(new Insets(0, 0, 0, 100));
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    public Pane getActiveAuctionPane()
    {
        return activeAuctionPane;
    }

    public Pane getFoundedCardPane()
    {
        return foundedCardPane;
    }

    public Pane getCardInAuction()
    {
        return cardInAuction;
    }

    public Label getCardInActionLabel()
    {
        return cardInActionLabel;
    }

    public Pane getTimeRemaining()
    {
        return timeRemaining;
    }

    public static void setInstance(AuctionMenu instance)
    {
        AuctionMenu.instance = instance;
    }

    public void setTimeRemaining(Pane timeRemaining)
    {
        this.timeRemaining = timeRemaining;
    }

    public Pane getLastSuggest()
    {
        return lastSuggest;
    }

    public void setLastSuggest(Pane lastSuggest)
    {
        this.lastSuggest = lastSuggest;
    }

    public void setCardInAuction(Pane cardInAuction)
    {
        this.cardInAuction = cardInAuction;
    }

    public void setCardInActionLabel(Label cardInActionLabel)
    {
        this.cardInActionLabel = cardInActionLabel;
    }

    public TextField getNewSuggestOnAuctionTF()
    {
        return newSuggestOnAuctionTF;
    }

    public void setNewSuggestOnAuctionTF(TextField newSuggestOnAuctionTF)
    {
        this.newSuggestOnAuctionTF = newSuggestOnAuctionTF;
    }

    public Pane getSuggestAuctionBTN()
    {
        return suggestAuctionBTN;
    }

    public void setSuggestAuctionBTN(Pane suggestAuctionBTN)
    {
        this.suggestAuctionBTN = suggestAuctionBTN;
    }

    public void setActiveAuctionPane(Pane activeAuctionPane)
    {
        this.activeAuctionPane = activeAuctionPane;
    }

    public void setFindCardTF(TextField findCardTF)
    {
        this.findCardTF = findCardTF;
    }

    public void setFindInCollectionBTN(Pane findInCollectionBTN)
    {
        this.findInCollectionBTN = findInCollectionBTN;
    }

    public void setFoundedCardPane(Pane foundedCardPane)
    {
        this.foundedCardPane = foundedCardPane;
    }

    public void setFoundedCard(Card foundedCard)
    {
        this.foundedCard = foundedCard;
    }

    public void setAddCardToAuctionBTN(Pane addCardToAuctionBTN)
    {
        this.addCardToAuctionBTN = addCardToAuctionBTN;
    }
}

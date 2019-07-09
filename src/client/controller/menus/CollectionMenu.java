package client.controller.menus;

import client.models.widget.cards.Warriors.Hero;
import client.models.widget.cards.Warriors.Minion;
import client.models.widget.cards.spells.Passive;
import client.models.widget.cards.spells.Spell;
import com.jniwrapper.Str;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import client.controller.Controller;
import client.controller.Exceptions.*;
import client.models.Player;
import client.models.widget.cards.Card;

import java.io.*;
import java.util.ArrayList;

public class CollectionMenu extends AbstractMenu implements Serializable
{
    private static CollectionMenu ourInstance = new CollectionMenu();
    private boolean isNewDeck;
    private boolean isInCreateDeck;
    private Pane exit = getRedButton("Exit");
    private Pane newDeck = getGreenButton("New Deck");
    TextField textField = new TextField();
    private Pane exportDeck = getBlueButton("Export");
    private Pane importDeck = getBlueButton("Import");
    private Pane save = getBlueButton("Save");
    private BorderPane borderPane = new BorderPane();
    TextField name = new TextField();
    private ArrayList<GridPane> gridPanes = new ArrayList<>();
    ScrollPane decksScroll = new ScrollPane();
    private Player.Deck currentDeck1;
    CheckBox checkBox = new CheckBox("Select As Main Deck");

    private int page = 1;

    public static CollectionMenu getInstance()
    {
        return ourInstance;
    }

    private CollectionMenu()
    {
        initializeGridPanes();
        initializeMenuGUI();
        ShopMenu.getInstance().addDefaultDeckToPlayer(Controller.getInstance().getCurrentPlayer());
    }

    @Override
    public void initializeMenuGUI()
    {
        borderPane.getChildren().add(getBackGround("bg3.png"));

        borderPane.setLeft(getLeft());

        borderPane.setCenter(gridPanes.get(0));
        borderPane.setRight(getRight1());
        borderPane.setBottom(getBottom());
        BorderPane.setAlignment(borderPane.getCenter(), Pos.CENTER);
        BorderPane.setAlignment(borderPane.getRight(), Pos.CENTER);
        BorderPane.setAlignment(borderPane.getBottom(), Pos.CENTER);
        BorderPane.setAlignment(borderPane.getLeft(), Pos.CENTER);

        gridPanes.get(0).setPadding(new Insets(0, 0, 0, 200));

        this.scene = new Scene(borderPane, Controller.getInstance().getWeight(), Controller.getInstance().getHeight());
        changeCursorImage("file:Css/pictures/New folder (3)/ui/mouse_auto.png");

    }

    private Parent getBottom()
    {
        HBox hBox = new HBox(60);
        hBox.setAlignment(Pos.CENTER);
        hBox.setStyle("-fx-padding: 0 0 100 0;");

        ImageView rightImage = new ImageView(new Image("file:Css/pictures/New folder (3)/ui/dialogue_carat@2x.png"));
        ImageView leftImage = new ImageView(new Image("file:Css/pictures/New folder (3)/ui/dialogue_carat@2x.png"));
        leftImage.setRotate(leftImage.getRotate() + 180);
        setGlowEffects(rightImage, leftImage);

        rightImage.setOnMouseClicked(mouseEvent ->
        {
            if (page < gridPanes.size())
                page++;
            borderPane.setCenter(gridPanes.get(page - 1));
        });
        leftImage.setOnMouseClicked(mouseEvent ->
        {
            if (page != 1)
                page--;

            borderPane.setCenter(gridPanes.get(page - 1));
        });
        hBox.getChildren().addAll(leftImage, rightImage);

        return hBox;
    }

    private Parent getRight1()
    {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-padding: 50 100 0 0;");

        borderPane.setCenter(decksScroll);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);

        decksScroll.setContent(vBox);
        decksScroll.setFitToWidth(true);

        Label label = new Label("Decks");
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("Leelawadee UI", 30));

        vBox.getChildren().add(label);

        for (Player.Deck deck : Controller.getInstance().getCurrentPlayer().getDecks())
        {
            HBox hBox = getDeckHb(deck);
            vBox.getChildren().add(hBox);
        }

        textField.setPromptText("Deck Name");
        textField.setMaxWidth(150);
        textField.setAlignment(Pos.CENTER);

        importDeck.setOnMouseClicked(event ->
        {
            String deckName = textField.getText();
            textField.clear();
            if (!deckName.equals(""))
            {
                File file = new File("src/client/decks/" + deckName + ".txt");
                if (file.exists())
                {
                    try
                    {
                        InputStream inputStream = new FileInputStream(file);
                        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                        Player.Deck deck = ((Player.Deck) objectInputStream.readObject());

                        Player player = Controller.getInstance().getCurrentPlayer();

                        ArrayList<Card> newDeckCards = new ArrayList<>();

                        Player.Collection collection = player.getCollection();

                        for (Card card : deck.getCards())
                        {
                            Card foundResult = collection.findCardInCollection(card.getName());
                            if (foundResult == null)
                            {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "You Dont Have All Of This Deck Cards");
                                alert.show();
                                return;
                            }
                            else if (foundResult instanceof Spell || foundResult instanceof Minion)
                                newDeckCards.add(foundResult);

                        }

                        Player.Deck newDeck = player.new Deck(
                                ((Hero) collection.findCardInCollection(deck.getHero().getName())),
                                newDeckCards,
                                ((Passive) collection.findCardInCollection(deck.getPassiveItem().getName())));

                        newDeck.setName(deckName);
                        player.getDecks().add(newDeck);


                        HBox hBox = getDeckHb(deck);
                        vBox.getChildren().add(hBox);
                    }
                    catch (FileNotFoundException | ClassNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IOException e)
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "No Deck With This Name Imported Already");
                        alert.show();
                    }
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "No Deck With This Name Imported Already");
                    alert.show();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Enter Deck Name");
                alert.show();
            }
        });

        exportDeck.setOnMouseClicked(event ->
        {
            String deckName = textField.getText();
            textField.clear();
            if (!deckName.equals(""))
            {
                Player player = Controller.getInstance().getCurrentPlayer();

                Player.Deck intendedDeck = null;
                for (Player.Deck deck : player.getDecks())
                    if (deck.getName().equals(deckName))
                        intendedDeck = deck;

                if (intendedDeck != null)
                {
                    File file = new File("src/client/decks/" + deckName + ".txt");
                    if (!file.exists())
                    {
                        try
                        {
                            file.createNewFile();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    try
                    {
                        OutputStream outputStream = new FileOutputStream(file);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                        objectOutputStream.writeObject(intendedDeck);
                        objectOutputStream.flush();
                        objectOutputStream.reset();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Deck Not Found");
                    alert.show();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Enter Deck Name");
                alert.show();
            }
        });

        VBox deckButtons = new VBox(5, newDeck, textField, exportDeck, importDeck);
        deckButtons.setAlignment(Pos.CENTER);
        borderPane.setBottom(deckButtons);
        BorderPane.setAlignment(borderPane.getCenter(), Pos.CENTER);
        BorderPane.setAlignment(borderPane.getBottom(), Pos.CENTER);

        setGlowEffects(newDeck, exportDeck, importDeck);

        newDeck.setOnMouseClicked(mouseEvent ->
        {
            currentDeck1 = Controller.getInstance().getCurrentPlayer().new Deck("");
            isInCreateDeck = true;
            name.setText("");
            isNewDeck = true;
            this.borderPane.setRight(getRight2());
        });

        return borderPane;

    }

    private Parent getRight2()
    {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-padding: 50 100 0 0;");

        borderPane.setCenter(decksScroll);

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);

        name.setAlignment(Pos.CENTER);
        name.setMaxWidth(150);
        name.setAlignment(Pos.CENTER);
        name.setPromptText("Enter Name");
        name.setStyle("-fx-background-radius: 100;-fx-border-style: Solid; -fx-border-color: #006a7d;-fx-border-width: 2;-fx-border-radius: 100");
        name.setFont(Font.font("Leelawadee UI", 16));

        checkBox.setAlignment(Pos.CENTER);
        checkBox.setFont(Font.font("Leelawadee UI", 16));

        Label label = new Label("Cards");
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("Leelawadee UI", 30));

        ArrayList<HBox> cardsInDeck = new ArrayList<>();
        fillHboxsOfCards(cardsInDeck);

        vBox.getChildren().addAll(name, checkBox, label);
        vBox.getChildren().addAll(cardsInDeck);

        decksScroll.setContent(vBox);
        decksScroll.setFitToWidth(true);

        VBox buttons = new VBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(save);
        borderPane.setBottom(buttons);

        BorderPane.setAlignment(borderPane.getCenter(), Pos.CENTER);
        BorderPane.setAlignment(borderPane.getBottom(), Pos.CENTER);

        setGlowEffects(save);

        save.setOnMouseClicked(mouseEvent ->
        {
            try
            {
                saveDeck();
            }
            catch (RepeatedDeckName repeatedDeckName)
            {
                showAlert(repeatedDeckName.getMessage());
            }
        });

        return borderPane;
    }

    private void fillHboxsOfCards(ArrayList<HBox> cardsInDeck)
    {
        if (currentDeck1.getHero() != null)
            cardsInDeck.add(getDeckCardsHb(currentDeck1.getHero()));
        if (currentDeck1.getPassiveItem() != null)
            cardsInDeck.add(getDeckCardsHb(currentDeck1.getPassiveItem()));
        for (Card card : currentDeck1.getCards())
        {
            cardsInDeck.add(getDeckCardsHb(card));
        }
    }

    private HBox getDeckCardsHb(Card card)
    {
        HBox hBox = new HBox(5);
        hBox.setAlignment(Pos.CENTER);

        ImageView deleteCard = new ImageView(new Image("file:Css/pictures/New folder (3)/ui/button_close.png"));
        deleteCard.setPreserveRatio(true);
        deleteCard.setFitHeight(40);
        deleteCard.setOnMouseClicked(mouseEvent ->
        {
            currentDeck1.removeCardFromDeck(card);
            borderPane.setRight(getRight2());
        });

        Label label = new Label(card.getName());
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("Leelawadee UI", 16));

        hBox.getChildren().addAll(label, deleteCard);
        return hBox;

    }

    private void saveDeck() throws RepeatedDeckName
    {
        if (!name.getText().equals(""))
        {
            if (isNewDeck)
            {
                if (Controller.getInstance().getCurrentPlayer().findDeck(name.getText()) != null)
                    throw new RepeatedDeckName();

                currentDeck1.setName(name.getText());
                Controller.getInstance().getCurrentPlayer().getDecks().add(currentDeck1);
            }
            else
            {
                if (!name.getText().equals(currentDeck1.getName()) && Controller.getInstance().getCurrentPlayer().findDeck(name.getText()) != null)
                    throw new RepeatedDeckName();
                currentDeck1.setName(name.getText());
            }
            if (checkBox.isSelected())
            {
                Controller.getInstance().getCurrentPlayer().setMainDeck(currentDeck1);
            }
            borderPane.setRight(getRight1());
            isNewDeck = false;
            isInCreateDeck = false;
            checkBox.setSelected(false);
        }
    }

    private VBox getCardVbox(Card card)
    {
        VBox vBox = new VBox(10);
        Button sellBt = new Button("Sell");
        sellBt.setStyle("-fx-background-color: #001f7d;-fx-background-radius: 5;-fx-border-style: Solid; -fx-border-color: #9bbbff;-fx-border-radius: 5");
        sellBt.setTextFill(Color.WHITE);
        Pane pane = card.toShow();
        sellBt.setPrefWidth(150);
        vBox.getChildren().addAll(pane, sellBt);
        setEffects(pane, sellBt, card);
        return vBox;
    }

    private void setEffects(Pane cardPane, Button sellBt, Card card)
    {

        cardPane.setOnMouseEntered(mouseEvent -> cardPane.setEffect(new Glow(30)));
        cardPane.setOnMouseExited(mouseEvent -> cardPane.setEffect(new Glow(0)));
        sellBt.setOnMouseEntered(mouseEvent -> sellBt.setEffect(new Glow(30)));
        sellBt.setOnMouseExited(mouseEvent -> sellBt.setEffect(new Glow(0)));

        cardPane.setOnMouseClicked(mouseEvent ->
        {
            if (isInCreateDeck)
            {
                try
                {
                    currentDeck1.addCardToDeck(card);
                }
                catch (Exception e)
                {
                    showAlert(e.getMessage());
                }
                borderPane.setRight(getRight2());
            }
        });

        sellBt.setOnMouseClicked(mouseEvent ->
        {

            if (!isInCreateDeck)
                sellCard(card);

        });
    }

    private Parent getLeft()
    {
        VBox vBox = new VBox(300);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(200, 50, 50, 120));

        VBox buttonsVb = new VBox(20);
        buttonsVb.getChildren().addAll(exit);
        setGlowEffects(exit);

        exit.setOnMouseClicked(mouseEvent -> Controller.getInstance().getStage().setScene(MainMenu.getInstance().getScene()));

        vBox.getChildren().addAll(buttonsVb);

        return vBox;
    }

    public void initializeGridPanes()
    {
        gridPanes.clear();

        Player player = Controller.getInstance().getCurrentPlayer();
        ArrayList<Card> cards = player.getCollection().getCards();

        int number1 = cards.size() / 8;
        int number2 = cards.size() % 8;
        int counter = 0;

        while (counter < number1)
        {
            GridPane gridPane = new GridPane();
            gridPane.setVgap(10);
            gridPane.setStyle("-fx-padding: 200 0 0 50;-fx-vgap: 20;-fx-hgap: 20");
            for (int i = 0; i < 8; i++)
            {
                if (i <= 3)
                    gridPane.add(getCardVbox(cards.get(counter * 8 + i)), i, 0);
                else
                    gridPane.add(getCardVbox(cards.get(counter * 8 + i)), i - 4, 1);

            }
            counter++;
            gridPanes.add(gridPane);
        }

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setStyle("-fx-padding: 200 0 0 50;");
        gridPane.setHgap(10);

        for (int i = 0; i < number2; i++)
        {

            if (i <= 3)
            {
                gridPane.add(getCardVbox(cards.get(i + number1 * 8)), i, 0);
            }
            else
                gridPane.add(getCardVbox(cards.get(i + number1 * 8)), i - 4, 1);
        }

        gridPanes.add(gridPane);
        borderPane.setCenter(gridPanes.get(0));
        borderPane.setRight(getRight1());
    }

    private void setDeckEffect(Pane pane, Player.Deck deck, ImageView imageView)
    {
        if (Controller.getInstance().getCurrentPlayer().getMainDeck() == deck)
            pane.setStyle("-fx-background-color:#A9E829;");
        else
            pane.setStyle("");
        pane.setOnMouseEntered(mouseEvent -> pane.setEffect(new Glow(30)));
        pane.setOnMouseExited(mouseEvent -> pane.setEffect(new Glow(0)));
        pane.setOnMouseClicked(mouseEvent ->
        {
            isInCreateDeck = true;
            name.setText(deck.getName());
            currentDeck1 = deck;
            isNewDeck = false;
            borderPane.setRight(getRight2());
        });
        imageView.setOnMouseClicked(mouseEvent ->
        {
            Controller.getInstance().getCurrentPlayer().removeDeck(deck);
            borderPane.setRight(getRight1());
        });

    }

    private void sellCard(Card card)
    {
        Player player = Controller.getInstance().getCurrentPlayer();

        player.increaseCash(card.getPrice() / 2);
        player.getCollection().removeFromCollection(card);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Succesfull Sold");
        alert.show();

        initializeGridPanes();
        ShopMenu.getInstance().getMoney().setText(String.valueOf(player.getCash()));
        borderPane.setRight(getRight1());

    }

    private HBox getDeckHb(Player.Deck deck)
    {
        HBox hBox = new HBox();
        ImageView deleteDeck = new ImageView(new Image("file:Css/pictures/New folder (3)/ui/bracket_enemy@2x.png"));
        deleteDeck.setPreserveRatio(true);
        deleteDeck.setFitHeight(75);

        Pane pane = deck.toShowDeck();
        hBox.getChildren().addAll(pane, deleteDeck);
        setDeckEffect(pane, deck, deleteDeck);
        return hBox;
    }

    private void showAlert(String content)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(content);

        alert.showAndWait();
    }
}

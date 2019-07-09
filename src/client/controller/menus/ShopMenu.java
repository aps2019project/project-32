package client.controller.menus;

import client.Client;
import client.controller.AnimationGIFSPack;
import javafx.animation.FadeTransition;
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
import client.controller.Exceptions.RepeatedDeckName;
import client.models.Player;
import client.models.widget.cards.Card;
import client.models.widget.cards.Warriors.AttackType;
import client.models.widget.cards.Warriors.Hero;
import client.models.widget.cards.Warriors.Minion;
import client.models.widget.cards.Warriors.Warrior;
import client.models.widget.cards.spells.*;
import client.models.widget.cards.spells.effects.*;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ShopMenu extends AbstractMenu implements Serializable
{
    private final String breathFirstHeroAddress = "file:Css/pictures/New folder (3)/unit_gifs/boss_spelleater_breathing.gif";
    private final String attackFirstHeroAddress = "sprites/hero1attack.gif";
    private final String breathSecondHeroAddress = "file:Css/pictures/New folder (3)/unit_gifs/boss_shadowlord_breathing.gif";
    private final String attackSecondHeroAddress = "sprites/hero2attack.gif";
    private final String breathThirdHeroAddress = "file:Css/pictures/New folder (3)/unit_gifs/f5_tier2general_breathing.gif";
    private final String attackThirdHeroAddress = "sprites/hero3attack.gif";

    private final String firstMinionGifAddress = "file:Css/pictures/New folder (3)/unit_gifs/boss_andromeda_breathing.gif";
    private final String firstMinionAttackGIFAddress = "sprites/minion1attack.gif";
    private final String secondMinionGifAddress = "file:Css/pictures/New folder (3)/unit_gifs/boss_cindera_breathing.gif";
    private final String secondMinionAttackGIFAddress = "sprites/minion2attack.gif";
    private final String thirdMinionGifAddress = "file:Css/pictures/New folder (3)/unit_gifs/f4_maehvmk2_breathing.gif";
    private final String thirdMinionAttackGIFAddress = "sprites/minion3attack.gif";

    private BorderPane borderPane;

    public BorderPane getBorderPane()
    {
        return borderPane;
    }

    private static ShopMenu instance = new ShopMenu();
    private ArrayList<Card> shopCards = new ArrayList<>();
    private ArrayList<Spell> collectibleCards = new ArrayList<>();
    private Pane collection = getBlueButton("Collection");
    private Pane createCardBTN = getGreenButton("Custom Card");

    private Label feathers = new Label("");
    private Pane cardOnEnter = new Pane();

    private Card searchedCard;

    private Integer cardNumber = null;

    private TextField searchCard = new TextField();
    private Pane buyPane = getGreenButton("Buy");
    private Pane auction = getYellowButton("Auction");
    private Pane exit = getRedButton("Exit");
    private Label money = new Label();

    public Label getMoney()
    {
        return money;
    }

    public static ShopMenu getInstance()
    {
        return instance;
    }

    public ArrayList<Card> getShopCards()
    {
        return shopCards;
    }

    private ShopMenu()
    {
        initializeMenuGUI();
        initialiseShop();
    }

    HashMap<String, Integer> cardNameToCardNumber = new HashMap<>();

    @Override
    public void initializeMenuGUI()
    {
        borderPane = new BorderPane();
        borderPane.setStyle("");
        borderPane.getChildren().add(getBackGround("file:bg14.jpg"));

        borderPane.setPadding(new Insets(18));

        borderPane.setLeft(getLeft());
        borderPane.setCenter(getCenter());
        borderPane.setRight(getRight());
        BorderPane.setAlignment(borderPane.getLeft(), Pos.CENTER);
        BorderPane.setAlignment(borderPane.getCenter(), Pos.TOP_CENTER);
        BorderPane.setAlignment(borderPane.getRight(), Pos.BOTTOM_RIGHT);

        this.scene = new Scene(borderPane, Controller.getInstance().getWeight(), Controller.getInstance().getHeight());
        changeCursorImage("file:Css/pictures/New folder (3)/ui/mouse_auto.png");

    }

    private Parent getRight()
    {
        VBox vBox = new VBox(330);

        VBox buttons = new VBox(20);
        buttons.setPadding(new Insets(0, 100, 180, 0));
        searchCard.setMaxWidth(240);

        Label label = new Label("                 Search Card");
        label.setFont(Font.font("Leelawadee UI", 14));
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);

        buttons.getChildren().addAll(label, searchCard, buyPane, auction, exit);
        setGlowEffects(money, collection, buyPane, auction, createCardBTN, exit);

        exit.setOnMouseClicked(mouseEvent -> Controller.getInstance().changeCurrentMenuTo(MainMenu.getInstance()));

        auction.setOnMouseClicked(event -> Controller.getInstance().changeCurrentMenuTo(AuctionMenu.getInstance()));

        collection.setOnMouseClicked(mouseEvent ->
        {
            CollectionMenu.getInstance().initializeGridPanes();
            Controller.getInstance().changeCurrentMenuTo(CollectionMenu.getInstance());
        });

        createCardBTN.setOnMouseClicked(event ->
        {
            Controller.getInstance().changeCurrentMenuTo(CustomCardMenu.getInstance());
            try
            {
                Client.getOutputStream().writeObject("incustomcardmenu");
                Client.getOutputStream().flush();
                Client.getOutputStream().reset();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
        createCardBTN.setPadding(new Insets(0, 100, 0, 0));

        searchCard.setPromptText("Type Card Name...");
        searchCard.setOnKeyPressed(event ->
        {
            if (event.getCode() == KeyCode.ENTER)
            {
                Card foundedCard = searchCard(searchCard.getText());
                if (foundedCard == null)
                {
                    showWarningAlert("Card Not Found");
                    return;
                }

                searchedCard = foundedCard;

//                FadeTransition transition = new FadeTransition(Duration.seconds(1000), feathers);
//                transition.setFromValue(0);
//                transition.setToValue(1);
//                transition.play();

                feathers.setText("\n" + getFeathersText
                        (searchedCard) + "   Card Number : " + searchedCard.getCardsNumber() + "   \n ");

                searchCard.clear();
            }
        });

        buyPane.setOnMouseClicked(event ->
        {
            try
            {
                buy(searchedCard);
                money.setText(String.valueOf(Controller.getInstance().getCurrentPlayer().getCash()));
            }
            catch (CloneNotSupportedException e)
            {
                e.printStackTrace();
            }
        });

        HBox moneyHbox = new HBox();
        moneyHbox.setPadding(new Insets(50, 30, 0, 0));
        ImageView imageView = new ImageView(new Image("file:Css/pictures/New folder (3)/shop/shop_premium_pack_ultimate.png"));
        imageView.setFitHeight(60);
        imageView.setPreserveRatio(true);

        money.setText(String.valueOf(Controller.getInstance().getCurrentPlayer().getCash()));
        money.setTextFill(Color.WHITE);
        money.setFont(Font.font("Leelawadee UI", 40));
        money.setAlignment(Pos.CENTER);
        moneyHbox.getChildren().addAll(imageView, money);
        money.setPadding(new Insets(0, 100, 0, 0));

        moneyHbox.setAlignment(Pos.CENTER);

        collection.setPadding(new Insets(0, 100, 0, 0));

        VBox topRightBox = new VBox(20, moneyHbox, collection, createCardBTN);
        vBox.getChildren().addAll(topRightBox, buttons);

        return vBox;
    }

    private Parent getCenter()
    {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);

        feathers.setFont(Font.font("Leelawadee UI", 30));
        feathers.setStyle("-fx-background-color: Black;-fx-opacity: .5;-fx-background-radius: 20");
        feathers.setTextFill(Color.WHITE);
        feathers.setTextAlignment(TextAlignment.CENTER);
        feathers.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(cardOnEnter, feathers);

        return vBox;
    }

    public Parent getLeft()
    {
        HBox herosHb = getCardHbox("Heros");
        HBox minionsHb = getCardHbox("Minions");
        HBox spellsHb = getCardHbox("Spells");
        HBox passivesHb = getCardHbox("Passives");
        VBox vBox = new VBox(20);
        vBox.getChildren().addAll(herosHb, minionsHb, spellsHb, passivesHb);

        vBox.setPadding(new Insets(20));

        return vBox;
    }

    private HBox getCardHbox(String type)
    {
        HBox hBox = new HBox(12);

        HBox cardsHb = new HBox();
        ScrollPane scrollPane = new ScrollPane();

        for (Card shopCard : getShopCards())
        {
            Pane pane = null;
            switch (type)
            {
                case "Heros":
                {
                    if (shopCard instanceof Hero)
                        pane = new Pane(shopCard.toShow());
                    break;
                }
                case "Minions":
                {
                    if (shopCard instanceof Minion)
                        pane = new Pane(shopCard.toShow());
                    break;
                }
                case "Spells":
                {
                    if (shopCard instanceof Spell && ((Spell) shopCard).getType() == Type.NormalSpell)
                        pane = new Pane(shopCard.toShow());
                    break;
                }
                case "Passives":
                {
                    if (shopCard instanceof Passive)
                        pane = new Pane(shopCard.toShow());
                    break;
                }
            }
            if (pane != null)
            {
                VBox vBox = new VBox();
                vBox.setAlignment(Pos.CENTER);
                setAllCardEffects(pane, shopCard);
                vBox.getChildren().addAll(pane);
                cardsHb.getChildren().add(vBox);
            }
        }

        scrollPane.setContent(cardsHb);
        scrollPane.resize(170, 225);
        scrollPane.setMaxSize(170, 225);
        scrollPane.setPrefViewportHeight(225);
        scrollPane.setPrefViewportWidth(170);

        cardsHb.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView(new Image("file:Css/pictures/New folder (3)/ui/card_silenced@2x.png"));
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);
        Label label = new Label(type);
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("Comic Sans MS", 22));
        StackPane stackPane = new StackPane(imageView, label);
        hBox.getChildren().addAll(stackPane, scrollPane);

        scrollPane.setVisible(false);

        stackPane.setOnMouseClicked(event ->
        {
            if (scrollPane.isVisible())
                scrollPane.setVisible(false);
            else
                scrollPane.setVisible(true);
        });

        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }

    private void setAllCardEffects(Pane pane, Card card)
    {
        pane.setOnMouseClicked(mouseEvent ->
        {
            try
            {
                buy(card);
                money.setText(String.valueOf(Controller.getInstance().getCurrentPlayer().getCash()));
            }
            catch (CloneNotSupportedException e)
            {
                e.printStackTrace();
            }
        });

        pane.setOnMouseEntered(mouseEvent ->
        {
            pane.setEffect(new Glow(30));
            card.syncCardNumber();
            feathers.setText("\n" + getFeathersText(card));

        });

        pane.setOnMouseExited(mouseEvent ->
        {
            pane.setEffect(new Glow(0));
            feathers.setText("");
        });

    }

    private String getFeathersText(Card card)
    {

        if (card instanceof Warrior && ((Warrior) card).getSpecialSpell() != null)
        {
            if (((Warrior) card).getSpecialSpell() == null)
            {
                return "No Special Spell";
            }
            return ("Special Spell Feathers : \n" + ((Warrior) card).getSpecialSpell().getFeathers());
        }
        else if (card instanceof Spell || card instanceof Passive)
        {
            if (card instanceof Passive)
                return ("Spell Feathers : \n" + ((Passive) card).getSpell().getFeathers());
            else return ("Spell Feathers : \n" + ((Spell) card).getFeathers());
        }
        return "";
    }

    private void buy(Card card) throws CloneNotSupportedException
    {

        Player currentPlayerInShop = Controller.getInstance().getCurrentPlayer();

        if (card.getPrice() > currentPlayerInShop.getCash())
        {
            showWarningAlert("You Have'nt Enough Cash");
            return;
        }
        if (card instanceof Passive && currentPlayerInShop.getCollection().getNumberOfUsable() == 3)
        {
            showWarningAlert("You Have Three Passive Item Already");
            return;
        }
        try
        {
            Client.getOutputStream().writeObject("number " + card.getName());
            Client.getOutputStream().flush();
            Client.getOutputStream().reset();

            if (cardNumber == 0)
            {
                showWarningAlert("Shop Has Finished This Card ...");
                return;
            }

            Card clonedCard = cloneCard(card);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successful Buy");
            alert.show();

            clonedCard.setOwnerPlayer(currentPlayerInShop);
            currentPlayerInShop.getCollection().getCards().add(clonedCard);
            currentPlayerInShop.decreaseCash(card.getPrice());

            feathers.setText(feathers.getText() + "   Card Number : " + cardNumber + "   \n ");

            Client.getOutputStream().writeObject("change " + (cardNumber - 1) + " " + card.getName());
            Client.getOutputStream().flush();
            Client.getOutputStream().reset();

            Client.getOutputStream().writeObject(Player.getPlayers());
            Client.getOutputStream().flush();
            Client.getOutputStream().reset();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void showWarningAlert(String content)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public Card cloneCard(Card card) throws CloneNotSupportedException
    {
        Object clonedCard = null;

        if (card instanceof Spell)
            clonedCard = ((Spell) card).clone();
        else if (card instanceof Minion)
            clonedCard = ((Minion) card).clone();
        else if (card instanceof Hero)
            clonedCard = ((Hero) card).clone();
        else if (card instanceof Passive)
            clonedCard = ((Passive) card).clone();

        return ((Card) clonedCard);

    }

    public void addPrimitiveCardsToPlayerCollection(Player player) throws RepeatedDeckName
    {

        if (!player.getCollection().getCards().contains(totalDisarm))
        {
            try
            {
                player.getCollection().getCards().addAll(
                        Arrays.asList(
                                cloneCard(arash),
                                cloneCard(pareSimorgh),
                                cloneCard(kamandareFars),
                                cloneCard(fireBall),
                                cloneCard(neyzeDareFars),
                                cloneCard(asbSavareFars),
                                cloneCard(lightingBolt),
                                cloneCard(poisonLake),
                                cloneCard(madness),
                                cloneCard(empower),
                                cloneCard(sepahSalar),
                                cloneCard(kamandareToorani),
                                cloneCard(gholabeToorani),
                                cloneCard(neyzedareToorani),
                                cloneCard(ashkBoos),
                                cloneCard(gorzDareToorani),
                                cloneCard(ghooleTakCheshm),
                                cloneCard(ejdehayeAtashAndaz),
                                cloneCard(shireDarandeh),
                                cloneCard(gorazeVahshi),
                                cloneCard(piran),
                                cloneCard(giv),
                                cloneCard(bahman),
                                cloneCard(seyed)
                        ));
                for (Card card : player.getCollection().getCards())
                {
                    card.setOwnerPlayer(player);
                }
            }
            catch (CloneNotSupportedException e)
            {
                e.printStackTrace();
            }
        }
    }

    void addDefaultDeckToPlayer(Player player)
    {
        for (Player.Deck deck : player.getDecks())
            if (deck.getName().equals("Default Deck"))
                return;

        ArrayList<Card> primitiveMinionsAndSpells = new ArrayList<>();
        for (int i = 2; i < 22; i++)
        {
            primitiveMinionsAndSpells.add(player.getCollection().getCards().get(i));
        }
        Player.Deck deck = player.new Deck(
                ((Hero) player.getCollection().getCards().get(0)),
                primitiveMinionsAndSpells,
                ((Passive) player.getCollection().getCards().get(1)));

        deck.setName("Default Deck");

        player.getDecks().add(deck);

        player.setMainDeck(deck);
    }

    public void initialiseShop()
    {
//    {
        shopCards.add(totalDisarm);
        shopCards.add(removeBadDispel);
        shopCards.add(removeGoodDispel);
        shopCards.add(empower);
        shopCards.add(fireBall);
        shopCards.add(godStrength);
        shopCards.add(hellFire);
        shopCards.add(lightingBolt);
        shopCards.add(poisonLake);
        shopCards.add(madness);
        shopCards.add(allDisarm);
        shopCards.add(allPoison);
        shopCards.add(removeBadOneDispel);
        shopCards.add(removeGoodDispel);
        shopCards.add(healthWithProfit);
        shopCards.add(powerUp);
        shopCards.add(allPower);
        shopCards.add(allAttack);
        shopCards.add(weakening);
        shopCards.add(sacrifice);
        shopCards.add(kingsGuard);
        shopCards.add(shock);
        shopCards.add(kamandareFars);
        shopCards.add(shamshirZaneFars);
        shopCards.add(neyzeDareFars);
        shopCards.add(asbSavareFars);
        shopCards.add(pahlavaneFars);
        shopCards.add(sepahSalar);
        shopCards.add(kamandareToorani);
        shopCards.add(gholabeToorani);
        shopCards.add(neyzedareToorani);
        shopCards.add(jasoseToorani);
        shopCards.add(gorzDareToorani);
        shopCards.add(ghooleTakCheshm);
        shopCards.add(removeGoodOneDispel);
        shopCards.add(mareSammi);
        shopCards.add(ejdehayeAtashAndaz);
        shopCards.add(shireDarandeh);
        shopCards.add(gorazeVahshi);
        shopCards.add(piran);
        shopCards.add(giv);
        shopCards.add(bahman);
        shopCards.add(seyed);
        shopCards.add(ashkBoos);

        shopCards.add(diveSepid);
        shopCards.add(simorgh);
        shopCards.add(ejdeha7Sar);
        shopCards.add(rakhsh);
        shopCards.add(zahhak);
        shopCards.add(kaveh);
        shopCards.add(arash);
        shopCards.add(afsaneh);
        shopCards.add(esfandiar);
        shopCards.add(rostam);

        shopCards.add(tajeDanai);
        shopCards.add(namooseSepar);
        shopCards.add(kamaneDamool);
        collectibleCards.add(nooshDaroo);
        collectibleCards.add(tire2Sakh);
        shopCards.add(pareSimorgh);
        collectibleCards.add(elksir);
        collectibleCards.add(majooneMana);
        collectibleCards.add(rooyinTan);
        collectibleCards.add(nefrineMarg);
        collectibleCards.add(randomDamage);
        shopCards.add(terrorHood);
        collectibleCards.add(bladesOfAgility);
        shopCards.add(kingWisdom);
        shopCards.add(poisonousDagger);
        shopCards.add(shockHammer);
        shopCards.add(soulEater);
        shopCards.add(ghosleTamid);
        collectibleCards.add(shamshireChini);

//
//        try
//        {
//            OutputStream outputStream = new FileOutputStream("src/server/cards.txt");
//            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
//            oos.writeObject(shopCards);
//            oos.flush();
//            oos.close();
//            outputStream.close();
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }

    }

    private Spell totalDisarm = new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "Total Disarm", 1000, 0, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new Disarm(1000, PON.Negative, ActiveTime.onInsert));

    //
    private Spell removeBadDispel = new Spell(Area.onSquare, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "Bad Dispel", 750, 1, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new DeBuff(1, PON.Positive, ActiveTime.perTurn, BuffType.deBuffNegatives));

    //
    private Spell removeGoodDispel = new Spell(Area.onSquare, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "Good Dispel", 750, 1, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new DeBuff(1, PON.Negative, ActiveTime.perTurn, BuffType.deBuffPositives));

    //
    private Spell empower = new Spell(Area.onOneTarget, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "Empower", 250, 1, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new AttackPoint(+2, 1, PON.Positive, ActiveTime.perTurn));

    //
    private Spell fireBall = new Spell(Area.onOneTarget, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "Fire Ball", 400, 1, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint(-4, 1, PON.Negative, ActiveTime.perTurn));

    //
    private Spell godStrength = new Spell(Area.onOneTarget, FOE.friend, TargetType.onHero, ActiveTime.onInsert, Type.NormalSpell, "God Strength", 450, 2, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new AttackPoint(+4, 1, PON.Positive, ActiveTime.perTurn));

    //
    private Spell hellFire = new Spell(Area.onSquare, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "Hell Fire", 600, 3, 0, 2,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint(-2, 2, PON.Negative, ActiveTime.perTurn));

    //
    private Spell lightingBolt = new Spell(Area.onOneTarget, FOE.enemy, TargetType.onHero, ActiveTime.onInsert, Type.NormalSpell, "Lighting", 1250, 2, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint(-8, 1, PON.Negative, ActiveTime.perTurn));

    //
    private Spell poisonLake = new Spell(Area.onSquare, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "Poison Lake", 900, 5, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint(-1, 1, PON.Negative, ActiveTime.perTurn));

    //
    private Spell madness = new Spell(Area.onOneTarget, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "Madness", 650, 0, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new AttackPoint(+4, 3, PON.Positive, ActiveTime.perTurn),
            new Disarm(4, PON.Negative, ActiveTime.perTurn));

    //
    private Spell allDisarm = new Spell(Area.allWarrior, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "Area Disarm", 2000, 9, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new Disarm(2, PON.Negative, ActiveTime.perTurn));

    //
    private Spell allPoison = new Spell(Area.allWarrior, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "Area Poison", 1500, 8, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint(-1, 4, PON.Negative, ActiveTime.perTurn));

    //
    private Spell removeBadOneDispel = new Spell(Area.onOneTarget, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "Bad Dispel1", 2100, 0, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new DeBuff(1, PON.Positive, ActiveTime.perTurn, BuffType.deBuffNegatives));

    //
    private Spell removeGoodOneDispel = new Spell(Area.onSquare, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "Good Dispel1", 750, 1, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new DeBuff(1, PON.Negative, ActiveTime.perTurn, BuffType.deBuffPositives));

    //
    private Spell healthWithProfit = new Spell(Area.onOneTarget, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "Health Profit", 2250, 0, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint(-6, 1, PON.Negative, ActiveTime.perTurn),
            new AttackPoint(+2, 3, PON.Positive, ActiveTime.onDefend));

    //
    private Spell powerUp = new Spell(Area.onOneTarget, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "Power Up", 2500, 2, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new AttackPoint(+6, 1, PON.Positive, ActiveTime.perTurn));

    //
    private Spell allPower = new Spell(Area.allWarrior, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "Area Power", 2000, 4, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new AttackPoint(+2, 1, PON.Positive, ActiveTime.perTurn));

    //
    private Spell allAttack = new Spell(Area.onCol, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "Area Attack", 1500, 4, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint(-6, 1, PON.Negative, ActiveTime.perTurn));

    //
    private Spell weakening = new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinion, ActiveTime.onInsert, Type.NormalSpell, "Weakening", 1000, 1, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new AttackPoint(-4, 1, PON.Negative, ActiveTime.perTurn));

    //
    private Spell sacrifice = new Spell(Area.onOneTarget, FOE.friend, TargetType.onMinion, ActiveTime.onInsert, Type.NormalSpell, "Sacrifice", 1600, 2, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint(-6, 1, PON.Negative, ActiveTime.perTurn),
            new AttackPoint(+8, 1, PON.Positive, ActiveTime.perTurn));

    //
    private Spell kingsGuard = new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinion, ActiveTime.onClick, Type.NormalSpell, "King Guard", 1750, 9, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint(-100, 1, PON.Negative, ActiveTime.perTurn));

    //
    private Spell shock = new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "Shock", 1200, 1, 0, 100,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new Stun(2, PON.Negative, ActiveTime.perTurn));

    //Minions

    AnimationGIFSPack firstMinionGifsPack = new AnimationGIFSPack(firstMinionGifAddress, firstMinionAttackGIFAddress);
    AnimationGIFSPack secondMinionGifsPack = new AnimationGIFSPack(secondMinionGifAddress, secondMinionAttackGIFAddress);
    AnimationGIFSPack thirdMinionGifsPack = new AnimationGIFSPack(thirdMinionGifAddress, thirdMinionAttackGIFAddress);

    private Minion kamandareFars = new Minion("Archer", 2, 4, 6, 300, AttackType.Ranged, 7,
            null, firstMinionGifsPack);

    //
    private Minion shamshirZaneFars = new Minion("Swordsman", 2, 4, 6, 400, AttackType.Melee, 1,
            new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onAttack, Type.SpecialSpell, "SP", 400, 0, 0, 1,
                    new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new Stun(1, PON.Negative, ActiveTime.perTurn)), firstMinionGifsPack);

    //
    private Minion neyzeDareFars = new Minion("Spearman", 1, 3, 5, 500, AttackType.Hybrid, 3,
            null, firstMinionGifsPack);

    //
    private Minion asbSavareFars = new Minion("Horseman", 4, 6, 10, 200, AttackType.Melee, 1,
            null, firstMinionGifsPack);

    //
    private Minion pahlavaneFars = new Minion("Gladiator", 9, 6, 24, 600, AttackType.Melee, 1,
            new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onAttack, Type.SpecialSpell, "SP", 0, 0, 0, 1,
                    new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint
                    (-3, 1, PON.Negative, ActiveTime.perTurn)), secondMinionGifsPack);

    //
    private Minion sepahSalar = new Minion("Separate", 7, 4, 12, 800, AttackType.Melee, 1,
            null, secondMinionGifsPack);

    //
    private Minion kamandareToorani = new Minion("Shooter", 1, 4, 3, 500, AttackType.Ranged, 5,
            null, secondMinionGifsPack);

    //
    private Minion gholabeToorani = new Minion("Slingman", 1, 2, 4, 600, AttackType.Ranged, 7,
            null, secondMinionGifsPack);

    //
    private Minion neyzedareToorani = new Minion("Lancer", 1, 4, 4, 600, AttackType.Hybrid, 3,
            null, thirdMinionGifsPack);

    //
    private Minion jasoseToorani = new Minion("Stoolie", 4, 6, 6, 700, AttackType.Melee, 1,
            new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onAttack, Type.SpecialSpell, "SP", 0, 0, 0, 1,
                    new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new Disarm(1, PON.Negative, ActiveTime.perTurn), new HealthPoint(-1, 4, PON.Negative, ActiveTime.perTurn))
            , thirdMinionGifsPack);

    //
    private Minion gorzDareToorani = new Minion("Wandman", 2, 10, 3, 450, AttackType.Melee, 1,
            null, thirdMinionGifsPack);

    //
    private Minion ghooleTakCheshm = new Minion("Single Eye", 7, 12, 11, 0, AttackType.Hybrid, 3,
            new Spell(Area.onAround, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onDeath, Type.SpecialSpell, "SP", 0, 0, 0, 1,
                    new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint(-2, 1, PON.Negative, ActiveTime.perTurn))
            , thirdMinionGifsPack);

    //
    private Minion mareSammi = new Minion("Snake", 4, 6, 5, 300, AttackType.Ranged, 4,
            new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onAttack, Type.SpecialSpell, "SP", 0, 0, 0, 0,
                    new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint(-1, 3, PON.Negative, ActiveTime.perTurn))
            , firstMinionGifsPack);

    //
    private Minion ejdehayeAtashAndaz = new Minion("Dragon", 5, 5, 9, 250, AttackType.Ranged, 4,
            null, secondMinionGifsPack);

    //
    private Minion shireDarandeh = new Minion("Lion", 2, 8, 1, 600, AttackType.Melee, 1,
            new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onAttack, Type.SpecialSpell, "SP", 0, 0, 0, 1,
                    new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new DeBuff(1, PON.Negative, ActiveTime.perTurn, BuffType.deBuffHoly))
            , thirdMinionGifsPack);

    //
    private Minion gorazeVahshi = new Minion("Hog", 6, 14, 10, 500, AttackType.Melee, 1,
            new Spell(Area.onOneTarget, FOE.friend, TargetType.onMinion, ActiveTime.onDefend, Type.SpecialSpell, "SP", 500, 0, 0, 10,
                    new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new DeBuff(2, PON.Positive, ActiveTime.perTurn, BuffType.deBuffDisarm))
            , firstMinionGifsPack);

    //
    private Minion piran = new Minion("Piran", 8, 12, 8, 400, AttackType.Melee, 1,
            new Spell(Area.onOneTarget, FOE.friend, TargetType.onMinion, ActiveTime.onDefend, Type.SpecialSpell, "SP", 400, 0, 0, 1,
                    new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new DeBuff(1, PON.Positive, ActiveTime.perTurn, BuffType.deBuffPoison))
            , secondMinionGifsPack);

    //
    private Minion giv = new Minion("Giv", 4, 7, 5, 450, AttackType.Ranged, 5,
            new Spell(Area.onOneTarget, FOE.friend, TargetType.onMinion, ActiveTime.onDefend, Type.SpecialSpell, "SP", 0, 0, 0, 10,
                    new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new DeBuff(1, PON.Positive, ActiveTime.perTurn, BuffType.deBuffNegatives))
            , thirdMinionGifsPack);

    //
    private Minion bahman = new Minion("Bahman", 8, 16, 9, 450, AttackType.Melee, 1,
            new Spell(Area.randomWarrior, FOE.enemy, TargetType.onMinion, ActiveTime.onInsert, Type.SpecialSpell, "SP", 0, 0, 0, 10,
                    new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint(-16, 1, PON.Negative, ActiveTime.perTurn))
            , firstMinionGifsPack);

    //
    private Minion seyed = new Minion("Siyyed", 7, 8, 14, 400, AttackType.Melee, 1,
            new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onDefend, Type.SpecialSpell, "SP", 0, 0, 0, 10,
                    new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new Stun(2, PON.Negative, ActiveTime.perTurn))
            , secondMinionGifsPack);

    //
    private Minion ashkBoos = new Minion("AshkBoos", 7, 8, 14, 400, AttackType.Melee, 1,
            new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onDefend, Type.SpecialSpell, "SP", 0, 0, 0, 1,
                    new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint(-2, 1, PON.Negative, ActiveTime.perTurn))
            , thirdMinionGifsPack);

    //////////////////Heros

    AnimationGIFSPack firstHeroGifsPack = new AnimationGIFSPack(breathFirstHeroAddress, attackFirstHeroAddress);
    AnimationGIFSPack secondHeroGifsPack = new AnimationGIFSPack(breathSecondHeroAddress, attackSecondHeroAddress);
    AnimationGIFSPack thirdHeroGifsPack = new AnimationGIFSPack(breathThirdHeroAddress, attackThirdHeroAddress);

    private Hero diveSepid = new Hero("Blue Demon", 4, 50, 8000, AttackType.Melee, 1,
            new Spell(Area.onOneTarget, FOE.friend, TargetType.onHero, ActiveTime.onClick,
                    Type.SpecialSpell, "SP", 0, 1, 2, 10,
                    new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new AttackPoint(+4, 1, PON.Positive, ActiveTime.perTurn))
            , firstHeroGifsPack);

    //
    private Hero simorgh = new Hero("Phoenix", 4, 50, 9000, AttackType.Melee, 1,
            new Spell(Area.allWarrior, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onClick,
                    Type.SpecialSpell, "SP", 0, 5, 8, 10,
                    new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new Stun(1, PON.Negative, ActiveTime.perTurn))
            , firstHeroGifsPack);

    //
    private Hero ejdeha7Sar = new Hero("Knight", 4, 50, 8000, AttackType.Melee, 1,
            new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onAttack,
                    Type.SpecialSpell, "SP", 0, 0, 2, 1,
                    new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new Disarm(2, PON.Negative, ActiveTime.perTurn))
            , firstHeroGifsPack);

    //
    private Hero rakhsh = new Hero("Rakhsh", 4, 50, 8000, AttackType.Melee, 1,
            new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onAttack,
                    Type.SpecialSpell, "SP", 0, 1, 2, 1,
                    new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new Stun(2, PON.Negative, ActiveTime.perTurn))
            , secondHeroGifsPack);

    //
    private Hero zahhak = new Hero("Blademan", 2, 50, 10000, AttackType.Melee, 1,
            new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onAttack,
                    Type.SpecialSpell, "SP", 0, 0, 0, 1,
                    new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint(-1, 3, PON.Negative, ActiveTime.perTurn))
            , secondHeroGifsPack);

    //
    private Hero kaveh = new Hero("Kaveh", 4, 50, 8000, AttackType.Melee, 1,
            new Spell(Area.onOneTarget, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onInsert,
                    Type.SpecialSpell, "SP", 0, 1, 3, 10,
                    new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint(+1, 3, PON.Positive, ActiveTime.onAttack))
            , secondHeroGifsPack);

    //
    private Hero arash = new Hero("Arash", 2, 30, 10000, AttackType.Ranged, 6,
            new Spell(Area.onRow, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onClick,
                    Type.SpecialSpell, "SP", 0, 2, 2, 10,
                    new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint(-4, 1, PON.Negative, ActiveTime.perTurn))
            , thirdHeroGifsPack);

    //
    private Hero afsaneh = new Hero("Afsane", 3, 40, 11000, AttackType.Ranged, 3,
            new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onAttack,
                    Type.SpecialSpell, "SP", 0, 1, 2, 1,
                    new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new DeBuff(1, PON.Negative, ActiveTime.perTurn, BuffType.deBuffPositives))
            , thirdHeroGifsPack);

    //
    private Hero esfandiar = new Hero("Esfandiar", 3, 35, 12000, AttackType.Hybrid, 3,
            new Spell(Area.onOneTarget, FOE.friend, TargetType.onHero, ActiveTime.onDefend,
                    Type.SpecialSpell, "SP", 0, 0, 6, 1,
                    new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint(+1, 3, PON.Positive, ActiveTime.onDefend, true))
            , thirdHeroGifsPack);

    //
    private Hero rostam = new Hero("Rostam", 7, 55, 8000, AttackType.Hybrid, 4, null,
            thirdHeroGifsPack);

    // Passives And Collectibles

    private Spell tajeDanaiSpell = new Spell(Area.onOneTarget, FOE.friend, TargetType.onHero, ActiveTime.Passive,
            Type.Usable, "Crown", 0, 0, 1000, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new Mana(+1, 3, PON.Positive, ActiveTime.perTurn));
    private Passive tajeDanai = new Passive("Crown", 300, tajeDanaiSpell, FOE.friend, TargetType.onPlayer, new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"));

    //
    private Spell namooseSeparSpell = new Spell(Area.onOneTarget, FOE.friend, TargetType.onHero, ActiveTime.Passive,
            Type.Usable, "Shield", 0, 0, 1000, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint(+2, 6, PON.Positive, ActiveTime.perTurn, true));
    private Passive namooseSepar = new Passive("Shield", 4000, namooseSeparSpell, FOE.friend, TargetType.onHero, new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"));

    //
    private Spell kamaneDamoolSpell = new Spell(Area.onOneTarget, FOE.friend, TargetType.onHero, ActiveTime.onAttack,
            Type.Usable, "Damool", 0, 0, 1000, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new Disarm(2, PON.Negative, ActiveTime.onAttack, true));
    private Passive kamaneDamool = new Passive("Damool", 30000, kamaneDamoolSpell, FOE.friend, TargetType.onHero, new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"));

    //
    private Spell nooshDaroo = new Spell(Area.randomWarrior, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onClick,
            Type.Collectible, "Drug", 0, 0, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint(+6, 1, PON.Positive, ActiveTime.perTurn));

    //
    private Spell tire2Sakh = new Spell(Area.randomWarrior, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onClick,
            Type.Collectible, "Arrow", 0, 0, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new AttackPoint(+2, 1, PON.Positive, ActiveTime.perTurn));

    //
    private Spell pareSimorghSpell = new Spell(Area.onOneTarget, FOE.enemy, TargetType.onHero, ActiveTime.onAttack,
            Type.Usable, "Phoniex Wing", 0, 0, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new AttackPoint(-1, 1, PON.Negative, ActiveTime.perTurn));
    private Passive pareSimorgh = new Passive("PareSimorgh", 3500, pareSimorghSpell, FOE.enemy, TargetType.onHero,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"));

    //
    private Spell elksir = new Spell(Area.onOneTarget, FOE.friend, TargetType.onHero, ActiveTime.onClick,
            Type.Collectible, "Elixir", 0, 0, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint(+3, 1, PON.Positive, ActiveTime.perTurn),
            new AttackPoint(+3, 1, PON.Positive, ActiveTime.perTurn));

    //
    private Spell majooneMana = new Spell(Area.onOneTarget, FOE.friend, TargetType.onPlayer, ActiveTime.onClick,
            Type.Collectible, "Mana Bar", 0, 0, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new Mana(+3, 1, PON.Positive, ActiveTime.perTurn));

    //
    private Spell rooyinTan = new Spell(Area.onOneTarget, FOE.friend, TargetType.onHero, ActiveTime.onClick,
            Type.Collectible, "Health Bar", 0, 0, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint(+5, 4, PON.Positive, ActiveTime.onDefend));

    //
    private Spell nefrineMarg = new Spell(Area.onOneTarget, FOE.enemy, TargetType.onHero, ActiveTime.onClick,
            Type.Collectible, "Curse", 0, 0, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint(-8, 1, PON.Negative, ActiveTime.perTurn));

    //
    private Spell randomDamage = new Spell(Area.randomWarrior, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onClick,
            Type.Collectible, "RandomDamage", 0, 0, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new AttackPoint(+2, 1, PON.Positive, ActiveTime.perTurn));

    //
    private Spell terrorHoodSpell = new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onAttack,
            Type.Usable, "Terror Hood", 0, 0, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new AttackPoint(-1, 1, PON.Negative, ActiveTime.perTurn));
    private Passive terrorHood = new Passive("TerrorHood", 5000, terrorHoodSpell, FOE.friend, TargetType.onMinionOrHero, new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"));

    //
    private Spell bladesOfAgility = new Spell(Area.randomWarrior, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onClick,
            Type.Collectible, "Agility", 0, 0, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new AttackPoint(+6, 1, PON.Positive, ActiveTime.perTurn));

    //
    private Spell kingWisdomSpell = new Spell(Area.allWarrior, FOE.friend, TargetType.onHero, ActiveTime.Passive,
            Type.Usable, "King Wisdom", 0, 0, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new Mana(+1, 100, PON.Positive, ActiveTime.perTurn, true));
    private Passive kingWisdom = new Passive("King Wisdom", 9000, kingWisdomSpell, FOE.friend, TargetType.onPlayer, new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"));

    //
    private Spell poisonousDaggerSpell = new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onAttack,
            Type.Usable, "Poisonous Dagger", 0, 0, 0, 0,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint(-1, 1, PON.Negative, ActiveTime.perTurn));
    private Passive poisonousDagger = new Passive("Poisonous Dagger", 7000, poisonousDaggerSpell, FOE.friend, TargetType.onMinionOrHero,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"));

    //
    private Spell shockHammerSpell = new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onAttack,
            Type.Usable, "Shock Hammer", 0, 0, 0, 1,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new Disarm(2, PON.Negative, ActiveTime.perTurn));
    private Passive shockHammer = new Passive("Shock Hammer", 15000, shockHammerSpell, FOE.friend, TargetType.onHero, new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"));

    //
    private Spell soulEaterSpell = new Spell(Area.onOneTarget, FOE.friend, TargetType.onHero, ActiveTime.onDeath,
            Type.Usable, "Soul Eater", 0, 0, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint(+1, 1, PON.Positive, ActiveTime.perTurn));
    private Passive soulEater = new Passive("Soul Eater", 25000, soulEaterSpell, FOE.friend, TargetType.onMinionOrHero, new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"));

    //
    private Spell ghosleTamidSpell = new Spell(Area.onOneTarget, FOE.friend, TargetType.onMinion, ActiveTime.onInsert,
            Type.Usable, "Ghosle Tamid", 0, 0, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new HealthPoint(+1, 2, PON.Positive, ActiveTime.onDefend));
    private Passive ghosleTamid = new Passive("Ghosle Tamid", 20000, ghosleTamidSpell, FOE.friend, TargetType.onMinion, new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"));

    //
    private Spell shamshireChini = new Spell(Area.onOneTarget, FOE.friend, TargetType.onHero, ActiveTime.onClick,
            Type.Collectible, "Dirk", 0, 0, 0, 10,
            new AnimationGIFSPack("file:Css/pictures/New folder (3)/shop/menu_icon_orbs.png"), new AttackPoint(+5, 1, PON.Positive, ActiveTime.perTurn));

    public ArrayList<Spell> getCollectibleCards()
    {
        return collectibleCards;
    }

    public Card searchCard(String cardName)
    {
        for (Card shopCard : shopCards)
            if (shopCard.getName().equals(cardName))
                return shopCard;

        return null;
    }

    public Label getFeathers()
    {
        return feathers;
    }

    public int getCardNumber()
    {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber)
    {
        this.cardNumber = cardNumber;
    }

}

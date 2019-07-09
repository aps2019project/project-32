package client.models;

import client.controller.Exceptions.*;
import client.models.widget.Widget;
import client.models.widget.cards.Card;
import client.models.widget.cards.Warriors.Hero;
import client.models.widget.cards.Warriors.Minion;
import client.models.widget.cards.Warriors.Warrior;
import client.models.widget.cards.spells.Passive;
import client.models.widget.cards.spells.Spell;
import client.models.widget.cards.spells.Type;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

//import sample.controller.Exceptions.*;
//import sample.models.widget.Widget;
//import sample.models.widget.cards.Card;
//import sample.models.widget.cards.Warriors.Hero;
//import sample.models.widget.cards.Warriors.Minion;
//import sample.models.widget.cards.Warriors.Warrior;
//import sample.models.widget.cards.spells.Passive;
//import sample.models.widget.cards.spells.Spell;
//import sample.models.widget.cards.spells.Type;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;

public class Player implements Serializable
{
    public Player(String name, String passWord)
    {
        this.name = name;
        this.passWord = passWord;
        cash = 35000;
        winNumber = 0;
        loseNumber = 0;
    }

    public Player()
    {
        this.name = "AI";
        this.passWord = "AI";
        this.cash = 0;
        this.winNumber = 0;
        this.loseNumber = 0;
        //this.collection = null;
    }

    private static ArrayList<Player> players = new ArrayList<>();
    private String name;
    private String passWord;
    private int cash;
    private int winNumber;
    private int loseNumber;
    private Collection collection = new Collection();

    private ArrayList<Deck> decks = new ArrayList<>();
    private Deck mainDeck;
    private Deck copiedMainDeck;

    public void addNewDeck(String name) throws RepeatedDeckName
    {
        if (findDeck(name) != null)
            throw new RepeatedDeckName();

        decks.add(new Deck(name));

    }

    public void removeDeck(Deck deck)
    {
        decks.remove(deck);
    }

    public Deck findDeck(String name)
    {
        for (Deck deck : decks)
            if (deck.name.equals(name))
                return deck;

        return null;
    }

    public String toShowAllDecks()
    {
        String allDeck = "";
//        if (mainDeck != null)
//            allDeck = mainDeck.toShowDeck();
//        for (Player.Deck deck : Controller.getInstance().getCurrentPlayer().getDecks())
//            allDeck = allDeck.concat(deck.toShowDeck());

        return allDeck;
    }

    private Hand playerHand = new Hand();
    private GraveYard graveYard = new GraveYard();
    private ArrayList<BattleHistory> battleHistories = new ArrayList<>();

    private int playerManaSpace;
    private int playerCurrentMana;

    public class Collection implements Serializable
    {
        ArrayList<Card> cards = new ArrayList<>();

        public ArrayList<Card> getCards()
        {
            return cards;
        }

        public Card findCardInCollection(int cardID)
        {
            for (Card card : cards)
                if (card.getID() == cardID)
                    return card;

            return null;
        }

        public Card findCardInCollection(String cardName)
        {
            for (Card card : cards)
                if (card.getName().equals(cardName))
                    return card;

            return null;
        }

        public void removeFromCollection(String cardName)
        {
            Card card = findCardInCollection(cardName);
            removeFromCollection(card);
        }

        public void removeFromCollection(Card card)
        {
            for (Deck deck : decks)
            {
                if (deck.searchCard(card) != null)
                {
                    deck.removeCardFromDeck(card);
                }
                else if (deck.getHero() == card)
                {
                    deck.hero = null;
                }
                else if (deck.getPassiveItem() == card)
                    deck.passiveItem = null;
            }

            card.setOwnerPlayer(null);
            getCards().remove(card);
        }

        public String toShowSearchResult(String cardOrItemName)
        {
            Widget intendedWidget = findCardInCollection(cardOrItemName);
            if (intendedWidget == null)
            {
            }
            //throw new CardNotFound();
            else
                return String.valueOf(intendedWidget.getID());
            return "";
        }

        public int getNumberOfUsable()
        {
            int count = 0;
            for (Card card : cards)
            {
                if (card instanceof Passive)
                    count++;
            }
            return count;
        }
    }

    public class Deck implements Serializable
    {
        public Deck(String name)
        {
            this.name = name;
        }

        public Deck(Hero hero, ArrayList<Spell> spells, ArrayList<Minion> minions, Passive passiveItem)
        {
            cards.addAll(spells);
            cards.addAll(minions);
            this.hero = hero;
            this.passiveItem = passiveItem;
        }

        public Deck(Hero hero, ArrayList<Card> cards, Passive passiveItem)
        {
            this.hero = hero;
            this.cards = cards;
            this.passiveItem = passiveItem;
        }

        @Override
        public Object clone() throws CloneNotSupportedException
        {
            ArrayList<Card> copiedCard = new ArrayList<>();
            for (Card card : cards)
            {
                if (card instanceof Spell)
                    copiedCard.add(((Card) ((Spell) card).clone()));
                if (card instanceof Minion)
                    copiedCard.add(((Card) ((Minion) card).clone()));
            }
            Hero copiedHero = null;
            Passive copiedPassive = null;
            if (this.hero != null)
            {
                copiedHero = ((Hero) this.hero.clone());
            }
            if (this.passiveItem != null)
            {
                copiedPassive = ((Passive) this.passiveItem.clone());
            }
            return new Deck(copiedHero, copiedCard, copiedPassive);
        }

        private String name;
        private Hero hero;
        private Passive passiveItem;
        private ArrayList<Card> cards = new ArrayList<>();

        public Pane toShowDeck()
        {
            int heroNum = 0;
            int passiveNum = 0;
            if (hero != null)
                heroNum = 1;
            if (passiveItem != null)
                passiveNum = 1;
            Pane pane = new Pane();
            ImageView imageView = new ImageView(new Image("file:C:/Users/Tarahan/IdeaProjects/project/Css/pictures/New folder (3)/generals/general_portrait_image_hex_f1.png"));
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(70);

            Label label = new Label(String.format("%s", this.name));
            label.relocate(60, 20);
            label.setTextFill(Color.BLACK);
            Label label1 = new Label(String.format("hero: %d cards: %d passive: %d", heroNum, cards.size(), passiveNum));
            label1.relocate(55, 40);
            label1.setTextFill(Color.BLACK);
            pane.getChildren().addAll(imageView, label, label1);

            return pane;
        }

        public void addCardToDeck(Card card) throws DeckIsFull, CardExistInDeckAlready, DeckHasPassiveAlready, DeckHasHeroAlready
        {
            if ((card instanceof Spell && ((Spell) card).getType() == Type.NormalSpell) || card instanceof Minion)
                if (cards.size() >= 20)
                {
                    throw new DeckIsFull();
                }
                else if (cards.contains(card))
                {
                    throw new CardExistInDeckAlready();
                }
                else
                    cards.add(card);
            else if (card instanceof Passive)
                if (passiveItem != null && passiveItem.equals(card))
                {
                    throw new CardExistInDeckAlready();
                }
                else if (passiveItem != null)
                {
                    throw new DeckHasPassiveAlready();
                }
                else
                    passiveItem = ((Passive) card);
            else if (card instanceof Hero)
                if (hero != null && hero.equals(card))
                {
                    throw new CardExistInDeckAlready();
                }
                else if (hero != null)
                {
                    throw new DeckHasHeroAlready();
                }
                else
                {
                    hero = ((Hero) card);
                }

        }

        public void removeCardFromDeck(Card card)
        {
            if (card instanceof Minion || card instanceof Spell)
                cards.remove(card);
            else if (card instanceof Hero)
            {
                hero = null;
            }
            else if (card instanceof Passive)
                passiveItem = null;

        }

        public Card searchCard(Card card1)
        {
            for (Card card : cards)
                if (card == card1)
                    return card;

            return null;
        }

        public boolean isValidDeck()
        {
            return hero != null && cards.size() == 20;
        }

        public Warrior getHero()
        {
            return hero;
        }

        public void setHero(Hero hero)
        {
            this.hero = hero;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public ArrayList<Card> getCards()
        {
            return cards;
        }

        public Passive getPassiveItem()
        {
            return passiveItem;
        }
    }

    public class Hand implements Serializable
    {
        private ArrayList<Card> handCards = new ArrayList<>();
        private ArrayList<Spell> collectedItems = new ArrayList<>(); // new in battle and after game going null
        private Card nextCard;
        private SecureRandom randomMaker = new SecureRandom();

        public void clearCollectedItemsAfterGame()
        {
            collectedItems.clear();
        }

        public int getFlagNumbersInCollectedItems()
        {
            int counter = 0;
            for (Spell collectedItem : collectedItems)
                if (collectedItem.getType() == Type.Flag)
                    counter++;

            return counter;
        }

        public void makeRandomiseHand()
        {
            int counter = 0;
            int randomNumber;
            while (counter != 5)
            {
                randomNumber = Math.abs(randomMaker.nextInt()) % 20;
                if (copiedMainDeck.getCards().get(randomNumber) != null)
                {
                    handCards.add(copiedMainDeck.getCards().get(randomNumber));
                    copiedMainDeck.getCards().set(randomNumber, null);
                    counter++;
                }
            }
            setNextCardInHand();
        }

        public String getRandomCardNameFromHand()
        {
            int random = Math.abs(randomMaker.nextInt()) % 5;
            return handCards.get(random).getName();
        }

        private void setNextCardInHand()
        {
            int randomNumber;
            while (true)
            {
                randomNumber = Math.abs(randomMaker.nextInt()) % copiedMainDeck.getCards().size();
                if (copiedMainDeck.getCards().get(randomNumber) != null)
                {
                    nextCard = copiedMainDeck.getCards().get(randomNumber);
                    copiedMainDeck.getCards().remove(randomNumber);
                    break;
                }
            }
        }

        public Card getCardByName(String cardName)
        {
            for (Card handCard : handCards)
                if (handCard.getName().equals(cardName))
                    return handCard;

            for (Spell collectedItem : collectedItems)
                if (collectedItem.getName().equals(cardName))
                    return collectedItem;

            return null;
        }

        public void putCardFromHandActions(Card intendedCard)
        {
            handCards.add(handCards.indexOf(intendedCard), nextCard);
            handCards.remove(intendedCard);
            setNextCardInHand();
        }

        public String toShowHand()
        {
            String handString = "";
//                for (Card handCard : handCards)
//                    handString = handString.concat(handCard.toShow());

            handString = handString.concat("\nNext Card : \n");
//                handString = handString.concat(nextCard.toShow());

            return handString;
        }

        public String toShowCollectedItems()
        {
            String showCollectedItems = "";
            for (Spell collectedItem : collectedItems)
                showCollectedItems = showCollectedItems.concat(collectedItem.toShow() + "\n");

            return showCollectedItems;
        }

        public ArrayList<Card> getHandCards()
        {
            return handCards;
        }

        public void setHandCards(ArrayList<Card> handCards)
        {
            this.handCards = handCards;
        }

        public Card getNextCard()
        {
            return nextCard;
        }

        public void setNextCard(Card nextCard)
        {
            this.nextCard = nextCard;
        }

        public ArrayList<Spell> getCollectedItems()
        {
            return collectedItems;
        }

        public void setCollectedItems(ArrayList<Spell> collectedItems)
        {
            this.collectedItems = collectedItems;
        }
    }

    public class GraveYard implements Serializable
    {
        private ArrayList<Card> graveYard = new ArrayList<>();

        public ArrayList<Card> getGraveYardList()
        {
            return graveYard;
        }

        public String toShowGraveYardCards()
        {
            String graveYardString = "";
//                for (Card card : graveYard)
//                    graveYardString = graveYardString.concat(card.toShow());

            return graveYardString;
        }

        public String toShowCard(int ID)
        {
            String cardString = "";
            for (Card card : graveYard)
                if (card.getID() == ID)
                {
                }
            //cardString = card.toShow();

            return cardString;
        }

        public ArrayList<Card> getGraveYard()
        {
            return graveYard;
        }
    }

    public class BattleHistory implements Serializable
    {
        Player opponent;
        boolean hasWin;
        long battleTime;

        private BattleHistory(Player opponent, boolean thisPlayerHasWin, long battleTime)
        {
            this.opponent = opponent;
            this.hasWin = thisPlayerHasWin;
            this.battleTime = battleTime;
        }
    }

    public void addGameResultToBattleHistories(Player opponent, boolean hasWin, long battleTime)
    {
        this.battleHistories.add(new BattleHistory(opponent, hasWin, battleTime));
    }

    public void decreaseMana(int value)
    {
        this.playerCurrentMana -= value;
    }

    public void toShowLeaderBoard()
    {
        String leaderBoardString = "";
        ArrayList<Player> playersCopy = new ArrayList<>(players);

        playersCopy.sort(Comparator.comparingInt(o -> o.winNumber));

        int counter = 1;
        for (Player player : playersCopy)
            leaderBoardString = leaderBoardString.concat
                    (String.format("%d - UserName : %s - WinNumber : %d\n", counter++, player.name, player.winNumber));
    }

    public void addPassiveOnPlayerCards() throws CloneNotSupportedException
    {
        Passive playerCopiedPassive = this.getCopiedMainDeck().getPassiveItem();

        switch (playerCopiedPassive.getTargetType())
        {
            case onMinion:
                for (Card card : this.getCopiedMainDeck().getCards())
                    if (card instanceof Minion)
                        ((Minion) card).setPassiveSpell(((Spell) playerCopiedPassive.getSpell().clone()));

                break;
            case onHero:
                this.getCopiedMainDeck().getHero().setPassiveSpell
                        (((Spell) playerCopiedPassive.getSpell().clone()));

                break;
            case onMinionOrHero:
                for (Card card : this.getCopiedMainDeck().getCards())
                    if (card instanceof Minion)
                        ((Minion) card).setPassiveSpell(((Spell) playerCopiedPassive.getSpell().clone()));

                this.getCopiedMainDeck().getHero().setPassiveSpell
                        (((Spell) playerCopiedPassive.getSpell().clone()));

                break;
        }
    }

    public String toShowPlayer()
    {
        return String.format
                ("UserName : %s - Cash : %d - WinNumber : %d - LoseNumber : %d",
                        this.name, this.cash, this.winNumber, this.loseNumber);
    }

    public void decreaseCash(int value)
    {
        this.cash -= value;
    }

    public void increaseCash(int value)
    {
        this.cash += value;
    }

    public void increaseWinNumber()
    {
        winNumber++;
    }

    public void increasLoseNumber()
    {
        loseNumber++;
    }

    public void decreaseWinNumber()
    {
        winNumber--;
    }

    public void save()
    {

    }

    public void logout()
    {

    }

    public void changeMana(int manaChange)
    {
        playerCurrentMana += manaChange;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Player)
            return ((Player) obj).name.equals(this.name);
        else return false;
    }

    public static ArrayList<Player> getPlayers()
    {
        return players;
    }

    public static void setPlayers(ArrayList<Player> players)
    {
        Player.players = players;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassWord()
    {
        return passWord;
    }

    public void setPassWord(String passWord)
    {
        this.passWord = passWord;
    }

    public int getCash()
    {
        return cash;
    }

    public void setCash(int cash)
    {
        this.cash = cash;
    }

    public int getWinNumber()
    {
        return winNumber;
    }

    public void setWinNumber(int winNumber)
    {
        this.winNumber = winNumber;
    }

    public int getLoseNumber()
    {
        return loseNumber;
    }

    public void setLoseNumber(int loseNumber)
    {
        this.loseNumber = loseNumber;
    }

    public Deck getMainDeck()
    {
        return mainDeck;
    }

    public void setMainDeck(Deck mainDeck)
    {
        this.mainDeck = mainDeck;
    }

    public Deck getCopiedMainDeck()
    {
        return copiedMainDeck;
    }

    public void setCopiedMainDeck(Deck copiedMainDeck)
    {
        this.copiedMainDeck = copiedMainDeck;
    }

    public ArrayList<Deck> getDecks()
    {
        return decks;
    }

    public Hand getPlayerHand()
    {
        return playerHand;
    }

    public void setPlayerHand(Hand playerHand)
    {
        this.playerHand = playerHand;
    }

    public GraveYard getGraveYard()
    {
        return graveYard;
    }

    public void setGraveYard(GraveYard graveYard)
    {
        this.graveYard = graveYard;
    }

    public ArrayList<BattleHistory> getBattleHistories()
    {
        return battleHistories;
    }

    public void setBattleHistories(ArrayList<BattleHistory> battleHistories)
    {
        this.battleHistories = battleHistories;
    }

    public int getPlayerManaSpace()
    {
        return playerManaSpace;
    }

    public void setPlayerManaSpace(int playerManaSpace)
    {
        this.playerManaSpace = playerManaSpace;
    }

    public int getPlayerCurrentMana()
    {
        return playerCurrentMana;
    }

    public void setPlayerCurrentMana(int playerCurrentMana)
    {
        this.playerCurrentMana = playerCurrentMana;
    }

    public void setCollection(Collection collection)
    {
        this.collection = collection;
    }

    public void setDecks(ArrayList<Deck> decks)
    {
        this.decks = decks;
    }

    public Collection getCollection()
    {
        return collection;
    }
}



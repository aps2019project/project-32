package com.company.models;

import com.company.controller.Controller;
import com.company.controller.Exceptions.*;
import com.company.models.widget.Widget;
import com.company.models.widget.cards.Card;
import com.company.models.widget.cards.Warriors.Hero;
import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.cards.spells.Spell;
import com.company.models.widget.cards.Usable;
import com.company.models.widget.cards.warriors.Hero;
import com.company.models.widget.cards.warriors.Minion;
import com.company.models.widget.items.Flag;
import com.company.models.widget.items.Item;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;

public class Player implements Serializable
{
    public Player(String name, String passWord)
    {
        this.name = name;
        this.passWord = passWord;
        cash = 15000;
        winNumber = 0;
        loseNumber = 0;
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
    private ArrayList<Card> copiedMainDeck;

    public void addNewDeck(String name)
    {
        decks.add(new Deck(name));
    }

    public void removeDeck(String name)
    {
        decks.remove(findDeck(name));
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
        String allDeck = Controller.getInstance().getCurrentPlayer().getMainDeck().getName();
        for (Player.Deck deck : Controller.getInstance().getCurrentPlayer().getDecks())
            allDeck = allDeck.concat(deck.toShowDeck());

        return allDeck;
    }

    private Hand playerHand = new Hand();
    private GraveYard graveYard = new GraveYard();
    private ArrayList<BattleHistory> battleHistories = new ArrayList<>();

    private int playerManaSpace;
    private int playerCurrentMana;

    public class Collection
    {
        ArrayList<Card> cards = new ArrayList<>();

        public ArrayList<Card> getCards() {
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

        public void addTOCollection(Card card){
            getCards().add(card);
        }
        public void removeFromCollection(int cardID) throws CardNotFound {
            for (Deck deck : getDecks()) {
                if (deck.serchCard(cardID) != null){
                    deck.removeCardFromDeck(cardID);
                    break;
                }
            }
            getCards().remove(findCardInCollection(cardID));
        }

        public String toShowSearchResult(String cardOrItemName) throws CardNotFound
        {
            Widget intendedWidget = findCardInCollection(Integer.parseInt(cardOrItemName));
            if (intendedWidget == null)
                throw new CardNotFound();
            else
                return String.valueOf(intendedWidget.getID());
        }

        public String toShowCollection()
        {
            String collectionString = "";
            for (Card card : collection.cards)
                collectionString = collectionString.concat(card.toShow());

            return collectionString;
        }

        public int getNumberOfUsable(){
            int count = 0;
            for (Card card : cards) {
                if (card instanceof Usable)
                    count++;
            }
            return count;
        }
    }

    public class Deck
    {
        private Deck(String name)
        {
            this.name = name;
        }

        private String name;
        private Hero hero;
        private Usable passiveItem;
        private ArrayList<Card> cards = new ArrayList<>();

        public String toShowDeck()
        {
            String deckString = "";
            deckString = deckString.concat(String.format("DeckName : %s", this.name));
            deckString = deckString.concat("Hero : \n");
            deckString = deckString.concat(hero.toShow());

            deckString = deckString.concat("PassiveItem : \n");
            deckString = deckString.concat(passiveItem.toShow());

            deckString = deckString.concat("Cards : \n");
            int counter = 1;
            for (Card card : cards)
                deckString = deckString.concat(String.format("%d - %s\n", counter++, card.toShow()));

            return deckString;
        }

        public void addCardToDeck(Card card) throws DeckIsFull, DeckHasHeroAlready, CardExistInDeckAlready, DeckHasPassiveAlready
        {
            if (card instanceof Spell || card instanceof Minion)
                if (cards.size() >= 20)
                    throw new DeckIsFull();
                else if (cards.contains(card))
                    throw new CardExistInDeckAlready();
                else
                    cards.add(card);
            else if (card instanceof Usable)
                if (passiveItem != null)
                    throw new DeckHasPassiveAlready();
                else if (passiveItem.equals(card))
                    throw new CardExistInDeckAlready();
                else
                    passiveItem = ((Usable) card);
            else if (card instanceof Hero)
                if (hero != null)
                    throw new DeckHasHeroAlready();
                else if (hero.equals(card))
                    throw new CardExistInDeckAlready();
                else
                    hero = ((Hero) card);

        }

        public void removeCardFromDeck(int cardID) throws CardNotFound
        {
            if (hero.getID() == cardID)
                hero = null;
            else if (passiveItem.getID() == cardID)
                passiveItem = null;
            else if (!cards.removeIf(card -> card.getID() == cardID))
                throw new CardNotFound();

        }
        public Card serchCard(int cardID){
            for (Card card : cards) {
                if (card.getID() == cardID)
                    return card;
            }
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

        public Usable getPassiveItem()
        {
            return passiveItem;
        }

        public void setPassiveItem(Usable passiveItem)
        {
            this.passiveItem = passiveItem;
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
    }


    public class Hand
    {
        private ArrayList<Card> handCards = new ArrayList<>();
        private ArrayList<Item> collectedItems; // new in battle and after game going null
        private Flag keepModeFlag;
        private Card nextCard;
        private SecureRandom randomMaker = new SecureRandom();

        public void clearCollectedItemsAfterGame()
        {
            collectedItems.clear();
        }

        public int getFlagNumbersInCollectedItems()
        {
            int counter = 0;
            for (Item collectedItem : collectedItems)
                if (collectedItem instanceof Flag)
                    counter++;

            return counter;
        }

        public void makeRandomiseHand()
        {
            int counter = 0;
            int randomNumber;
            while (counter != 5)
            {
                randomNumber = randomMaker.nextInt(20);
                if (copiedMainDeck.get(randomNumber) != null)
                {
                    handCards.add(copiedMainDeck.get(randomNumber));
                    copiedMainDeck.remove(randomNumber);
                    counter++;
                }
            }
        }

        private void setNextCardInHand()
        {
            int randomNumber;
            while (true)
            {
                randomNumber = randomMaker.nextInt(20);
                if (copiedMainDeck.get(randomNumber) != null)
                {
                    nextCard = copiedMainDeck.get(randomNumber);
                    copiedMainDeck.remove(randomNumber);
                    break;
                }
            }
        }

        public Card getCardFromHandActions(Card intendedCard)
        {
            for (Card handCard : handCards)
                if (handCard.equals(intendedCard))
                    return handCard;

            setNextCardInHand();
            handCards.add(handCards.indexOf(intendedCard), nextCard);
            handCards.remove(handCards.indexOf(intendedCard) + 1);
            return null;
        }

        public String toShowHand()
        {
            String handString = "";
            for (Card handCard : handCards)
                handString = handString.concat(handCard.toShow());

            handString = handString.concat("NextCard : \n");
            handString = handString.concat(nextCard.toShow());

            return handString;
        }

        public String toShowCollectedItems()
        {
            String showCollectedItems = "";
            for (Item collectedItem : collectedItems)
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

        public ArrayList<Item> getCollectedItems()
        {
            return collectedItems;
        }

        public void setCollectedItems(ArrayList<Item> collectedItems)
        {
            this.collectedItems = collectedItems;
        }

        public Flag getKeepModeFlag()
        {
            return keepModeFlag;
        }

        public void setKeepModeFlag(Flag keepModeFlag)
        {
            this.keepModeFlag = keepModeFlag;
        }
    }

    public class GraveYard
    {
        private ArrayList<Card> graveYard = new ArrayList<>();

        public ArrayList<Card> getGraveYardList()
        {
            return graveYard;
        }

        public void setGraveYard(ArrayList<Card> graveYard)
        {
            this.graveYard = graveYard;
        }
    }

    public class BattleHistory
    {
        Player opponent;
        boolean hasWin;
        Date battleTime;

        private BattleHistory(Player opponent, boolean thisPlayerHasWin, Date battleTime)
        {
            this.opponent = opponent;
            this.hasWin = thisPlayerHasWin;
            this.battleTime = battleTime;
        }
    }

    public void addGameResultToBattleHistories(Player opponent, boolean hasWin, Date battleTime)
    {
        this.battleHistories.add(new BattleHistory(opponent, hasWin, battleTime));
    }

    public Item selectItem(int ID)
    {
        return null;
    }

    public void decreaseMana(int value)
    {
        this.playerCurrentMana -= value;
    }

    public void toShowLeaderBoard()
    {
        String leaderBoardString = "";
        ArrayList<Player> playersCopy = new ArrayList<>(players);

        playersCopy.sort((o1, o2) -> o1.winNumber - o2.winNumber);

        int counter = 1;
        for (Player player : playersCopy)
            leaderBoardString = leaderBoardString.concat
                    (String.format("%d - UserName : %s - WinNumber : %d\n", counter++, player.name, player.winNumber));
    }

    public String toShowPlayer()
    {
//         name;
//         passWord;
//         price;
//         winNumber;
//         loseNumber;
        return String.format
                ("UserName : %s - Cash : %d - WinNumber : %d - LoseNumber : %d",
                        this.name, this.cash, this.winNumber, this.loseNumber);
    }
    public void deCreaseCash(int value){
        this.cash -= value;
    }
    public void inCreaseCash(int value){
        this.cash += value;
    }

    public void save()
    {

    }

    public void logout()
    {


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

    public ArrayList<Card> getCopiedMainDeck()
    {
        return copiedMainDeck;
    }

    public void setCopiedMainDeck(ArrayList<Card> copiedMainDeck)
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

